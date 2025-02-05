package dev.mzcy.plugin.database.lootbox.model;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ILootbox extends Lootbox {

    String name;
    Component displayName;
    Material material;
    List<LootboxItem> items;

    @Override
    public String name() {
        return name;
    }

    @Override
    public Component displayName() {
        return displayName;
    }

    @Override
    public Material material() {
        return material;
    }

    @Override
    public List<LootboxItem> items() {
        return items;
    }

    @Override
    public void name(String name) {
        this.name = name;
    }

    @Override
    public void displayName(Component displayName) {
        this.displayName = displayName;
    }

    @Override
    public void material(Material material) {
        this.material = material;
    }

    @Override
    public void items(List<LootboxItem> items) {
        this.items = items;
    }
}
