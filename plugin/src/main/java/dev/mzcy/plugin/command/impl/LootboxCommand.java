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
import dev.mzcy.plugin.lootbox.edit.gui.LootboxEditMainGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.joml.Vector3f;

import java.util.ArrayList;
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
            sendUsageMessages(player);
            return;
        }
        switch (arguments[0]) {
            case "give":
                handleGiveCommand(player, arguments);
                break;
            case "preview":
                handlePreviewCommand(player, arguments);
                break;
            case "create":
                handleCreateCommand(player, arguments);
                break;
            case "delete":
                handleDeleteCommand(player, arguments);
                break;
            case "edit":
                handleEditCommand(player, arguments);
                break;
            case "list":
                //TODO: Implement list command
                break;
            default:
                sendUsageMessages(player);
                break;
        }
    }

    private void handleConsoleCommand(ConsoleCommandSender sender, String[] arguments) {
        if (arguments.length == 0 || !"give".equals(arguments[0])) {
            sender.sendMessage("Usage: /lootbox <give> <player/all> <lootbox> <amount>");
            return;
        }
        handleGiveCommand(sender, arguments);
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
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox preview <lootbox>")));
            return;
        }
        Optional<Lootbox> lootboxOpt = plugin.databaseManager().lootboxManager().get(arguments[1]);
        if (lootboxOpt.isEmpty()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.not-found", plugin.messagesConfiguration().getPrefix())));
            return;
        }
        Lootbox lootbox = lootboxOpt.get();
        if (!plugin.fancyHologramsEnabled()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().getPrefix() + "FancyHolograms is not enabled. Please enable it in the configuration."));
            return;
        }
        String hologramId = "lb-prev-" + lootbox.getName();
        if (FancyHologramsPlugin.get().getHologramManager().getHologram(hologramId).isEmpty()) {
            createHologram(player, lootbox, hologramId);
            player.sendMessage(plugin.messagesConfiguration().get("messages.lootbox.preview.create", plugin.messagesConfiguration().getPrefix(), lootbox.getName()));
        } else {
            removeHologram(hologramId);
            player.sendMessage(plugin.messagesConfiguration().get("messages.lootbox.preview.remove", plugin.messagesConfiguration().getPrefix(), lootbox.getName()));
        }
    }

    private void handleCreateCommand(Player player, String[] arguments) {
        if (arguments.length < 3) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox create <lootbox> <material>")));
            return;
        }
        String lootboxName = arguments[1];
        String materialName = arguments[2];
        if (plugin.databaseManager().lootboxManager().exists(lootboxName)) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.exists", plugin.messagesConfiguration().getPrefix(), lootboxName)));
            return;
        }
        Lootbox lootbox = new Lootbox();
        lootbox.setName(lootboxName);
        lootbox.setMaterial(Material.valueOf(materialName));
        lootbox.setItems(new ArrayList<>());
        lootbox.setDisplayName(Component.text(lootboxName));
        plugin.databaseManager().lootboxManager().save(lootbox);
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.create", plugin.messagesConfiguration().getPrefix(), lootboxName)));
    }

    private void handleDeleteCommand(Player player, String[] arguments) {
        if (arguments.length < 2) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox delete <lootbox>")));
            return;
        }
        String lootboxName = arguments[1];
        if (!plugin.databaseManager().lootboxManager().exists(lootboxName)) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.not-found", plugin.messagesConfiguration().getPrefix())));
            return;
        }
        plugin.databaseManager().lootboxManager().delete(lootboxName);
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.delete", plugin.messagesConfiguration().getPrefix())));
    }

    private void handleEditCommand(Player player, String[] arguments) {
        if (arguments.length < 2) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox edit <lootbox>")));
            return;
        }
        String lootboxName = arguments[1];
        if (!plugin.databaseManager().lootboxManager().exists(lootboxName)) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.not-found", plugin.messagesConfiguration().getPrefix())));
            return;
        }
        new LootboxEditMainGui(plugin, plugin.databaseManager().lootboxManager().get(lootboxName).get()).open(player);
    }

    private void createHologram(Player player, Lootbox lootbox, String hologramId) {
        BlockHologramData blockData = new BlockHologramData(hologramId, player.getLocation());
        blockData.setBlock(lootbox.getMaterial());
        blockData.setScale(new Vector3f(2, 2, 2));
        Hologram blockHologram = FancyHologramsPlugin.get().getHologramManager().create(blockData);
        FancyHologramsPlugin.get().getHologramManager().addHologram(blockHologram);

        TextHologramData textData = new TextHologramData(hologramId + "-text", player.getLocation().add(0, 2.3, 0));
        List<String> lines = plugin.messagesConfiguration().getPreviewHologram();
        lines.replaceAll(s -> s.replace("{displayName}", MiniMessage.miniMessage().serialize(lootbox.getDisplayName())));
        textData.setText(lines);
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
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.not-found", plugin.messagesConfiguration().getPrefix(), lootboxName)));
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
        sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.give-sender", plugin.messagesConfiguration().getPrefix(), lootboxOpt.get().getDisplayName(), amount, "all online players")));
    }

    private void giveLootboxToPlayer(CommandSender sender, String playerName, String lootboxName, String amountStr) {
        Player target = sender.getServer().getPlayerExact(playerName);
        if (target == null) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.player-not-found", plugin.messagesConfiguration().getPrefix())));
            return;
        }
        Optional<Lootbox> lootboxOpt = plugin.databaseManager().lootboxManager().get(lootboxName);
        if (lootboxOpt.isEmpty()) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.not-found", plugin.messagesConfiguration().getPrefix(), lootboxName)));
            return;
        }
        int amount = parseAmount(sender, amountStr);
        if (amount == -1) return;

        SimpleItemStack item = createLootboxItem(lootboxOpt.get(), amount);
        target.getInventory().addItem(item);
        target.sendMessage(createLootboxMessage(lootboxOpt.get(), amount, (sender instanceof Player) ? sender.getName() : "CONSOLE"));
        sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.lootbox.give-sender", plugin.messagesConfiguration().getPrefix(), lootboxOpt.get().getDisplayName(), amount, target.getName())));
    }

    private int parseAmount(CommandSender sender, String amountStr) {
        try {
            int amount = Integer.parseInt(amountStr);
            if (Utility.inRange(amount, 1, 64)) {
                return amount;
            }
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.invalid-amount", plugin.messagesConfiguration().getPrefix(), 1, 64)));
        } catch (NumberFormatException e) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.invalid-amount", plugin.messagesConfiguration().getPrefix(), 1, 64)));
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

    private void sendUsageMessages(Player player) {
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.not-found", plugin.messagesConfiguration().getPrefix())));
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox <create> <lootbox> <material>")));
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox <delete> <lootbox>")));
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox <list>")));
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox <give> <player/all> <lootbox> <amount>")));
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox <edit> <lootbox>")));
        player.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messagesConfiguration().get("messages.command.usage", plugin.messagesConfiguration().getPrefix(), "/lootbox <preview> <lootbox>")));
    }
}