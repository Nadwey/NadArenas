package pl.nadwey.nadarenas.bukkit.command.command;

import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;

public abstract class CommandBase {
    private final BukkitNadArenasPlugin plugin;

    public CommandBase(BukkitNadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    protected BukkitNadArenasPlugin getPlugin() {
        return plugin;
    }
}
