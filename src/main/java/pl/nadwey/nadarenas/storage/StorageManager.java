package pl.nadwey.nadarenas.storage;

import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.ArenaStorageManager;

public class StorageManager {
    private final NadArenas plugin;
    private Storage storage;

    private ArenaStorageManager arenaStorageManager;

    public StorageManager(NadArenas plugin) {
        this.plugin = plugin;
    }

    public Storage getStorage() {
        return storage;
    }

    public void onEnable() {
        plugin.getLogger().info("Enabling storage and performing migrations...");
        storage = new StorageFactory(plugin).getInstance();

        arenaStorageManager = new ArenaStorageManager(this);
    }

    public void onDisable() {
        plugin.getLogger().info("Shutting down storage...");
        storage.shutdown();
    }

    public NadArenas getPlugin() {
        return plugin;
    }

    public ArenaStorageManager arena() {
        return arenaStorageManager;
    }
}
