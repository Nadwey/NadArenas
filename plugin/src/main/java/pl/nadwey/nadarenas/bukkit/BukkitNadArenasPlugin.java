package pl.nadwey.nadarenas.bukkit;

import lombok.Getter;
import pl.nadwey.nadarenas.bukkit.command.CommandHandler;
import pl.nadwey.nadarenas.bukkit.restorer.ArenaRestorer;
import pl.nadwey.nadarenas.common.NadArenasPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

public final class BukkitNadArenasPlugin extends NadArenasPlugin {
    @Getter
    private final BukkitNadArenasPluginLoader loader;
    @Getter
    private ArenaRestorer arenaRestorer;
    private CommandHandler commandHandler;

    public BukkitNadArenasPlugin(BukkitNadArenasPluginLoader loader) {
        this.loader = loader;
    }

    public void onLoad() {

    }

    public void onEnable() {
        enable();

        arenaRestorer = new ArenaRestorer(this);
        commandHandler = new CommandHandler(this);
    }

    public void onDisable() {
        commandHandler.onDisable();

        arenaRestorer.onDisable();

        disable();
    }

    public void reload() {
        onDisable();
        onEnable();
    }

    @Override
    public Logger getLogger() {
        return loader.getLogger();
    }

    @Override
    public Path getDataDir() {
        return loader.getDataFolder().toPath();
    }
}
