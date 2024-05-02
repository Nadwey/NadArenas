package pl.nadwey.nadarenas.storage;

import com.google.common.collect.ImmutableSet;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.storage.implementation.sql.SqlStorage;
import pl.nadwey.nadarenas.storage.implementation.sql.connection.file.SqliteConnectionFactory;

import java.util.Set;

public class StorageFactory {
    private final NadArenas plugin;

    public StorageFactory(NadArenas plugin) {
        this.plugin = plugin;
    }

    public Set<StorageType> getRequiredTypes() {
        return ImmutableSet.of(StorageType.SQLITE); // TODO
    }

    public Storage getInstance() {
        Storage storage;
        StorageType type = StorageType.SQLITE; // TODO

        this.plugin.getLogger().info("Loading storage provider... [" + type.name() + "]");
        storage = new Storage(createNewImplementation(type));

        storage.init();
        return storage;
    }

    private StorageImplementation createNewImplementation(StorageType method) {
        switch (method) {
            case SQLITE:
                return new SqlStorage(
                        this.plugin,
                        new SqliteConnectionFactory(this.plugin.getDataFolder().toPath().resolve("nadarenas.db"))
                );
            default:
                throw new RuntimeException("Unknown storage method: " + method);
        }
    }
}
