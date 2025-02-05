package dev.mzcy.api.lootbox.livedrop.model;

import dev.mzcy.api.database.lootbox.model.item.LootboxItem;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Class representing a live drop in the lootbox system.
 * Contains information about the winner, the item won, and the time of winning.
 */
@Getter
@Accessors(fluent = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LiveDrop {

    /**
     * The unique ID of the winner.
     */
    UUID winnerUniqueId;

    /**
     * The item won by the winner.
     */
    LootboxItem wonItem;

    /**
     * The timestamp when the item was won.
     */
    long wonAt;

    /**
     * Constructs a new LiveDrop instance.
     *
     * @param winnerUniqueId the unique ID of the winner
     * @param wonItem        the item won by the winner
     */
    public LiveDrop(UUID winnerUniqueId, LootboxItem wonItem) {
        this.winnerUniqueId = winnerUniqueId;
        this.wonItem = wonItem;
        this.wonAt = System.currentTimeMillis();
    }

}