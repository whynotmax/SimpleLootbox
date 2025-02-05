package dev.mzcy.api.configuration;

import dev.mzcy.api.database.lootbox.model.item.rarity.LootboxItemRarity;
import net.kyori.adventure.text.Component;

/**
 * Interface representing the configuration for lootbox item rarities.
 * Provides methods to get a rarity by name, determine a rarity by chance, and get the display name of a rarity.
 */
public interface RarityConfiguration {

    /**
     * Gets the rarity configuration by its name.
     *
     * @param name the name of the rarity
     * @return the lootbox item rarity
     */
    LootboxItemRarity get(String name);

    /**
     * Determines the rarity configuration based on a given chance.
     *
     * @param chance the chance to determine the rarity
     * @return the determined lootbox item rarity
     */
    LootboxItemRarity determine(double chance);

    /**
     * Gets the display name of the rarity.
     *
     * @param rarity the lootbox item rarity
     * @return the display name of the rarity
     */
    Component displayName(LootboxItemRarity rarity);

}