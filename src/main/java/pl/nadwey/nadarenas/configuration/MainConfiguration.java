package pl.nadwey.nadarenas.configuration;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;

public class MainConfiguration implements Reloadable {
    private final NadArenas plugin;
    private FileConfiguration config;

    public MainConfiguration(NadArenas plugin) {
        this.plugin = plugin;

        reload();
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public Location getLocation(String path) {
        return config.getLocation(path);
    }

    @Override
    public void reload() {
        this.plugin.reloadConfig();
        config = this.plugin.getConfig();
    }
}
