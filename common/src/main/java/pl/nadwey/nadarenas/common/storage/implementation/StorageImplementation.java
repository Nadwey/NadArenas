package pl.nadwey.nadarenas.common.storage.implementation;

import pl.nadwey.nadarenas.common.INadArenasPlugin;

public interface StorageImplementation extends ArenaStorageImplementation {
    INadArenasPlugin getPlugin();

    void init();

    void shutdown();
}
