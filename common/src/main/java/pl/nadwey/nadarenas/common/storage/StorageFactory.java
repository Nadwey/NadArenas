package pl.nadwey.nadarenas.common.storage;

import pl.nadwey.nadarenas.common.INadArenasPlugin;
import pl.nadwey.nadarenas.common.lang.LangMessage;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.common.storage.implementation.sql.SqlStorage;
import pl.nadwey.nadarenas.common.storage.implementation.sql.connection.file.SqliteConnectionFactory;

import java.util.Map;

public class StorageFactory {
    private final INadArenasPlugin plugin;

    public StorageFactory(INadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    public Storage getInstance() {
        Storage storage;
        StorageType type = plugin.getConfigManager().getStorageConfig().getStorageMethod();

        plugin.getLogger().info(plugin.getLangManager().getMessage(LangMessage.STORAGE_LOADING_PROVIDER, Map.of("provider", type.toString())));

        storage = new Storage(plugin, createNewImplementation(type));

        storage.init();
        return storage;
    }

    private StorageImplementation createNewImplementation(StorageType method) {
        switch (method) {
            case SQLITE:
                return new SqlStorage(
                        plugin,
                        new SqliteConnectionFactory(plugin.getDataDir().resolve("nadarenas.db"))
                );
            default:
                throw new RuntimeException("Unknown storage method: " + method);
        }
    }
}
