package pl.nadwey.nadarenas;

import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.command.CommandHandler;
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

    public static NadArenas getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        NadArenas.instance = this;

        getDataFolder().mkdir();
        getDataFolder().toPath().resolve("arenas").toFile().mkdir();

        this.commandHandler = new CommandHandler(this);

        this.commandHandler.onLoad();
    }

    @Override
    public void onEnable() {
        getLogger().info("Enabling storage and performing migrations...");
        StorageFactory storageFactory = new StorageFactory(this);
        this.storage = storageFactory.getInstance();

        this.arenaManager = new ArenaManager(this);
        this.arenaLoader = new ArenaLoader();

        this.commandHandler.onEnable();
        this.arenaLoader.onEnable();
    }

    @Override
    public void onDisable() {
        this.commandHandler.onDisable();
        this.arenaLoader.onDisable();

        getLogger().info("Closing storage...");
        this.storage.shutdown();
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
}
