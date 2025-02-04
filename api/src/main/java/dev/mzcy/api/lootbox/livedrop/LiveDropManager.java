package dev.mzcy.api.lootbox.livedrop;

import dev.mzcy.api.lootbox.livedrop.model.LiveDrop;

import java.util.List;

public interface LiveDropManager {

    void add(LiveDrop liveDrop);

    List<LiveDrop> liveDrops();
}
