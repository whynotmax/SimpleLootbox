package dev.mzcy.plugin.lootbox.livedrop;

import dev.mzcy.api.lootbox.livedrop.LiveDropManager;
import dev.mzcy.api.lootbox.livedrop.model.LiveDrop;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ILiveDropManager implements LiveDropManager {

    List<LiveDrop> liveDrops;

    public ILiveDropManager() {
        this.liveDrops = new ArrayList<>(7);
    }

    @Override
    public void add(LiveDrop liveDrop) {
        if (this.liveDrops.size() == 7) {
            this.liveDrops.remove(this.liveDrops.size() - 1);
        }
        this.liveDrops.add(0, liveDrop);
    }
}
