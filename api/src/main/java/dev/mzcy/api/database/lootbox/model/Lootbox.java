package dev.mzcy.api.database.lootbox.model;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

/**
 * Interface representing a lootbox in the database.
 * Provides methods to get and set the name, display name, material, and items of the lootbox.
 */
public interface Lootbox {

    /**
     * Gets the name of the lootbox.
     *
     * @return the name of the lootbox
     */
    String name();

    /**
     * Gets the display name of the lootbox.
     *
     * @return the display name of the lootbox
     */
    Component displayName();

    /**
     * Gets the material of the lootbox.
     *
     * @return the material of the lootbox
     */
    Material material();

    /**
     * Gets the list of items in the lootbox.
     *
     * @return the list of items in the lootbox
     */
    List<LootboxItem> items();

    /**
     * Sets the name of the lootbox.
     *
     * @param name the new name of the lootbox
     */
    void name(String name);

    /**
     * Sets the display name of the lootbox.
     *
     * @param displayName the new display name of the lootbox
     */
    void displayName(Component displayName);

    /**
     * Sets the material of the lootbox.
     *
     * @param material the new material of the lootbox
     */
    void material(Material material);

    /**
     * Sets the list of items in the lootbox.
     *
     * @param items the new list of items in the lootbox
     */
    void items(List<LootboxItem> items);

}