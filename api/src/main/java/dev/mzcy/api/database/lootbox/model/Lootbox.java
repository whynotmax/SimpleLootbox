package dev.mzcy.api.database.lootbox.model;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

/**
 * Abstract class representing a lootbox in the database.
 * Provides methods to get and set the name, display name, material, and items of the lootbox.
 */
public abstract class Lootbox {

    /**
     * Gets the name of the lootbox.
     *
     * @return the name of the lootbox
     */
    public abstract String name();

    /**
     * Gets the display name of the lootbox.
     *
     * @return the display name of the lootbox
     */
    public abstract Component displayName();

    /**
     * Gets the material of the lootbox.
     *
     * @return the material of the lootbox
     */
    public abstract Material material();

    /**
     * Gets the list of items in the lootbox.
     *
     * @return the list of items in the lootbox
     */
    public abstract List<LootboxItem> items();

    /**
     * Sets the name of the lootbox.
     *
     * @param name the new name of the lootbox
     */
    public abstract void name(String name);

    /**
     * Sets the display name of the lootbox.
     *
     * @param displayName the new display name of the lootbox
     */
    public abstract void displayName(Component displayName);

    /**
     * Sets the material of the lootbox.
     *
     * @param material the new material of the lootbox
     */
    public abstract void material(Material material);

    /**
     * Sets the list of items in the lootbox.
     *
     * @param items the new list of items in the lootbox
     */
    public abstract void items(List<LootboxItem> items);

}