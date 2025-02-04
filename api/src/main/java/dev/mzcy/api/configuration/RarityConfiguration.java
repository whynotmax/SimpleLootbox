package dev.mzcy.api.configuration;

import dev.mzcy.api.database.lootbox.model.item.rarity.LootboxItemRarity;
import net.kyori.adventure.text.Component;

public interface RarityConfiguration {

    LootboxItemRarity get(String name);

    LootboxItemRarity determine(double chance);

    Component displayName(LootboxItemRarity rarity);

}
