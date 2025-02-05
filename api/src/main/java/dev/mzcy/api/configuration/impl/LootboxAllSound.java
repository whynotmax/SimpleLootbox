package dev.mzcy.api.configuration.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    Sound sound;
    float volume;
    float pitch;

    @JsonCreator
    public LootboxAllSound(@JsonProperty("sound") Sound sound, @JsonProperty("volume") float volume, @JsonProperty("pitch") float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

}
