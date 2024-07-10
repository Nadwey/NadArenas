package pl.nadwey.nadarenas.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitNadArenasPluginLoader extends JavaPlugin {
    private BukkitNadArenasPlugin plugin;

    @Override
    public void onLoad() {
        getDataFolder().mkdir();
        getDataFolder().toPath().resolve("arenas").toFile().mkdir();

        plugin = new BukkitNadArenasPlugin(this);
    }

    @Override
    public void onEnable() {
        plugin.onEnable();
    }

    @Override
    public void onDisable() {
        plugin.onDisable();
    }
}
