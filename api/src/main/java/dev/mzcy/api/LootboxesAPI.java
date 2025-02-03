package dev.mzcy.api;

import dev.mzcy.api.configuration.DatabaseConfiguration;
import dev.mzcy.api.configuration.MessagesConfiguration;
import dev.mzcy.api.database.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LootboxesAPI extends JavaPlugin {

    private static LootboxesAPI instance;

    public LootboxesAPI() {
        instance = this;
    }

    public static LootboxesAPI instance() {
        return instance;
    }

    public abstract DatabaseConfiguration databaseConfiguration();

    public abstract MessagesConfiguration messagesConfiguration();

    public abstract DatabaseManager databaseManager();

}
