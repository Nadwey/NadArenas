package pl.nadwey.nadarenas.common.plugin;

import pl.nadwey.nadarenas.common.plugin.bootstrap.NadArenasBootstrap;
import pl.nadwey.nadarenas.common.storage.StorageManager;

public interface NadArenasPlugin {
    NadArenasBootstrap getBootstrap();

    StorageManager getStorageManager();
}
