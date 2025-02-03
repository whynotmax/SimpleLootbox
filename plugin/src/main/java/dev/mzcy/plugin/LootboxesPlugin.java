package dev.mzcy.plugin;

import dev.mzcy.api.LootboxesAPI;
import dev.mzcy.configuration.impl.IDatabaseConfiguration;
import dev.mzcy.configuration.impl.IMessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxesPlugin extends LootboxesAPI {

    IDatabaseConfiguration databaseConfiguration;
    IMessagesConfiguration messagesConfiguration;

    @Override
    public void onEnable() {
        databaseConfiguration = IDatabaseConfiguration.load(getDataFolder().getAbsolutePath() + "/database.yml", IDatabaseConfiguration.class);
        messagesConfiguration = IMessagesConfiguration.load(getDataFolder().getAbsolutePath() + "/messages.yml", IMessagesConfiguration.class);
    }

    @Override
    public void onDisable() {

    }
}
