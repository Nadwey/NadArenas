package pl.nadwey.nadarenas.storage.implementation;

import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.ArenaStorageImplementation;

public interface StorageImplementation extends ArenaStorageImplementation {
    NadArenas getPlugin();

    String getImplementationName();

    void init();

    void shutdown();
}
