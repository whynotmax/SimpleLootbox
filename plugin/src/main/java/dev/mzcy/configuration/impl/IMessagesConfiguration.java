package dev.mzcy.configuration.impl;

import dev.mzcy.api.configuration.MessagesConfiguration;
import dev.mzcy.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class IMessagesConfiguration extends Configuration implements MessagesConfiguration {

    String prefix = "<gray>[<gold>Lootboxes<gray>] <reset>";
    String noPermission = "<red>You do not have permission to do this.";

    Map<String, String> messages = new HashMap<>() {{
        put("messages.command.not-found", "{0}<red>Subcommand not found.");
        put("messages.command.usage", "{0}<gray>Usage: <gold>{1}");
        put("messages.command.no-permission", "{0}<red>You do not have permission to do this.");
        put("messages.lootbox.give", "{0}<gray>You have received <gold>{1}<gray>x <gold>{2} <gray>from <gold>{3}<gray>.");
        put("messages.lootbox.open", "{0}<gray>You have opened <gold>{1}<gray>x <gold>{2}<gray>.");
    }};

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
        return messages.getOrDefault("messages." + key, null);
    }
}
