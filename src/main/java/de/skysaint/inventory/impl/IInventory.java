package de.skysaint.inventory.impl;

import de.skysaint.SkySaintPlugin;
import de.skysaint.inventory.impl.click.ClickAction;
import de.skysaint.inventory.impl.pagination.Pagination;
import de.skysaint.utility.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class IInventory {

    SkySaintPlugin plugin;

    Inventory inventory;
    InventoryType type;

    String title;
    int size;

    @Setter
    int page;
    Pagination<ItemStack> pagination;
    
    Map<Player, BukkitTask> updateTasks;

    Map<Integer, ClickAction> clickActions;

    public IInventory(SkySaintPlugin plugin, String title, int size) {
        this.plugin = plugin;
        this.title = title;
        this.size = size;
        this.type = InventoryType.CHEST;
        this.page = 0;
        this.clickActions = new HashMap<>();
        this.inventory = plugin.getServer().createInventory(null, size, title);
    }

    public IInventory(SkySaintPlugin plugin, String title, InventoryType type) {
        this.plugin = plugin;
        this.title = title;
        this.size = type.getDefaultSize();
        this.type = type;
        this.page = 0;
        this.clickActions = new HashMap<>();
        this.inventory = plugin.getServer().createInventory(null, type, title);
    }

    public void usePagination(int pageSize) {
        this.pagination = new Pagination<>();
        this.pagination.setPageSize(pageSize);
        this.pagination.setPages(new ArrayList<>());

        this.initializePageItems();
    }
    
    public void defaultInventory() {
        this.fill(new ItemBuilder(Material.STAINED_GLASS_PANE).setDataId(7).setName("§7"));
        this.fillBorders(new ItemBuilder(Material.STAINED_GLASS_PANE).setDataId(15).setName("§7"));
    }

    public void fill(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
        }
    }

    public void fill(ItemStack itemStack, ClickAction clickAction) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
    }

    public void fillBorders(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i < 9 || i >= inventory.getSize() - 9 || i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, itemStack);
            }
        }
    }

    public void fillBorders(ItemStack itemStack, ClickAction clickAction) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i < 9 || i >= inventory.getSize() - 9 || i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, itemStack);
                clickActions.put(i, clickAction);
            }
        }
    }

    public void fillRow(int row, ItemStack itemStack) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(row * 9 + i, itemStack);
        }
    }

    public void fillRow(int row, ItemStack itemStack, ClickAction clickAction) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(row * 9 + i, itemStack);
            clickActions.put(row * 9 + i, clickAction);
        }
    }

    public void fillColumn(int column, ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i += 9) {
            inventory.setItem(column + i, itemStack);
        }
    }

    public void fillColumn(int column, ItemStack itemStack, ClickAction clickAction) {
        for (int i = 0; i < inventory.getSize(); i += 9) {
            inventory.setItem(column + i, itemStack);
            clickActions.put(column + i, clickAction);
        }
    }

    public void fillDiagonal(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i += 10) {
            inventory.setItem(i, itemStack);
        }
        for (int i = 8; i < inventory.getSize() - 1; i += 8) {
            inventory.setItem(i, itemStack);
        }
    }

    public void fillDiagonal(ItemStack itemStack, ClickAction clickAction) {
        for (int i = 0; i < inventory.getSize(); i += 10) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
        for (int i = 8; i < inventory.getSize() - 1; i += 8) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
    }

    public void fillCross(ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
        }
        for (int i = 1; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
        }
        for (int i = 3; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
        }
        for (int i = 5; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
        }
        for (int i = 7; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
        }
    }

    public void fillCross(ItemStack itemStack, ClickAction clickAction) {
        for (int i = 0; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
        for (int i = 1; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
        for (int i = 3; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
        for (int i = 5; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
        for (int i = 7; i < inventory.getSize(); i += 9) {
            inventory.setItem(i, itemStack);
            clickActions.put(i, clickAction);
        }
    }

    public void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }
    
    public void setItem(int slot, ItemStack itemStack, ClickAction clickAction) {
        inventory.setItem(slot, itemStack);
        clickActions.put(slot, clickAction);
    }
    
    public void enableUpdateTask(Player player, int ticks) {
        updateTasks.put(player, Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (!player.getOpenInventory().getTopInventory().getTitle().equals(title)) {
                BukkitTask task = updateTasks.get(player);
                if (task != null) {
                    task.cancel();
                    updateTasks.remove(player);
                }
                return;
            }
            update(this.page);
        }, 0L, ticks));
    }
    
    public void disableUpdateTask(Player player) {
        BukkitTask task = updateTasks.get(player);
        if (task != null) {
            task.cancel();
            updateTasks.remove(player);
        }
    }

    public void handleClickEvent(InventoryClickEvent event) {
        if (clickActions.containsKey(event.getSlot())) {
            ClickAction clickAction = clickActions.get(event.getSlot());
            clickAction.onClick(event.getRawSlot(), event.getCurrentItem(), (Player) event.getWhoClicked());
            return;
        }
        event.setCancelled(true);
    }

    /**
     * Use this method to initialize the items of the inventory IF you are using pagination.
     */
    public abstract void initializePageItems();

    /**
     * Initialize the inventory here. This method is called when the inventory is created.
     *
     * @param player The player that opened the inventory
     */
    public abstract void open(Player player);

    /**
     * This method is called when the inventory is closed.
     *
     * @param event The InventoryCloseEvent
     */
    public abstract void close(InventoryCloseEvent event);

    /**
     * This method is called when the inventory is updated.
     * @param newPage The new page of the inventory
     */
    public abstract void update(int newPage);


}
