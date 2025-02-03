package dev.mzcy.api.database.lootbox;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public interface LootboxManager {

    Optional<Lootbox> get(String name);

    Optional<Lootbox> get(Component displayName);

    Optional<Lootbox> get(Class<? extends Lootbox> clazz);

    void save(Lootbox lootbox);

    void delete(Lootbox lootbox);

    void delete(String name);

    void delete(Component displayName);

    void delete(Class<? extends Lootbox> clazz);

    boolean exists(String name);

    Optional<LootboxItem> randomItem(Lootbox lootbox);

}
