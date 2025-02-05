package dev.mzcy.api.configuration;

import dev.mzcy.api.configuration.impl.LootboxAllSound;

import java.util.List;

/**
 * Interface representing the general configuration.
 * Provides methods for general aspects of the plugin.
 */
public interface GeneralConfiguration {

    /**
     * What is a live drop?
     * A live drop is a drop that gets saved to a local cache on the server.
     * Every player can see the last drop a player got.
     *
     * @return true if live drops are enabled, false otherwise
     */
    boolean liveDropsEnabled();

    /**
     * Gets the date format for the plugin.
     *
     * @return the date format
     */
    String dateFormat();

    /**
     * Gets the title for the live drop item.
     *
     * @return the title for the live drop item
     */
    String liveDropItemTitle();

    /**
     * Gets the lore for the live drop item.
     *
     * @return the lore for the live drop item
     */
    List<String> liveDropItemLore();

    /**
     * Gets the sound for the lootbox all broadcasts.
     *
     * @return the sound for the lootbox all broadcasts
     */
    LootboxAllSound lootboxAllSound();

}
