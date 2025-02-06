package dev.mzcy.plugin.command.impl;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.api.utility.SimpleUUIDFetcher;
import dev.mzcy.api.utility.Utility;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.command.model.SimpleCommand;
import dev.mzcy.plugin.utility.SkinRender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.joml.Vector3f;

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
        if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            handleConsoleCommand(consoleCommandSender, arguments);
        } else {
            Player player = (Player) sender;
            handlePlayerCommand(player, arguments);
        }
    }

    private void handlePlayerCommand(Player player, String[] arguments) {
        if (arguments.length == 0) {
            player.sendMessage("Usage: /lootbox <give> <lootbox> <amount>");
            return;
        }
        if (arguments[0].equals("give")) {
            if (arguments.length < 3) {
                player.sendMessage("Usage: /lootbox give <player/all> <lootbox> <amount>");
                return;
            }
            if (arguments[1].equalsIgnoreCase("all")) {
                giveLootboxToAll(player, arguments[2], arguments[3]);
            } else {
                giveLootboxToPlayer(player, arguments[1], arguments[2], arguments[3]);
            }
        } else if (arguments[0].equals("preview")) {
            if (arguments.length < 2) {
                player.sendMessage("Usage: /lootbox preview <lootbox>");
            }
            Lootbox lootbox = plugin.databaseManager().lootboxManager().get(arguments[1]).orElse(null);
            if (lootbox == null) {
                player.sendMessage("Lootbox not found.");
                return;
            }
            if (!plugin.fancyHologramsEnabled()) {
                player.sendMessage("FancyHolograms is not enabled! This means that we can't spawn any holograms at this time (maybe in the next update) as I don't want to code my own hologram implementation at this moment. Sorry!");
                return;
            }
            if (FancyHologramsPlugin.get().getHologramManager().getHologram("lb-prev-" + lootbox.getName()).isEmpty()) {
                BlockHologramData hologramData = new BlockHologramData("lb-prev-" + lootbox.getName(), player.getLocation());
                hologramData.setBlock(lootbox.getMaterial());
                hologramData.setScale(new Vector3f(2, 2, 2));
                Hologram blockHologram = FancyHologramsPlugin.get().getHologramManager().create(hologramData);
                FancyHologramsPlugin.get().getHologramManager().addHologram(blockHologram);
                TextHologramData textHologramData = new TextHologramData("lb-prev-text-" + lootbox.getName(), player.getLocation().add(0, 2.3, 0));
                textHologramData.setText(List.of(
                        MiniMessage.miniMessage().serialize(lootbox.getDisplayName()),
                        "§8§m                  §r",
                        "§7§oPreview §8• §7§oRightclick to open"
                ));
                textHologramData.setTextAlignment(TextDisplay.TextAlignment.CENTER);
                textHologramData.setTextUpdateInterval(20);
                textHologramData.setScale(new Vector3f(1.5f, 1.5f, 1.5f));
                textHologramData.setBackground(Color.fromARGB(0));
                Hologram textHologram = FancyHologramsPlugin.get().getHologramManager().create(textHologramData);
                FancyHologramsPlugin.get().getHologramManager().addHologram(textHologram);
                player.sendMessage("Lootbox preview created. To remove it, use this command again.");
                return;
            }
            FancyHologramsPlugin.get().getHologramManager().getHologram("lb-prev-" + lootbox.getName()).ifPresent(Hologram::deleteHologram);
            FancyHologramsPlugin.get().getHologramManager().getHologram("lb-prev-text-" + lootbox.getName()).ifPresent(Hologram::deleteHologram);
            player.sendMessage("Lootbox preview removed. To create it again, use this command.");
        } else {
            player.sendMessage("Usage: /lootbox <give> <player/all> <lootbox> <amount>");
        }
    }

    private void handleConsoleCommand(ConsoleCommandSender sender, String[] arguments) {
        if (arguments.length == 0) {
            sender.sendMessage("Usage: /lootbox <give> <player/all> <lootbox> <amount>");
            sender.sendMessage("Usage: /lootbox <preview> <lootbox>");
            return;
        }
        if (arguments[0].equals("give")) {
            if (arguments.length < 4) {
                sender.sendMessage("Usage: /lootbox give <player> <lootbox> <amount>");
                return;
            }
            if (arguments[1].equalsIgnoreCase("all")) {
                giveLootboxToAll(sender, arguments[2], arguments[3]);
            } else {
                giveLootboxToPlayer(sender, arguments[1], arguments[2], arguments[3]);
            }
        } else {
            sender.sendMessage("Usage: /lootbox <give> <player> <lootbox> <amount>");
        }
    }

    private void giveLootboxToAll(CommandSender sender, String lootboxName, String amountStr) {
        Optional<Lootbox> lootbox = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootbox.isEmpty()) {
            sender.sendMessage("Lootbox not found.");
            return;
        }
        int amount = parseAmount(sender, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootbox.get(), amount);
        for (Player target : sender.getServer().getOnlinePlayers()) {
            target.getInventory().addItem(item);
            target.sendMessage(createLootboxMessage(lootbox.get(), amount, "CONSOLE"));
        }
        sendLootboxAllMessage(lootbox.get(), amount, new UUID(0, 0));
        sender.sendMessage("Gave lootbox to all players.");
    }

    private void giveLootboxToPlayer(CommandSender sender, String playerName, String lootboxName, String amountStr) {
        Player target = sender.getServer().getPlayerExact(playerName);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return;
        }
        Optional<Lootbox> lootbox = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootbox.isEmpty()) {
            sender.sendMessage("Lootbox not found.");
            return;
        }
        int amount = parseAmount(sender, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootbox.get(), amount);
        target.getInventory().addItem(item);
        target.sendMessage(createLootboxMessage(lootbox.get(), amount, "CONSOLE"));
        sender.sendMessage("Gave lootbox to player.");
    }

    private int parseAmount(CommandSender sender, String amountStr) {
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount.");
            return -1;
        }
        if (!Utility.inRange(amount, 1, 64)) {
            sender.sendMessage("Amount must be between 1 and 64.");
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
        float volume = ((Number) plugin.generalConfiguration().lootboxAllSound().volume()).floatValue();
        float pitch = ((Number) plugin.generalConfiguration().lootboxAllSound().pitch()).floatValue();
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, volume, pitch));
    }
}