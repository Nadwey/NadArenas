package pl.nadwey.nadarenas;

import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.model.arena.ArenaManager;
import pl.nadwey.nadarenas.storage.Storage;
import pl.nadwey.nadarenas.storage.StorageFactory;

public final class NadArenas extends JavaPlugin {
    private static NadArenas instance;
    private CommandHandler commandHandler;
    private Storage storage;

    private ArenaManager arenaManager;

    public static NadArenas getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        getDataFolder().mkdir();

        commandHandler = new CommandHandler(this);

        commandHandler.onLoad();
    }

    @Override
    public void onEnable() {
        StorageFactory storageFactory = new StorageFactory(this);
        this.storage = storageFactory.getInstance();

        arenaManager = new ArenaManager(this);

        commandHandler.onEnable();
    }

    @Override
    public void onDisable() {
        commandHandler.onDisable();

        getLogger().info("Closing storage...");
        this.storage.shutdown();
    }

    public Storage getStorage() {
        return this.storage;
    }

    public ArenaManager getArenaManager() {
        return this.arenaManager;
    }
}
