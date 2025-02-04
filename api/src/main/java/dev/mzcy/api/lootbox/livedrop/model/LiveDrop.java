package dev.mzcy.api.lootbox.livedrop.model;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LiveDrop {

    UUID winnerUniqueId;
    LootboxItem wonItem;
    long wonAt;

    public LiveDrop(UUID winnerUniqueId, LootboxItem wonItem) {
        this.winnerUniqueId = winnerUniqueId;
        this.wonItem = wonItem;
        this.wonAt = System.currentTimeMillis();
    }

}
