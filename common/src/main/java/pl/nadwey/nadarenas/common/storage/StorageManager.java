package pl.nadwey.nadarenas.common.storage;

import lombok.Getter;
import pl.nadwey.nadarenas.common.plugin.NadArenasPlugin;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;

public class StorageManager {
    @Getter
    private final NadArenasPlugin plugin;
    @Getter
    private Storage storage;

    public StorageManager(NadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        plugin.getBootstrap().getLogger().info("Enabling storage and performing migrations...");
        storage = new StorageFactory(plugin).getInstance();
    }

    public void onDisable() {
        plugin.getBootstrap().getLogger().info("Shutting down storage...");
        storage.shutdown();
    }

    public StorageImplementation getImplementation() {
        return storage.getImplementation();
    }
}
