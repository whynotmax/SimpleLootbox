package dev.mzcy.plugin.lootbox.livedrop.gui;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.lootbox.livedrop.model.LiveDrop;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.api.utility.SimpleUUIDFetcher;
import dev.mzcy.plugin.LootboxesPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class LiveDropGui {

    private final LootboxesPlugin plugin;
    private final Item borderItem;
    private final Item noLiveDropFoundItem;
    private final Gui gui;
    private final Lootbox lootbox;

    public LiveDropGui(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.borderItem = createSimpleItem(Material.BLACK_STAINED_GLASS_PANE, "§r");
        this.noLiveDropFoundItem = createSimpleItem(Material.BARRIER, "§cNo live drop found.");
        this.gui = createGui();
    }

    private Gui createGui() {
        Gui.Builder.Normal normalBuilder = Gui.normal()
                .setStructure(
                        "#########",
                        "#ABCDEFG#",
                        "#########"
                )
                .addIngredient('#', borderItem);

        for (int i = 0; i < 7; i++) {
            LiveDrop liveDrop = (i < plugin.liveDropManager().liveDrops().size()) ? plugin.liveDropManager().liveDrops().get(i) : null;
            Item item = (liveDrop == null) ? noLiveDropFoundItem : createLiveDropItem(liveDrop);
            normalBuilder.addIngredient((char) ('A' + i), item);
        }
        return normalBuilder.build();
    }

    private Item createLiveDropItem(LiveDrop liveDrop) {
        String title = replacePlaceholders(plugin.generalConfiguration().liveDropItemTitle(), liveDrop);
        List<String> lore = new ArrayList<>(plugin.generalConfiguration().liveDropItemLore());
        lore.replaceAll(text -> replacePlaceholders(text, liveDrop));
        return new SimpleItem(SimpleItemStack.builder(Material.PLAYER_HEAD)
                .skullOwner(liveDrop.winnerUniqueId())
                .withDisplayName(title)
                .withLore(lore.toArray(new String[0])));
    }

    private String replacePlaceholders(@NotNull String text, @Nullable LiveDrop liveDrop) {
        if (liveDrop == null) return text.replace("{time}", "N/A").replace("{player}", "N/A").replace("{item}", "N/A");
        text = text.replace("{time}", new SimpleDateFormat(plugin.generalConfiguration().dateFormat()).format(new Date(liveDrop.wonAt())));
        text = text.replace("{player}", (SimpleUUIDFetcher.fromUniqueId(liveDrop.winnerUniqueId()) == null ? "Unknown" : Objects.requireNonNull(SimpleUUIDFetcher.fromUniqueId(liveDrop.winnerUniqueId()))));
        text = text.replace("{item}", "%sx %s".formatted(liveDrop.wonItem().getItemStack().getAmount(), PlainTextComponentSerializer.plainText().serialize(liveDrop.wonItem().getItemStack().displayName())));
        text = text.replace("{lootbox}", lootbox.getName());
        return text;
    }

    private SimpleItem createSimpleItem(Material material, String displayName) {
        return new SimpleItem(SimpleItemStack.builder(material).withDisplayName(displayName));
    }

    public void open(Player player) {
        Window window = Window.single().setViewer(player).setGui(gui).setTitle(MiniMessage.miniMessage().serialize(lootbox.getDisplayName().append(Component.text("<reset> - Live drops")))).build(player);
        window.open();
        window.setCloseable(true);
    }
}