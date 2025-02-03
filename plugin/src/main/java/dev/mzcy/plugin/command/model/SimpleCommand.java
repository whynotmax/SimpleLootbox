package dev.mzcy.plugin.command.model;

import dev.mzcy.plugin.LootboxesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SimpleCommand extends Command {

    protected LootboxesPlugin plugin;

    public SimpleCommand(@NotNull String name, @NotNull List<String> aliases, LootboxesPlugin plugin) {
        super(name, "", "", aliases);
        this.plugin = plugin;
    }

    public abstract void run(CommandSender sender, String[] arguments);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        run(sender, args);
        return false;
    }
}
