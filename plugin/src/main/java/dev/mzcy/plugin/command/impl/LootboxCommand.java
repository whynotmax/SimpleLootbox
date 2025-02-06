package dev.mzcy.plugin.command.impl;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.api.utility.Utility;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.command.model.SimpleCommand;
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
import java.util.Optional;
import java.util.UUID;

public class LootboxCommand extends SimpleCommand {

    private static final UUID CONSOLE_SENDER_UNIQUE_ID = new UUID(0, 0);

    public LootboxCommand(LootboxesPlugin plugin) {
        super("lootbox", List.of("lb", "slb", "simplelb", "simplelootbox", "simplelootboxes", "lootboxes"), plugin);
    }

    @Override
    public void run(CommandSender sender, String[] arguments) {
        if (sender instanceof ConsoleCommandSender) {
            handleConsoleCommand((ConsoleCommandSender) sender, arguments);
        } else {
            handlePlayerCommand((Player) sender, arguments);
        }
    }

    private void handlePlayerCommand(Player player, String[] arguments) {
        if (arguments.length == 0) {
            player.sendMessage("Usage: /lootbox <give> <lootbox> <amount>");
            return;
        }
        switch (arguments[0]) {
            case "give":
                handleGiveCommand(player, arguments);
                break;
            case "preview":
                handlePreviewCommand(player, arguments);
                break;
            default:
                player.sendMessage("Usage: /lootbox <give> <player/all> <lootbox> <amount>");
        }
    }

    private void handleConsoleCommand(ConsoleCommandSender sender, String[] arguments) {
        if (arguments.length == 0) {
            sender.sendMessage("Usage: /lootbox <give> <player/all> <lootbox> <amount>");
            return;
        }
        if ("give".equals(arguments[0])) {
            handleGiveCommand(sender, arguments);
        } else {
            sender.sendMessage("Usage: /lootbox <give> <player> <lootbox> <amount>");
        }
    }

    private void handleGiveCommand(CommandSender sender, String[] arguments) {
        if (arguments.length < 4) {
            sender.sendMessage("Usage: /lootbox give <player/all> <lootbox> <amount>");
            return;
        }
        if ("all".equalsIgnoreCase(arguments[1])) {
            giveLootboxToAll(sender, arguments[2], arguments[3]);
        } else {
            giveLootboxToPlayer(sender, arguments[1], arguments[2], arguments[3]);
        }
    }

    private void handlePreviewCommand(Player player, String[] arguments) {
        if (arguments.length < 2) {
            player.sendMessage("Usage: /lootbox preview <lootbox>");
            return;
        }
        Optional<Lootbox> lootboxOpt = plugin.databaseManager().lootboxManager().get(arguments[1]);
        if (lootboxOpt.isEmpty()) {
            player.sendMessage("Lootbox not found.");
            return;
        }
        Lootbox lootbox = lootboxOpt.get();
        if (!plugin.fancyHologramsEnabled()) {
            player.sendMessage("FancyHolograms is not enabled!");
            return;
        }
        String hologramId = "lb-prev-" + lootbox.getName();
        if (FancyHologramsPlugin.get().getHologramManager().getHologram(hologramId).isEmpty()) {
            createHologram(player, lootbox, hologramId);
            player.sendMessage("Lootbox preview created. To remove it, use this command again.");
        } else {
            removeHologram(hologramId);
            player.sendMessage("Lootbox preview removed. To create it again, use this command.");
        }
    }

    private void createHologram(Player player, Lootbox lootbox, String hologramId) {
        BlockHologramData blockData = new BlockHologramData(hologramId, player.getLocation());
        blockData.setBlock(lootbox.getMaterial());
        blockData.setScale(new Vector3f(2, 2, 2));
        Hologram blockHologram = FancyHologramsPlugin.get().getHologramManager().create(blockData);
        FancyHologramsPlugin.get().getHologramManager().addHologram(blockHologram);

        TextHologramData textData = new TextHologramData(hologramId + "-text", player.getLocation().add(0, 2.3, 0));
        textData.setText(List.of(
                MiniMessage.miniMessage().serialize(lootbox.getDisplayName()),
                "§8§m                  §r",
                "§7§oPreview §8• §7§oRightclick to open"
        ));
        textData.setTextAlignment(TextDisplay.TextAlignment.CENTER);
        textData.setTextUpdateInterval(20);
        textData.setScale(new Vector3f(1.5f, 1.5f, 1.5f));
        textData.setBackground(Color.fromARGB(0));
        Hologram textHologram = FancyHologramsPlugin.get().getHologramManager().create(textData);
        FancyHologramsPlugin.get().getHologramManager().addHologram(textHologram);
    }

