package pl.nadwey.nadarenas.common.storage;

import lombok.Getter;
import pl.nadwey.nadarenas.common.plugin.NadArenasPlugin;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;

public class Storage {
    private final NadArenasPlugin plugin;
    @Getter
    private final StorageImplementation implementation;

    public Storage(NadArenasPlugin plugin, StorageImplementation implementation) {
        this.plugin = plugin;
        this.implementation = implementation;
    }

    public void init() {
        plugin.getBootstrap().getLogger().info("Enabling storage and performing migrations...");
        implementation.init();
    }

    public void shutdown() {
        plugin.getBootstrap().getLogger().info("Shutting down storage...");
        implementation.shutdown();
    }
}
