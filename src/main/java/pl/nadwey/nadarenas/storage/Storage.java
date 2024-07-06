package pl.nadwey.nadarenas.storage;

import lombok.Getter;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;

public class Storage {
    private final NadArenas plugin;
    @Getter
    private final StorageImplementation implementation;

    public Storage(NadArenas plugin, StorageImplementation implementation) {
        this.plugin = plugin;
        this.implementation = implementation;
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
