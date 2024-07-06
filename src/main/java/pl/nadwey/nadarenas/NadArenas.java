package pl.nadwey.nadarenas;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.configuration.MainConfiguration;
import pl.nadwey.nadarenas.lang.LangManager;
import pl.nadwey.nadarenas.storage.StorageManager;
import pl.nadwey.nadarenas.restorer.ArenaRestorer;

public final class NadArenas extends JavaPlugin {
    private CommandHandler commandHandler;

    @Getter
    private StorageManager storageManager;
    @Getter
    private ArenaRestorer arenaRestorer;
    @Getter
    private LangManager langManager;
    @Getter
    private MainConfiguration mainConfiguration;

    @Override
    public void onLoad() {
        getDataFolder().mkdir();
        getDataFolder().toPath().resolve("arenas").toFile().mkdir();

        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("org.jooq.no-tips", "true");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        mainConfiguration = new MainConfiguration(this);

        storageManager = new StorageManager(this);
        storageManager.onEnable();

        arenaRestorer = new ArenaRestorer(this);
        arenaRestorer.onEnable();

        commandHandler = new CommandHandler(this);
        commandHandler.onEnable();

        langManager = new LangManager(this);
    }

    @Override
    public void onDisable() {
        commandHandler.onDisable();

        arenaRestorer.onDisable();

        storageManager.onDisable();
    }

    public void reload() {
        // disable
        arenaRestorer.onDisable();
        storageManager.onDisable();

        // enable and reload reloadable stuff
        mainConfiguration.reload();

        storageManager = new StorageManager(this);
        storageManager.onEnable();

        arenaRestorer = new ArenaRestorer(this);
        arenaRestorer.onEnable();

        langManager.reload();
        commandHandler.reload();
    }
}
