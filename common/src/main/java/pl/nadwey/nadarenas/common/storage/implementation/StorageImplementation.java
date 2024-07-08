package pl.nadwey.nadarenas.common.storage.implementation;

import pl.nadwey.nadarenas.common.plugin.NadArenasPlugin;

public interface StorageImplementation extends ArenaStorageImplementation {
    NadArenasPlugin getPlugin();

    String getImplementationName();

    void init();

    void shutdown();
}
