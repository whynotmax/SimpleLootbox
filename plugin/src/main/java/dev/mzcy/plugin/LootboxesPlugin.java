package dev.mzcy.plugin;

import dev.mzcy.api.LootboxesAPI;
import dev.mzcy.configuration.Configuration;
import dev.mzcy.configuration.impl.IDatabaseConfiguration;
import dev.mzcy.configuration.impl.IGeneralConfiguration;
import dev.mzcy.configuration.impl.IMessagesConfiguration;
import dev.mzcy.configuration.impl.IRarityConfiguration;
import dev.mzcy.plugin.command.CommandManager;
import dev.mzcy.plugin.database.IDatabaseManager;
import dev.mzcy.plugin.lootbox.livedrop.ILiveDropManager;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
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
    CommandManager commandManager;
    boolean fancyHologramsEnabled;

    @Override
    public void onEnable() {
        databaseConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/database.yml", IDatabaseConfiguration.class);
        messagesConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/messages.yml", IMessagesConfiguration.class);
        generalConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/general.yml", IGeneralConfiguration.class);
        rarityConfiguration = Configuration.load(getDataFolder().getAbsolutePath() + "/rarity.yml", IRarityConfiguration.class);
        databaseManager = new IDatabaseManager(this);
        liveDropManager = new ILiveDropManager();
        commandManager = new CommandManager(this);

        fancyHologramsEnabled = Bukkit.getPluginManager().isPluginEnabled("FancyHolograms");
        if (!fancyHologramsEnabled) {
            getLogger().warning("FancyHolograms is not enabled! This means that we can't spawn any holograms at this time (maybe in the next update) as I don't want to code my own hologram implementation at this moment. Sorry!");
        }

        InvUI.getInstance().setPlugin(this);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean fancyHologramsEnabled() {
        return fancyHologramsEnabled;
    }
}
