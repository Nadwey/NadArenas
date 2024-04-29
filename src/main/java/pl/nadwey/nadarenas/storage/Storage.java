package pl.nadwey.nadarenas.storage;

import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;

public class Storage {
    private final StorageImplementation implementation;

    public Storage(StorageImplementation implementation) {
        this.implementation = implementation;
    }

    public StorageImplementation getImplementation() {
        return implementation;
    }

    public void init() {
        this.implementation.init();
    }

    public void shutdown() {
        this.implementation.shutdown();
    }
}
