package dev.mzcy.api.database.lootbox.model.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

/**
 * Class representing an item in a lootbox.
 * Contains information about the item, its chance of being obtained, and additional properties.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxItem {

    /**
     * The unique identifier of the lootbox item.
     */
    int id;

    /**
     * The item stack representing the lootbox item.
     */
    ItemStack itemStack;

    /**
     * The chance of obtaining the lootbox item.
     */
    double chance;

    /**
     * Whether the lootbox item should be broadcasted.
     */
    boolean broadcast;

    /**
     * The amount of money associated with the lootbox item.
     */
    double money;

    /**
     * The commands to be executed when the lootbox item is obtained.
     */
    String[] commands;

}