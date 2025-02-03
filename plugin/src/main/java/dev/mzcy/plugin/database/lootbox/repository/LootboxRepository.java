package dev.mzcy.plugin.database.lootbox.repository;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;

@Collection("lootboxes")
public interface LootboxRepository extends Repository<Lootbox, String> {
}


