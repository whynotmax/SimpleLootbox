package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.GeneralConfiguration;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IGeneralConfiguration extends Configuration implements GeneralConfiguration {

    boolean enableLiveDrops;
    String dateFormat;
    String liveDropItemTitle;
    List<String> liveDropItemLore;

    public IGeneralConfiguration() {
        enableLiveDrops = true;
        dateFormat = "dd.MM.yyyy HH:mm:ss";
        liveDropItemTitle = "{time}";
        liveDropItemLore = List.of(
                "§r",
                "§8• §e{player} §7received",
                "§8• §e{item} §7from",
                "§8• §e{lootbox} §7at",
                "§8• §e{time}",
                "§r"
        );
    }

    /**
     * What is a live drop?
     * A live drop is a drop that gets saved to a local cache on the server.
     * Every player can see the last drop a player got.
     *
     * @return true if live drops are enabled, false otherwise
     */
    @Override
    public boolean liveDropsEnabled() {
        return enableLiveDrops;
    }

    @Override
    public String dateFormat() {
        return dateFormat;
    }

    @Override
    public String liveDropItemTitle() {
        return liveDropItemTitle;
    }

    @Override
    public List<String> liveDropItemLore() {
        return liveDropItemLore;
    }
}
