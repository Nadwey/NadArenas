package pl.nadwey.nadarenas.common.storage;

import pl.nadwey.nadarenas.common.plugin.NadArenasPlugin;
import pl.nadwey.nadarenas.common.storage.implementation.sql.connection.file.SqliteConnectionFactory;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.common.storage.implementation.sql.SqlStorage;

import java.util.Set;

public class StorageFactory {
    private final NadArenasPlugin plugin;

    public StorageFactory(NadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    public Set<StorageType> getRequiredTypes() {
        return Set.of(StorageType.SQLITE); // TODO
    }

    public Storage getInstance() {
        Storage storage;
        StorageType type = StorageType.SQLITE; // TODO

        plugin.getBootstrap().getLogger().info("Loading storage provider... [" + type.name() + "]");
        storage = new Storage(plugin, createNewImplementation(type));

        storage.init();
        return storage;
    }

    private StorageImplementation createNewImplementation(StorageType method) {
        switch (method) {
            case SQLITE:
                return new SqlStorage(
                        plugin,
                        new SqliteConnectionFactory(plugin.getBootstrap().getDataDirectory().resolve("nadarenas.db"))
                );
            default:
                throw new RuntimeException("Unknown storage method: " + method);
        }
    }
}
