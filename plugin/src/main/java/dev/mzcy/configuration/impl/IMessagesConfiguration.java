package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.MessagesConfiguration;
import dev.mzcy.configuration.Configuration;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class IMessagesConfiguration extends Configuration implements MessagesConfiguration {

    final String prefix;
    final String noPermission;

    final Map<String, String> messages;
    final List<String> lootboxAllBroadcast;
    final List<String> previewHologram;

    public IMessagesConfiguration() {
        this.prefix = "<gray>[<gold>Lootboxes<gray>] <reset>";
        this.noPermission = "<red>You do not have permission to do this.";

        this.messages = new HashMap<>() {{
            put("messages.command.player-not-found", "{0}<red>Player not found.");
            put("messages.command.invalid-amount", "{0}<red>The amount must be between <gold>{1}<red> and <gold>{2}<red>.");
            put("messages.command.not-found", "{0}<red>Subcommand not found.");
            put("messages.command.usage", "{0}<gray>Usage: <gold>{1}");
            put("messages.command.no-permission", "{0}<red>You do not have permission to do this.");
            put("messages.lootbox.give", "{0}<gray>You have received <gold>{1}<gray>x <gold>{2} <gray>from <gold>{3}<gray>.");
            put("messages.lootbox.give-sender", "{0}<gray>You have given <gold>{1}<gray>x <gold>{2} <gray>to <gold>{3}<gray>.");
            put("messages.lootbox.open", "{0}<gray>You have opened <gold>{1}<gray>x <gold>{2}<gray>.");
            put("messages.lootbox.exists", "{0}<red>A lootbox with the name <gold>{1}<red> already exists.");
            put("messages.lootbox.not-found", "{0}<red>A lootbox with the name <gold>{1}<red> couldn't be found.");
            put("messages.lootbox.create", "{0}<gray>You have created a lootbox with the name <gold>{1}<gray>.");
            put("messages.lootbox.delete", "{0}<gray>You have deleted the lootbox with the name <gold>{1}<gray>.");


        }};
        this.lootboxAllBroadcast = List.of(
                "{skin0}<dark_gray><st>                              <reset>",
                "{skin1}<rainbow><b>LOOTBOX ALL!</b></rainbow> <gray>from {3}",
                "{skin2}",
                "{skin3}<dark_gray>• <gray>Lootbox: <gold>{1}",
                "{skin4}<dark_gray>• <gray>Amount: <gold>{2}x",
                "{skin5}",
                "{skin6}<gray>Say your thanks in chat!",
                "{skin7}<dark_gray><st>                              <reset>"
        );
        this.previewHologram = List.of(
                "{displayName}",
                "§r",
                "§7§oPreview §8• §7Rightclick to open"
        );
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getNoPermission() {
        return noPermission;
    }

    @Override
    public String get(String key, Object... args) {
        String message = get(key);
        if (message == null) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", args[i].toString());
        }
        return message;
    }

    @Override
    public String get(String key) {
        return messages.getOrDefault((key.startsWith("messages.") ? key : "messages." + key), null);
    }
}
