package pl.nadwey.nadarenas.bukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.bukkit.command.CommandHandler;
import pl.nadwey.nadarenas.bukkit.restorer.ArenaRestorer;
import pl.nadwey.nadarenas.common.INadArenasPlugin;
import pl.nadwey.nadarenas.common.config.ConfigManager;
import pl.nadwey.nadarenas.common.storage.Storage;
import pl.nadwey.nadarenas.common.storage.StorageFactory;

import java.nio.file.Path;

public final class BukkitNadArenasPlugin extends JavaPlugin implements INadArenasPlugin {
    @Getter
    private ConfigManager configManager;
    @Getter
    private Storage storage;
    @Getter
    private ArenaRestorer arenaRestorer;
    private CommandHandler commandHandler;

    @Override
    public void onLoad() {
        getDataFolder().mkdir();
        getDataFolder().toPath().resolve("arenas").toFile().mkdir();
    }

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        storage = new StorageFactory(this).getInstance();
        arenaRestorer = new ArenaRestorer(this);
        commandHandler = new CommandHandler(this);
    }

    @Override
    public void onDisable() {
        commandHandler.onDisable();

        arenaRestorer.onDisable();

        storage.shutdown();

        configManager.onDisable();
    }

    public void reload() {
        onDisable();
        onEnable();
    }

    @Override
    public Path getDataDir() {
        return getDataFolder().toPath();
    }
}
