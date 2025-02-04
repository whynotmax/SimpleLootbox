package dev.mzcy.plugin.lootbox.livedrop.gui;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.lootbox.livedrop.model.LiveDrop;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.plugin.LootboxesPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

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
            Item item = (liveDrop == null) ? noLiveDropFoundItem : new SimpleItem(SimpleItemStack.builder(Material.PLAYER_HEAD)
                    .skullOwner(liveDrop.winnerUniqueId())
                    .withDisplayName("TODO")
                    .withLore("TODO"));
            normalBuilder.addIngredient((char) ('A' + i), item);
        }
    }

    public void open(Player player) {
        Window window = Window.single().setViewer(player).setGui(gui).setTitle("Live Drops: " + lootbox.name()).build(player);
        window.open();
        window.setCloseable(true);
    }

}
