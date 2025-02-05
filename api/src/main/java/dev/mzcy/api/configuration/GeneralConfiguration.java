package dev.mzcy.api.configuration;

/**
 * Interface representing the general configuration.
 * Provides methods for general aspects of the plugin.
 */
public interface GeneralConfiguration {

    /**
     * What is a live drop?
     * A live drop is a drop that gets saved to a local cache on the server.
     * Every player can see the last drop a player got.
     *
     * @return true if live drops are enabled, false otherwise
     */
    boolean liveDropsEnabled();

}
