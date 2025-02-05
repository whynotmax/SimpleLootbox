package dev.mzcy.plugin.gui.items.preview;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import dev.mzcy.api.database.lootbox.model.item.rarity.LootboxItemRarity;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.plugin.LootboxesPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class LootboxPreviewItem extends AbstractItem {

    LootboxesPlugin plugin;
    LootboxItem lootboxItem;

    public LootboxPreviewItem(LootboxesPlugin plugin, LootboxItem lootboxItem) {
        this.plugin = plugin;
        this.lootboxItem = lootboxItem;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = lootboxItem.getItemStack();
        SimpleItemStack item = SimpleItemStack.builder(itemStack);
        item.withDisplayName(Component.text("§7" + item.getAmount() + "x ").append(item.displayName()));
        List<Component> lore = (item.lore() == null) ? new ArrayList<>() : new ArrayList<>(item.lore());
        lore.add(Component.text("§r"));
        LootboxItemRarity rarity = plugin.rarityConfiguration().determine(lootboxItem.getChance());
        lore.add(plugin.rarityConfiguration().displayName(rarity).append(Component.text(" §8(§7" + lootboxItem.getChance() + "%§8)")));
        item.withLore(lore.toArray(new Component[0]));

        return new ItemBuilder(item);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        //TODO: Implement lootbox item preview
    }
}
