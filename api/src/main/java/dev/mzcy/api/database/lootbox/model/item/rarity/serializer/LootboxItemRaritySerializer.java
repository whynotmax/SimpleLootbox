package dev.mzcy.api.database.lootbox.model.item.rarity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.mzcy.api.database.lootbox.model.item.rarity.LootboxItemRarity;

import java.io.IOException;

public class LootboxItemRaritySerializer extends JsonSerializer<LootboxItemRarity> {
    @Override
    public void serialize(LootboxItemRarity rarity, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", rarity.name());
        gen.writeNumberField("weight", rarity.weight());
        gen.writeNumberField("minChance", rarity.minChance());
        gen.writeNumberField("maxChance", rarity.maxChance());
        gen.writeEndObject();
    }
}

