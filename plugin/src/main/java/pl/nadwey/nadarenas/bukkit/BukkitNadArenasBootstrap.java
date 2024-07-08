package pl.nadwey.nadarenas.bukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.common.plugin.bootstrap.NadArenasBootstrap;
import pl.nadwey.nadarenas.common.plugin.logging.JavaPluginLogger;
import pl.nadwey.nadarenas.common.plugin.logging.NadArenasLogger;

import java.nio.file.Path;

@Getter
public class BukkitNadArenasBootstrap implements NadArenasBootstrap {
    private final JavaPlugin loader;

    public BukkitNadArenasBootstrap(JavaPlugin loader) {
        this.loader = loader;
    }

    @Override
    public NadArenasLogger getLogger() {
        return new JavaPluginLogger(loader.getLogger());
    }

    @Override
    public Path getDataDirectory() {
        return loader.getDataFolder().toPath().toAbsolutePath();
    }
}
