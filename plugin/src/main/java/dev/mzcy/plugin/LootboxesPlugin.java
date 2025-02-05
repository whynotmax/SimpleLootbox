package dev.mzcy.plugin;

import dev.mzcy.api.LootboxesAPI;
import dev.mzcy.configuration.Configuration;
import dev.mzcy.configuration.impl.IDatabaseConfiguration;
import dev.mzcy.configuration.impl.IGeneralConfiguration;
import dev.mzcy.configuration.impl.IMessagesConfiguration;
import dev.mzcy.configuration.impl.IRarityConfiguration;
import dev.mzcy.plugin.database.IDatabaseManager;
import dev.mzcy.plugin.lootbox.livedrop.ILiveDropManager;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import xyz.xenondevs.invui.InvUI;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxesPlugin extends LootboxesAPI {

    IDatabaseConfiguration databaseConfiguration;
    IMessagesConfiguration messagesConfiguration;
    IGeneralConfiguration generalConfiguration;
    IRarityConfiguration rarityConfiguration;
    IDatabaseManager databaseManager;
    ILiveDropManager liveDropManager;

    @Override
    public void onEnable() {
        databaseConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/database.yml", IDatabaseConfiguration.class);
        messagesConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/messages.yml", IMessagesConfiguration.class);
        generalConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/general.yml", IGeneralConfiguration.class);
        rarityConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/rarity.yml", IRarityConfiguration.class);
        databaseManager = new IDatabaseManager(this);
        liveDropManager = new ILiveDropManager();

        InvUI.getInstance().setPlugin(this);
    }

    @Override
    public void onDisable() {

    }
}