    private void removeHologram(String hologramId) {
        FancyHologramsPlugin.get().getHologramManager().getHologram(hologramId).ifPresent(Hologram::deleteHologram);
        FancyHologramsPlugin.get().getHologramManager().getHologram(hologramId + "-text").ifPresent(Hologram::deleteHologram);
    }

    private void giveLootboxToAll(CommandSender sender, String lootboxName, String amountStr) {
        Optional<Lootbox> lootboxOpt = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootboxOpt.isEmpty()) {
            sender.sendMessage("Lootbox not found.");
            return;
        }
        int amount = parseAmount(sender, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootboxOpt.get(), amount);
        for (Player target : sender.getServer().getOnlinePlayers()) {
            target.getInventory().addItem(item);
            target.sendMessage(createLootboxMessage(lootboxOpt.get(), amount, (sender instanceof Player) ? sender.getName() : "CONSOLE"));
        }
        sendLootboxAllMessage(lootboxOpt.get(), amount, (sender instanceof Player) ? ((Player) sender).getUniqueId() : CONSOLE_SENDER_UNIQUE_ID);
        sender.sendMessage("Gave lootbox to all players.");
    }

    private void giveLootboxToPlayer(CommandSender sender, String playerName, String lootboxName, String amountStr) {
        Player target = sender.getServer().getPlayerExact(playerName);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return;
        }
        Optional<Lootbox> lootboxOpt = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootboxOpt.isEmpty()) {
            sender.sendMessage("Lootbox not found.");
            return;
        }
        int amount = parseAmount(sender, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootboxOpt.get(), amount);
        target.getInventory().addItem(item);
        target.sendMessage(createLootboxMessage(lootboxOpt.get(), amount, (sender instanceof Player) ? sender.getName() : "CONSOLE"));
        sender.sendMessage("Gave lootbox to player.");
    }

    private int parseAmount(CommandSender sender, String amountStr) {
        try {
            int amount = Integer.parseInt(amountStr);
            if (Utility.inRange(amount, 1, 64)) {
                return amount;
            }
            sender.sendMessage("Amount must be between 1 and 64.");
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount.");
        }
        return -1;
    }

    private SimpleItemStack createLootboxItem(Lootbox lootbox, int amount) {
        return SimpleItemStack.builder(lootbox.getMaterial())
                .withAmount(amount)
                .withDisplayName(lootbox.getDisplayName());
    }

    private Component createLootboxMessage(Lootbox lootbox, int amount, String sender) {
        return MiniMessage.miniMessage().deserialize(
                plugin.messagesConfiguration().get("lootbox.give", plugin.messagesConfiguration().getPrefix(),
                        PlainTextComponentSerializer.plainText().serialize(lootbox.getDisplayName()), amount, sender)
        );
    }

    private void sendLootboxAllMessage(Lootbox lootbox, int amount, UUID sender) {
        List<String> messages = plugin.messagesConfiguration().getLootboxAllBroadcast();
        messages.replaceAll(s -> s
                .replace("{1}", lootbox.getDisplayName().toString())
                .replace("{2}", String.valueOf(amount))
                .replace("{3}", sender.equals(CONSOLE_SENDER_UNIQUE_ID) ? "CONSOLE" : "N/A"));
        messages.stream().map(MiniMessage.miniMessage()::deserialize).forEach(Bukkit::broadcast);

        Sound sound = plugin.generalConfiguration().lootboxAllSound().sound();
        float volume = ((Number) plugin.generalConfiguration().lootboxAllSound().volume()).floatValue();
        float pitch = ((Number) plugin.generalConfiguration().lootboxAllSound().pitch()).floatValue();
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, volume, pitch));
    }
}