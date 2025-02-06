package dev.mzcy.plugin.database.lootbox;

import dev.mzcy.api.database.lootbox.LootboxManager;
import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import dev.mzcy.plugin.database.lootbox.repository.LootboxRepository;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ILootboxManager implements LootboxManager {

    Map<String, Lootbox> lootboxes;
    LootboxRepository repository;

    public ILootboxManager(LootboxRepository repository) {
        this.repository = repository;
        this.lootboxes = new HashMap<>(repository.findAll().stream().map(lootbox -> Map.entry(lootbox.getName(), lootbox)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    public Optional<Lootbox> get(String name) {
        return Optional.ofNullable(lootboxes.get(name));
    }

    @Override
    public Optional<Lootbox> get(Component displayName) {
        return lootboxes.values().stream().filter(lootbox -> lootbox.getDisplayName().equals(displayName)).findFirst();
    }

    @Override
    public Optional<Lootbox> get(Class<? extends Lootbox> clazz) {
        return lootboxes.values().stream().filter(clazz::isInstance).findFirst();
    }

    @Override
    public void save(Lootbox lootbox) {
        lootboxes.put(lootbox.getName(), lootbox);
        repository.save(lootbox);
    }

    @Override
    public void delete(Lootbox lootbox) {
        lootboxes.remove(lootbox.getName());
        repository.delete(lootbox);
    }

    @Override
    public void delete(String name) {
        Lootbox lootbox = lootboxes.remove(name);
        if (lootbox != null) {
            repository.delete(lootbox);
        }
    }

    @Override
    public void delete(Component displayName) {
        Lootbox lootbox = lootboxes.values().stream().filter(l -> l.getDisplayName().equals(displayName)).findFirst().orElse(null);
        if (lootbox != null) {
            lootboxes.remove(lootbox.getName());
            repository.delete(lootbox);
        }
    }

    @Override
    public void delete(Class<? extends Lootbox> clazz) {
        Lootbox lootbox = lootboxes.values().stream().filter(clazz::isInstance).findFirst().orElse(null);
        if (lootbox != null) {
            lootboxes.remove(lootbox.getName());
            repository.delete(lootbox);
        }
    }

    @Override
    public boolean exists(String name) {
        return lootboxes.containsKey(name);
    }

    @Override
    public Optional<LootboxItem> randomItem(Lootbox lootbox) {
        //TODO: Implement random item (with chances)
        return Optional.empty();
    }
}
