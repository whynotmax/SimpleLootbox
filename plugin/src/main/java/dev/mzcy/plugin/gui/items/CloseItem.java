package dev.mzcy.plugin.gui.items;

import dev.mzcy.api.utility.SimpleItemStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.window.Window;

public class CloseItem extends AbstractItem {

    public CloseItem() {
    }

    @Override
    public ItemProvider getItemProvider() {
        SimpleItemStack itemStack = SimpleItemStack.builder(Material.ITEM_FRAME);

        itemStack.withDisplayName(Component.text("§cClose"));
        itemStack.withLore(Component.text("§7Close this inventory"));

        return new ItemBuilder(itemStack);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        getWindows().stream().findFirst().ifPresent(Window::close);
    }
}
