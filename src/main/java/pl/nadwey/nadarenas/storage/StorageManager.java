package pl.nadwey.nadarenas.storage;

import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.ArenaManager;

public class StorageManager {
    private final NadArenas plugin;
    private Storage storage;

    private ArenaManager arenaManager;

    public StorageManager(NadArenas plugin) {
        this.plugin = plugin;
    }

    public Storage getStorage() {
        return storage;
    }

    public void onEnable() {
        plugin.getLogger().info("Enabling storage and performing migrations...");
        storage = new StorageFactory(plugin).getInstance();

        arenaManager = new ArenaManager(this);
    }

    public void onDisable() {
        plugin.getLogger().info("Shutting down storage...");
        storage.shutdown();
    }

    public NadArenas getPlugin() {
        return plugin;
    }

    public ArenaManager arena() {
        return arenaManager;
    }
}
