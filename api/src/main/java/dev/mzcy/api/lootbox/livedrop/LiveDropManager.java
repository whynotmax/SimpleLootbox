package dev.mzcy.api.lootbox.livedrop;

import dev.mzcy.api.lootbox.livedrop.model.LiveDrop;

import java.util.List;

/**
 * Interface representing the manager for live drops.
 * Provides methods to add a live drop and retrieve the list of live drops.
 */
public interface LiveDropManager {

    /**
     * Adds a live drop to the manager.
     *
     * @param liveDrop the live drop to add
     */
    void add(LiveDrop liveDrop);

    /**
     * Retrieves the list of live drops.
     *
     * @return the list of live drops
     */
    List<LiveDrop> liveDrops();
}