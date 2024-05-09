package pl.nadwey.nadarenas;

import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.lang.LangManager;
import pl.nadwey.nadarenas.model.arena.ArenaManager;
import pl.nadwey.nadarenas.storage.Storage;
import pl.nadwey.nadarenas.storage.StorageFactory;
import pl.nadwey.nadarenas.utility.ArenaLoader;

public final class NadArenas extends JavaPlugin {
    private static NadArenas instance;
    private CommandHandler commandHandler;
    private Storage storage;

    private ArenaManager arenaManager;
    private ArenaLoader arenaLoader;
    private LangManager langManager;

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

        this.storage = new StorageFactory(this).getInstance();

        this.arenaManager = new ArenaManager(this);

        this.arenaLoader = new ArenaLoader();
        this.arenaLoader.onEnable();

        this.commandHandler = new CommandHandler(this);
        this.commandHandler.onEnable();

        this.langManager = new LangManager(this);
    }

    @Override
    public void onDisable() {
        this.commandHandler.onDisable();
        this.arenaLoader.onDisable();

        getLogger().info("Closing storage...");
        this.storage.shutdown();
    }

    public void reload() {
        this.arenaLoader.onDisable();
        this.storage.shutdown();


        this.storage = new StorageFactory(this).getInstance();

        this.arenaLoader = new ArenaLoader();
        this.arenaLoader.onEnable();

        this.langManager.reload();
        this.commandHandler.reload();
    }

    public Storage getStorage() {
        return this.storage;
    }

    public ArenaManager getArenaManager() {
        return this.arenaManager;
    }

    public ArenaLoader getArenaLoader() {
        return this.arenaLoader;
    }

    public LangManager getLangManager() {
        return this.langManager;
    }
}
