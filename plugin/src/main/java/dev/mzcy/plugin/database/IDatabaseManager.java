package dev.mzcy.plugin.database;

import dev.mzcy.api.database.DatabaseManager;
import dev.mzcy.api.database.lootbox.LootboxManager;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.database.lootbox.ILootboxManager;
import dev.mzcy.plugin.database.lootbox.repository.LootboxRepository;
import eu.koboo.en2do.Credentials;
import eu.koboo.en2do.MongoManager;
import eu.koboo.en2do.SettingsBuilder;

public class IDatabaseManager implements DatabaseManager {

    MongoManager manager;

    LootboxRepository lootboxRepository;
    ILootboxManager lootboxManager;

    public IDatabaseManager(LootboxesPlugin plugin) {
        this.manager = new MongoManager(Credentials.of(plugin.databaseConfiguration().getMongoConnectionUri(), plugin.databaseConfiguration().getMongoDatabase()),
                new SettingsBuilder().disableMongoDBLogger().collectionPrefix(plugin.databaseConfiguration().getMongoCollectionPrefix()));

        this.lootboxRepository = manager.create(LootboxRepository.class);
        this.lootboxManager = new ILootboxManager(lootboxRepository);
    }

    @Override
    public LootboxManager lootboxManager() {
        return lootboxManager;
    }
}
