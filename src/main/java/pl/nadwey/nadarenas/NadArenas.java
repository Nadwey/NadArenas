package pl.nadwey.nadarenas;

import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.configuration.MainConfiguration;
import pl.nadwey.nadarenas.lang.LangManager;
import pl.nadwey.nadarenas.storage.StorageManager;
import pl.nadwey.nadarenas.restorer.ArenaManager;

public final class NadArenas extends JavaPlugin {
    private static NadArenas instance;
    private CommandHandler commandHandler;

    private StorageManager storageManager;
    private ArenaManager arenaManager;
    private LangManager langManager;
    private MainConfiguration mainConfiguration;

    public static NadArenas getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        NadArenas.instance = this;

        getDataFolder().mkdir();
        getDataFolder().toPath().resolve("arenas").toFile().mkdir();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.mainConfiguration = new MainConfiguration(this);

        this.storageManager = new StorageManager(this);
        this.storageManager.onEnable();

        this.arenaManager = new ArenaManager(this);
        this.arenaManager.onEnable();

        this.commandHandler = new CommandHandler(this);
        this.commandHandler.onEnable();

        this.langManager = new LangManager(this);
    }

    @Override
    public void onDisable() {
        this.commandHandler.onDisable();

        this.arenaManager.onDisable();

        this.storageManager.onDisable();
    }

    public void reload() {
        // disable
        this.arenaManager.onDisable();
        this.storageManager.onDisable();

        // enable and reload reloadable stuff
        this.mainConfiguration.reload();

        this.storageManager = new StorageManager(this);
        this.storageManager.onEnable();

        this.arenaManager = new ArenaManager(this);
        this.arenaManager.onEnable();

        this.langManager.reload();
        this.commandHandler.reload();
    }

    public StorageManager getStorageManager() {
        return this.storageManager;
    }

    public ArenaManager getArenaManager() {
        return this.arenaManager;
    }

    public LangManager getLangManager() {
        return this.langManager;
    }

    public MainConfiguration getMainConfiguration() {
        return this.mainConfiguration;
    }
}
