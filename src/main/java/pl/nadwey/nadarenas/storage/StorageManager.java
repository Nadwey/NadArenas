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
        return this.storage;
    }

    public void onEnable() {
        this.storage = new StorageFactory(this.plugin).getInstance();

        this.arenaStorageManager = new ArenaStorageManager(this);
    }

    public void onDisable() {
        this.storage.shutdown();
    }

    public NadArenas getPlugin() {
        return this.plugin;
    }

    public ArenaStorageManager arena() {
        return this.arenaStorageManager;
    }
}
