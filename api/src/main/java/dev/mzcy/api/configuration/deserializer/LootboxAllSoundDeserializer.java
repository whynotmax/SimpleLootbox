package dev.mzcy.api.configuration.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.mzcy.api.configuration.impl.LootboxAllSound;
import org.bukkit.Sound;

import java.io.IOException;

public class LootboxAllSoundDeserializer extends JsonDeserializer<LootboxAllSound> {
    @Override
    public LootboxAllSound deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // Read the JSON as a tree structure
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Extract individual fields
        String soundString = node.get("sound").asText();  // Sound name as string
        Sound sound = Sound.valueOf(soundString);         // Convert to Sound enum

        float volume = node.get("volume").floatValue();      // Get volume value
        float pitch = node.get("pitch").floatValue();        // Get pitch value

        // Create and return the LootboxAllSound instance
        return new LootboxAllSound(sound, volume, pitch);
    }
}
