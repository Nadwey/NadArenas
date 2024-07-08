package pl.nadwey.nadarenas.common.plugin.bootstrap;

import pl.nadwey.nadarenas.common.plugin.logging.NadArenasLogger;

import java.nio.file.Path;

public interface NadArenasBootstrap {
    NadArenasLogger getLogger();

    Path getDataDirectory();
}
