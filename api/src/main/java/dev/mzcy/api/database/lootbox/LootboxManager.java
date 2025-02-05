package dev.mzcy.api.database.lootbox;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import net.kyori.adventure.text.Component;

import java.util.Optional;

/**
 * Interface representing the manager for lootboxes.
 * Provides methods to get, save, delete, check existence, and retrieve random items from lootboxes.
 */
public interface LootboxManager {

        /**
         * Gets a lootbox by its name.
         *
         * @param name the name of the lootbox
         * @return an Optional containing the lootbox if found, otherwise empty
         */
        Optional<Lootbox> get(String name);

    /**
     * Gets a lootbox by its display name.
     *
     * @param displayName the display name of the lootbox
     * @return an Optional containing the lootbox if found, otherwise empty
     */
    Optional<Lootbox> get(Component displayName);

    /**
     * Gets a lootbox by its class type.
     *
     * @param clazz the class type of the lootbox
     * @return an Optional containing the lootbox if found, otherwise empty
     */
    Optional<Lootbox> get(Class<? extends Lootbox> clazz);

    /**
     * Saves a lootbox.
     *
     * @param lootbox the lootbox to save
     */
    void save(Lootbox lootbox);

    /**
     * Deletes a lootbox.
     *
     * @param lootbox the lootbox to delete
     */
    void delete(Lootbox lootbox);

    /**
     * Deletes a lootbox by its name.
     *
     * @param name the name of the lootbox to delete
     */
    void delete(String name);

    /**
     * Deletes a lootbox by its display name.
     *
     * @param displayName the display name of the lootbox to delete
     */
    void delete(Component displayName);

    /**
     * Deletes a lootbox by its class type.
     *
     * @param clazz the class type of the lootbox to delete
     */
    void delete(Class<? extends Lootbox> clazz);

    /**
     * Checks if a lootbox exists by its name.
     *
     * @param name the name of the lootbox
     * @return true if the lootbox exists, otherwise false
     */
    boolean exists(String name);

    /**
     * Retrieves a random item from a lootbox.
     *
     * @param lootbox the lootbox to retrieve a random item from
     * @return an Optional containing the random lootbox item if found, otherwise empty
     */
    Optional<LootboxItem> randomItem(Lootbox lootbox);

}