package dev.mzcy.api.configuration.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import dev.mzcy.api.configuration.deserializer.SoundDeserializer;
import dev.mzcy.api.configuration.serializer.SoundSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.Sound;

@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxAllSound {

    @JsonSerialize(using = SoundSerializer.class)
    @JsonDeserialize(using = SoundDeserializer.class)
    Sound sound;

    @JsonSerialize(using = NumberSerializers.DoubleSerializer.class)
    @JsonDeserialize(using = NumberDeserializers.DoubleDeserializer.class)
    double volume;

    @JsonSerialize(using = NumberSerializers.DoubleSerializer.class)
    @JsonDeserialize(using = NumberDeserializers.DoubleDeserializer.class)
    double pitch;

    @JsonCreator
    public LootboxAllSound(@JsonProperty("sound") Sound sound, @JsonProperty("volume") double volume, @JsonProperty("pitch") double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

}
