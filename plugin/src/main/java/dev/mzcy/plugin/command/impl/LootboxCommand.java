package dev.mzcy.plugin.command.impl;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.api.utility.Utility;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.command.model.SimpleCommand;
import dev.mzcy.api.utility.SimpleUUIDFetcher;
import dev.mzcy.plugin.utility.SkinRender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class LootboxCommand extends SimpleCommand {

    public LootboxCommand(LootboxesPlugin plugin) {
        super("lootbox", List.of("lb", "slb", "simplelb", "simplelootbox", "simplelootboxes", "lootboxes"), plugin);
    }

    @Override
    public void run(CommandSender sender, String[] arguments) {
        if (sender instanceof ConsoleCommandSender console) {
            handleConsoleCommand(console, arguments);
        } else {
            Player player = (Player) sender;
            // Handle player command if needed
        }
    }

    private void handleConsoleCommand(ConsoleCommandSender console, String[] arguments) {
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
                giveLootboxToAll(console, arguments[2], arguments[3]);
            } else {
                giveLootboxToPlayer(console, arguments[1], arguments[2], arguments[3]);
            }
        } else {
            console.sendMessage("Usage: /lootbox <give> <player> <lootbox> <amount>");
        }
    }

    private void giveLootboxToAll(ConsoleCommandSender console, String lootboxName, String amountStr) {
        Optional<Lootbox> lootbox = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootbox.isEmpty()) {
            console.sendMessage("Lootbox not found.");
            return;
        }
        int amount = parseAmount(console, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootbox.get(), amount);
        for (Player target : console.getServer().getOnlinePlayers()) {
            target.getInventory().addItem(item);
            target.sendMessage(createLootboxMessage(lootbox.get(), amount, "CONSOLE"));
        }
        sendLootboxAllMessage(lootbox.get(), amount, new UUID(0, 0));
        console.sendMessage("Gave lootbox to all players.");
    }

    private void giveLootboxToPlayer(ConsoleCommandSender console, String playerName, String lootboxName, String amountStr) {
        Player target = console.getServer().getPlayerExact(playerName);
        if (target == null) {
            console.sendMessage("Player not found.");
            return;
        }
        Optional<Lootbox> lootbox = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootbox.isEmpty()) {
            console.sendMessage("Lootbox not found.");
            return;
        }
        int amount = parseAmount(console, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootbox.get(), amount);
        target.getInventory().addItem(item);
        target.sendMessage(createLootboxMessage(lootbox.get(), amount, "CONSOLE"));
        console.sendMessage("Gave lootbox to player.");
    }

    private int parseAmount(ConsoleCommandSender console, String amountStr) {
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            console.sendMessage("Invalid amount.");
            return -1;
        }
        if (!Utility.inRange(amount, 1, 64)) {
            console.sendMessage("Amount must be between 1 and 64.");
            return -1;
        }
        return amount;
    }

    private SimpleItemStack createLootboxItem(Lootbox lootbox, int amount) {
        SimpleItemStack item = SimpleItemStack.builder(lootbox.getMaterial());
        item.withAmount(amount);
        item.withDisplayName(lootbox.getDisplayName());
        //TODO: Add lore to item
        return item;
    }

    private Component createLootboxMessage(Lootbox lootbox, int amount, String sender) {
        return MiniMessage.miniMessage().deserialize(
                plugin.messagesConfiguration().get("lootbox.give", plugin.messagesConfiguration().getPrefix(),
                        PlainTextComponentSerializer.plainText().serialize(lootbox.getDisplayName()), amount, sender)
        );
    }

    private void sendLootboxAllMessage(Lootbox lootbox, int amount, UUID sender) {
        SkinRender skinRender = new SkinRender.Builder().fromUniqueId(sender).useHexColors().build();
        Component[] skinRendered = !Objects.equals(sender, new UUID(0, 0)) ? skinRender.render() : null;

        List<String> messages = plugin.messagesConfiguration().getLootboxAllBroadcast();
        for (int i = 0; i < messages.size(); i++) {
            if (skinRendered != null) {
                messages.set(i, messages.get(i).replace("{skin" + i + "}", MiniMessage.miniMessage().serialize(skinRendered[i])));
            } else {
                messages.set(i, messages.get(i).replace("{skin" + i + "}", ""));
            }
            messages.set(i, messages.get(i).replace("{1}", lootbox.getDisplayName().toString()));
            messages.set(i, messages.get(i).replace("{2}", String.valueOf(amount)));
            messages.set(i, messages.get(i).replace("{3}", (sender.equals(new UUID(0, 0)) ? "CONSOLE" : (SimpleUUIDFetcher.fromUniqueId(sender) == null ? "N/A" : Objects.requireNonNull(SimpleUUIDFetcher.fromUniqueId(sender)))))); //TODO: UUIDFetcher
        }
        messages.stream().map(MiniMessage.miniMessage()::deserialize).forEach(Bukkit::broadcast);

        Sound sound = plugin.generalConfiguration().lootboxAllSound().sound();
        float volume = plugin.generalConfiguration().lootboxAllSound().volume();
        float pitch = plugin.generalConfiguration().lootboxAllSound().pitch();
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, volume, pitch));
    }
}