package dev.mzcy.api.configuration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.mzcy.api.configuration.impl.LootboxAllSound;
import org.bukkit.Sound;
import java.io.IOException;

public class LootboxAllSoundSerializer extends JsonSerializer<LootboxAllSound> {
    @Override
    public void serialize(LootboxAllSound lootboxAllSound, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("sound", lootboxAllSound.sound().name().toUpperCase());
        jsonGenerator.writeNumberField("volume", lootboxAllSound.volume());
        jsonGenerator.writeNumberField("pitch", lootboxAllSound.pitch());
        jsonGenerator.writeEndObject();
    }
}
