package dev.mzcy.api;

import dev.mzcy.api.configuration.DatabaseConfiguration;
import dev.mzcy.api.configuration.GeneralConfiguration;
import dev.mzcy.api.configuration.MessagesConfiguration;
import dev.mzcy.api.configuration.RarityConfiguration;
import dev.mzcy.api.database.DatabaseManager;
import dev.mzcy.api.lootbox.livedrop.LiveDropManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Abstract class representing the main API for the Lootboxes plugin.
 * This class extends JavaPlugin and provides abstract methods for
 * accessing various configurations and managers.
 */
public abstract class LootboxesAPI extends JavaPlugin {

    private static LootboxesAPI instance;

    /**
     * Constructor for LootboxesAPI.
     * Sets the static instance to this instance.
     */
    public LootboxesAPI() {
        instance = this;
    }

    /**
     * Gets the current instance of LootboxesAPI.
     *
     * @return the current instance of LootboxesAPI
     */
    public static LootboxesAPI instance() {
        return instance;
    }

    /**
     * Gets the database configuration.
     *
     * @return the database configuration
     */
    public abstract DatabaseConfiguration databaseConfiguration();

    /**
     * Gets the messages configuration.
     *
     * @return the messages configuration
     */
    public abstract MessagesConfiguration messagesConfiguration();

    /**
     * Gets the general configuration.
     *
     * @return the general configuration
     */
    public abstract GeneralConfiguration generalConfiguration();

    /**
     * Gets the rarity configuration.
     *
     * @return the rarity configuration
     */
    public abstract RarityConfiguration rarityConfiguration();

    /**
     * Gets the database manager.
     *
     * @return the database manager
     */
    public abstract DatabaseManager databaseManager();

    /**
     * Gets the live drop manager.
     *
     * @return the live drop manager
     */
    public abstract LiveDropManager liveDropManager();

}