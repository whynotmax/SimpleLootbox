package dev.mzcy.plugin.lootbox.edit.gui;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.gui.items.CloseItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Arrays;

public class LootboxEditMainGui {

    private final LootboxesPlugin plugin;
    private final Item borderItem;
    private final Gui gui;
    private final Lootbox lootbox;

    public LootboxEditMainGui(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.borderItem = new SimpleItem(SimpleItemStack.builder(Material.BLACK_STAINED_GLASS_PANE).withDisplayName("§r"));
        this.gui = createGui();
    }

    private Gui createGui() {
        return Gui.normal()
                .setStructure(
                        "####I####",
                        "#.......#",
                        "#.A.B.D.#",
                        "#..E.F..#",
                        "#.......#",
                        "####C####"
                )
                .addIngredient('#', borderItem)
                .addIngredient('C', new CloseItem())
                .addIngredient('A', createItem(Material.NAME_TAG, "§7Edit display name", "§7Click to edit the display name of the lootbox.", "§8• §7Current display name: §r" + lootbox.getDisplayName()))
                .addIngredient('B', createItem(Material.CHEST, "§7Edit items", "§7Click to edit the items of the lootbox.", "§8• §7Current amount of items: §e" + lootbox.getItems().size()))
                .addIngredient('D', createItem(Material.BOOK, "§7Edit animation", "§7Click to edit the animation of the lootbox.", "§8• §7Current animation: §eTODO"))
                .addIngredient('E', createItem(Material.CLOCK, "§7Edit cooldown", "§7Click to edit the cooldown of the lootbox.", "§8• §7Current cooldown: §eTODO"))
                .addIngredient('F', createItem(Material.STRUCTURE_VOID, "§cDelete lootbox", "§7Click to delete the lootbox.", "§8• §cThis action cannot be undone!"))
                .addIngredient('I', ItemProvider.EMPTY)
                .build();
    }

    private SimpleItem createItem(Material material, String displayName, String... lore) {
        return new SimpleItem(SimpleItemStack.builder(material).withDisplayName(displayName).withLore(Arrays.stream(lore).map(Component::text).toArray(Component[]::new)));
    }

    public void open(Player player) {
        Window window = Window.single().setViewer(player).setGui(gui).setTitle("Editing lootbox " + lootbox.getName()).build(player);
        window.open();
        window.setCloseable(true);
    }
}