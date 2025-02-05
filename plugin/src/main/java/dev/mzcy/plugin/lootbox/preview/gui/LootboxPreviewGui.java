package dev.mzcy.plugin.lootbox.preview.gui;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.gui.items.CloseItem;
import dev.mzcy.plugin.gui.items.NextPageItem;
import dev.mzcy.plugin.gui.items.PreviousPageItem;
import dev.mzcy.plugin.gui.items.preview.LootboxPreviewItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;

public class LootboxPreviewGui {

    LootboxesPlugin plugin;
    Item borderItem;
    PagedGui<?> gui;
    Lootbox lootbox;

    public LootboxPreviewGui(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.borderItem = new SimpleItem(SimpleItemStack.builder(Material.BLACK_STAINED_GLASS_PANE).withDisplayName("§r"));

        List<LootboxItem> items = new ArrayList<>(lootbox.items());
        List<Item> newItems = new ArrayList<>();

        for (LootboxItem itemStack : items) {
            newItems.add(new LootboxPreviewItem(plugin, itemStack));
        }

        this.gui = PagedGui.items()
                .setStructure(
                        "#########",
                        "#xxxxxxx#",
                        "#xxxxxxx#",
                        "#xxxxxxx#",
                        "##<#A#>##"
                )
                .addIngredient('#', borderItem)
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('<', new PreviousPageItem())
                .addIngredient('>', new NextPageItem())
                .addIngredient('A', new CloseItem())
                .setContent(newItems)
                .build();
    }

    public void open(Player player) {
        Window window = Window.single().setViewer(player).setGui(gui).setTitle("Preview: " + lootbox.name()).build(player);
        window.open();
        window.setCloseable(true);
    }

}
