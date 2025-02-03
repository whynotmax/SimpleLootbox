package dev.mzcy.plugin.command.impl;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.api.utility.Utility;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.command.model.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class LootboxCommand extends SimpleCommand {

    public LootboxCommand(LootboxesPlugin plugin) {
        super("lootbox", List.of("lb", "slb", "simplelb", "simplelootbox", "simplelootboxes", "lootboxes"), plugin);
    }

    @Override
    public void run(CommandSender sender, String[] arguments) {
        if (sender instanceof ConsoleCommandSender console) {
            if (arguments.length == 0) {
                console.sendMessage("Usage: /lootbox <give> <player/all> <lootbox> <amount>");
                return;
            }
            if (arguments[0].equals("give")) {
                if (arguments.length < 4) {
                    console.sendMessage("Usage: /lootbox give <player> <lootbox> <amount>");
                    return;
                }
                if (arguments[1].equalsIgnoreCase("all")) {
                    Optional<Lootbox> lootbox = plugin.databaseManager().lootboxManager().get(arguments[2]);
                    if (lootbox.isEmpty()) {
                        console.sendMessage("Lootbox not found.");
                        return;
                    }
                    int amount;
                    try {
                        amount = Integer.parseInt(arguments[3]);
                    } catch (NumberFormatException e) {
                        console.sendMessage("Invalid amount.");
                        return;
                    }
                    if (!Utility.inRange(amount, 1, 64)) {
                        console.sendMessage("Amount must be between 1 and 64.");
                        return;
                    }
                    SimpleItemStack item = SimpleItemStack.builder(lootbox.get().material());
                    item.withAmount(amount);
                    item.withDisplayName(lootbox.get().displayName());
                    for (Player target : console.getServer().getOnlinePlayers()) {
                        target.getInventory().addItem(item);
                        target.sendMessage(
                                MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("lootbox.give", plugin.messagesConfiguration().getPrefix(), PlainTextComponentSerializer.plainText().serialize(lootbox.get().displayName()), amount, "CONSOLE"))
                        );
                    }
                    console.sendMessage("Gave lootbox to all players.");
                    return;
                }
                Player target = console.getServer().getPlayerExact(arguments[1]);
                if (target == null) {
                    console.sendMessage("Player not found.");
                    return;
                }
                Optional<Lootbox> lootbox = plugin.databaseManager().lootboxManager().get(arguments[2]);
                if (lootbox.isEmpty()) {
                    console.sendMessage("Lootbox not found.");
                    return;
                }
                int amount;
                try {
                    amount = Integer.parseInt(arguments[3]);
                } catch (NumberFormatException e) {
                    console.sendMessage("Invalid amount.");
                    return;
                }
                if (!Utility.inRange(amount, 1, 64)) {
                    console.sendMessage("Amount must be between 1 and 64.");
                    return;
                }
                SimpleItemStack item = SimpleItemStack.builder(lootbox.get().material());
                item.withAmount(amount);
                item.withDisplayName(lootbox.get().displayName());
                //TODO: Add lore to item
                target.getInventory().addItem(item);
                target.sendMessage(
                        MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("lootbox.give", plugin.messagesConfiguration().getPrefix(), PlainTextComponentSerializer.plainText().serialize(lootbox.get().displayName()), amount, "CONSOLE"))
                );
                console.sendMessage("Gave lootbox to player.");
            } else {
                console.sendMessage("Usage: /lootbox <give> <player> <lootbox> <amount>");
            }
            return;
        }
        Player player = (Player) sender;

    }
}
