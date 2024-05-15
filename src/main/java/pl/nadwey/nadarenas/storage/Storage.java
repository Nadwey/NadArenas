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
        plugin.getLogger().info("Enabling storage and performing migrations...");
        implementation.init();
    }

    public void shutdown() {
        plugin.getLogger().info("Shutting down storage...");
        implementation.shutdown();
    }
}
