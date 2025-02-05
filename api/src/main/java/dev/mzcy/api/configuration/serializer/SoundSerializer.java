package dev.mzcy.api.configuration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.Sound;
import java.io.IOException;

public class SoundSerializer extends JsonSerializer<Sound> {
    @Override
    public void serialize(Sound sound, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (sound != null) {
            jsonGenerator.writeString(sound.name());
        }
    }
}
