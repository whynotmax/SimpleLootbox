package dev.mzcy.api.database.lootbox.model;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public interface Lootbox {

    String name();

    Component displayName();

    Material material();

    List<LootboxItem> items();

}
