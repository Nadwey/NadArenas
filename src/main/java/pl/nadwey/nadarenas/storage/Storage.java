package pl.nadwey.nadarenas.storage;

import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;

public class Storage {
    private final NadArenas plugin;
    private final StorageImplementation implementation;

    public Storage(NadArenas plugin, StorageImplementation implementation) {
        this.plugin = plugin;
        this.implementation = implementation;
    }

    public StorageImplementation getImplementation() {
        return implementation;
    }

    public void init() {
        this.plugin.getLogger().info("Enabling storage and performing migrations...");
        this.implementation.init();
    }

    public void shutdown() {
        this.plugin.getLogger().info("Shutting down storage...");
        this.implementation.shutdown();
    }
}
