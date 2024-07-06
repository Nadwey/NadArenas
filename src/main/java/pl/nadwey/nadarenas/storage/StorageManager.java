package pl.nadwey.nadarenas.storage;

import lombok.Getter;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.ArenaManager;

public class StorageManager {
    @Getter
    private final NadArenas plugin;
    @Getter
    private Storage storage;

    private ArenaManager arenaManager;

    public StorageManager(NadArenas plugin) {
        this.plugin = plugin;
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

    public ArenaManager arena() {
        return arenaManager;
    }
}
