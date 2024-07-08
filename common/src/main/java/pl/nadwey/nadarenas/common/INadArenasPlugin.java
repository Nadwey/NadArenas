package pl.nadwey.nadarenas.common;

import pl.nadwey.nadarenas.common.config.ConfigManager;
import pl.nadwey.nadarenas.common.storage.Storage;

import java.nio.file.Path;
import java.util.logging.Logger;

public interface INadArenasPlugin {
    Logger getLogger();

    Path getDataDir();

    Storage getStorage();

    ConfigManager getConfigManager();
}
