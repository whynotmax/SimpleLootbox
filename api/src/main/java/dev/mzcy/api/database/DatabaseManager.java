package dev.mzcy.api.database;

import dev.mzcy.api.database.lootbox.LootboxManager;

/**
 * Interface representing the database manager.
 * Provides a method to access the lootbox manager.
 */
public interface DatabaseManager {

    /**
     * Gets the lootbox manager.
     *
     * @return the lootbox manager
     */
    LootboxManager lootboxManager();

}