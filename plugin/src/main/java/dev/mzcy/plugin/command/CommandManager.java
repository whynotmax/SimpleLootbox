package dev.mzcy.plugin.command;

import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.command.model.SimpleCommand;
import org.bukkit.command.CommandMap;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    LootboxesPlugin plugin;
    Map<String, SimpleCommand> commands;
    CommandMap commandMap;

    public CommandManager(LootboxesPlugin plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<>();
        this.commandMap = plugin.getServer().getCommandMap();

        Reflections reflections = new Reflections("dev.mzcy.plugin.command.impl");
        reflections.getSubTypesOf(SimpleCommand.class).forEach(command -> {
            try {
                SimpleCommand instance = command.getDeclaredConstructor(LootboxesPlugin.class).newInstance(this.plugin);
                commands.put(instance.getName(), instance);
                commandMap.register("simplelb", instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        plugin.getLogger().info("Registered " + commands.size() + " commands.");
    }

}
