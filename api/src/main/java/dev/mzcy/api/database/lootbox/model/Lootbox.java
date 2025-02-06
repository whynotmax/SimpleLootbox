package dev.mzcy.api.database.lootbox.model;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import eu.koboo.en2do.repository.entity.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

/**
 * Abstract class representing a lootbox in the database.
 * Provides methods to get and set the name, display name, material, and items of the lootbox.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Lootbox {

    @Id
    String name;
    Component displayName;
    Material material;
    List<LootboxItem> items;

}