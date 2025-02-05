package dev.mzcy.plugin.lootbox.livedrop.gui;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.lootbox.livedrop.model.LiveDrop;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.plugin.LootboxesPlugin;
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

public class LiveDropGui {

    LootboxesPlugin plugin;
    Item borderItem;
    Item noLiveDropFoundItem;
    Gui gui;
    Lootbox lootbox;

    public LiveDropGui(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.borderItem = new SimpleItem(SimpleItemStack.builder(Material.BLACK_STAINED_GLASS_PANE).withDisplayName("§r"));
        this.noLiveDropFoundItem = new SimpleItem(SimpleItemStack.builder(Material.BARRIER).withDisplayName("§cNo live drop found."));
        Gui.Builder.Normal normalBuilder = Gui.normal()
                .setStructure(
                        "#########",
                        "#ABCDEFG#",
                        "#########"
                )
                .addIngredient('#', borderItem);
        for (int i = 0; i < 7; i++) {
            LiveDrop liveDrop = null;
            if (i < plugin.liveDropManager().liveDrops().size()) {
                liveDrop = plugin.liveDropManager().liveDrops().get(i);
            }
            String title = plugin.generalConfiguration().liveDropItemTitle();
            title = replacePlaceholders(title, liveDrop);

            // Call of new ArrayList<>(...) due to the fact that the list may be immutable
            List<String> lore = new ArrayList<>(plugin.generalConfiguration().liveDropItemLore());
            for (int j = 0; j < lore.size(); j++) {
                lore.set(j, replacePlaceholders(lore.get(j), liveDrop));
            }

            Item item = (liveDrop == null) ? noLiveDropFoundItem : new SimpleItem(SimpleItemStack.builder(Material.PLAYER_HEAD)
                    .skullOwner(liveDrop.winnerUniqueId())
                    .withDisplayName(title)
                    .withLore(lore.toArray(new String[0])));
            normalBuilder.addIngredient((char) ('A' + i), item);
        }
    }

    private String replacePlaceholders(@NotNull String text, @Nullable LiveDrop liveDrop) {
        if (liveDrop == null) return text.replace("{time}", "N/A").replace("{player}", "N/A").replace("{item}", "N/A");
        text = text.replace("{time}", new SimpleDateFormat(plugin.generalConfiguration().dateFormat()).format(new Date(liveDrop.wonAt())));
        text = text.replace("{player}", "Username"); //TODO
        text = text.replace("{item}", "Item"); //TODO
        text = text.replace("{lootbox}", lootbox.getName());
        return text;
    }

    public void open(Player player) {
        Window window = Window.single().setViewer(player).setGui(gui).setTitle("Live Drops: " + lootbox.getName()).build(player);
        window.open();
        window.setCloseable(true);
    }

}
