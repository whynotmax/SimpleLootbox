package dev.mzcy.api.database.lootbox.model.item.rarity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.mzcy.api.database.lootbox.model.item.rarity.serializer.LootboxItemRaritySerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * Class representing the rarity of a lootbox item.
 * Contains information about the name, weight, and chance range of the rarity.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonSerialize(using = LootboxItemRaritySerializer.class)
public class LootboxItemRarity {

    /**
     * The name of the rarity.
     */
    String name;

    /**
     * The weight of the rarity.
     */
    int weight;

    /**
     * The minimum chance of obtaining an item with this rarity.
     */
    double minChance;

    /**
     * The maximum chance of obtaining an item with this rarity.
     */
    double maxChance;

    /**
     * Constructs a new LootboxItemRarity instance.
     * @param name the name of the rarity
     * @param weight the weight of the rarity
     * @param minChance the minimum chance of obtaining an item with this rarity
     * @param maxChance the maximum chance of obtaining an item with this rarity
     */
    @JsonCreator
    public LootboxItemRarity(@JsonProperty("name") String name, @JsonProperty("weight") int weight, @JsonProperty("minChance") double minChance, @JsonProperty("maxChance") double maxChance) {
        this.name = name;
        this.weight = weight;
        this.minChance = minChance;
        this.maxChance = maxChance;
    }

}