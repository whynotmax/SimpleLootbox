package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.RarityConfiguration;
import dev.mzcy.api.database.lootbox.model.item.rarity.LootboxItemRarity;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;
import java.util.Map;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IRarityConfiguration extends Configuration implements RarityConfiguration {

    final Map<String, LootboxItemRarity> rarities;

    final Map<String, String> displayNames;

    public IRarityConfiguration() {
        this.rarities = new HashMap<>() {{
            put("COMMON", new LootboxItemRarity("COMMON", 1, 100.0, 60.0));
            put("UNCOMMON", new LootboxItemRarity("UNCOMMON", 2, 60.0, 30.0));
            put("RARE", new LootboxItemRarity("RARE", 3, 30.0, 7.0));
            put("EPIC", new LootboxItemRarity("EPIC", 4, 7.0, 2.0));
            put("LEGENDARY", new LootboxItemRarity("LEGENDARY", 5, 2.0, 0.5));
            put("MYTHIC", new LootboxItemRarity("MYTHIC", 6, 0.5, 0.1));
            put("UNIQUE", new LootboxItemRarity("UNIQUE", 7, 0.1, 0.0));
        }};

        this.displayNames = new HashMap<>() {{
            put("COMMON", "<gray><b>Common");
            put("UNCOMMON", "<green><b>Uncommon");
            put("RARE", "<blue><b>Rare");
            put("EPIC", "<purple><b>Epic");
            put("LEGENDARY", "<gold><b>Legendary");
            put("MYTHIC", "<red><b>Mythic");
            put("UNIQUE", "<dark_purple><b>Unique");
        }};
    }

    @Override
    public LootboxItemRarity get(String name) {
        return rarities.getOrDefault(name, null);
    }

    @Override
    public LootboxItemRarity determine(double chance) {
        return rarities.values().stream().filter(rarity -> chance >= rarity.minChance() && chance < rarity.maxChance()).findFirst().orElse(rarities.values().stream().findFirst().orElse(null));
    }

    @Override
    public Component displayName(LootboxItemRarity rarity) {
        return MiniMessage.miniMessage().deserializeOr(displayNames.getOrDefault(rarity.name().toUpperCase(), null), Component.text(rarity.name()));
    }
}
