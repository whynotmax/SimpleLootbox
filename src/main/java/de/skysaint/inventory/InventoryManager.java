package de.skysaint.inventory;

import de.skysaint.SkySaintPlugin;
import de.skysaint.inventory.impl.IInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements Listener {

    SkySaintPlugin plugin;
    Map<Player, IInventory> openInventories;

    public InventoryManager(SkySaintPlugin plugin) {
        this.plugin = plugin;
        this.openInventories = new HashMap<>();
    }

    public void openInventory(Player player, IInventory inventory) {
        player.openInventory(inventory.getInventory());
        openInventories.put(player, inventory);
        inventory.open(player);
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (openInventories.containsKey(player)) {
                IInventory inventory = openInventories.remove(player);
                inventory.close(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (openInventories.containsKey(player)) {
                IInventory inventory = openInventories.get(player);
                inventory.handleClickEvent(event);
            }
        }
    }

}
