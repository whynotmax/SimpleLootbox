package de.skysaint.inventory.impl.click;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

@Getter
@Setter
public class ClickAction {

    ClickType clickType;
    boolean cancel;
    int slot;
    BiConsumer<ItemStack, Player> consumer;

    public static ClickAction of(int slot, BiConsumer<ItemStack, Player> clickConsumer) {
        ClickAction clickAction = new ClickAction();
        clickAction.setSlot(slot);
        clickAction.setConsumer(clickConsumer);
        return clickAction;
    }

    public static ClickAction of(int slot, boolean cancel, BiConsumer<ItemStack, Player> clickConsumer) {
        ClickAction clickAction = new ClickAction();
        clickAction.setSlot(slot);
        clickAction.setCancel(cancel);
        clickAction.setConsumer(clickConsumer);
        return clickAction;
    }

    public static ClickAction of(ClickType clickType, int slot, boolean cancel, BiConsumer<ItemStack, Player> clickConsumer) {
        ClickAction clickAction = new ClickAction();
        clickAction.setClickType(clickType);
        clickAction.setSlot(slot);
        clickAction.setCancel(cancel);
        clickAction.setConsumer(clickConsumer);
        return clickAction;
    }

    public void onClick(int slot, ItemStack item, Player player) {
        if (slot != this.slot) {
            return;
        }
        consumer.accept(item, player);
    }

}
