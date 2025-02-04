package dev.mzcy.plugin;

import dev.mzcy.api.LootboxesAPI;
import dev.mzcy.configuration.Configuration;
import dev.mzcy.configuration.impl.IDatabaseConfiguration;
import dev.mzcy.configuration.impl.IMessagesConfiguration;
import dev.mzcy.configuration.impl.IRarityConfiguration;
import dev.mzcy.plugin.database.IDatabaseManager;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxesPlugin extends LootboxesAPI {

    IDatabaseConfiguration databaseConfiguration;
    IMessagesConfiguration messagesConfiguration;
    IRarityConfiguration rarityConfiguration;
    IDatabaseManager databaseManager;

    @Override
    public void onEnable() {
        databaseConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/database.yml", IDatabaseConfiguration.class);
        messagesConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/messages.yml", IMessagesConfiguration.class);
        rarityConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/rarity.yml", IRarityConfiguration.class);
        databaseManager = new IDatabaseManager(this);
    }

    @Override
    public void onDisable() {

    }
}
