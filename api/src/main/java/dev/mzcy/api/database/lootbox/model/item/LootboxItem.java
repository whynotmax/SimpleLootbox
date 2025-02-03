package dev.mzcy.api.database.lootbox.model.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LootboxItem {

    int id;
    ItemStack itemStack;
    double chance;

    boolean broadcast;
    double money;
    String[] commands;

}
