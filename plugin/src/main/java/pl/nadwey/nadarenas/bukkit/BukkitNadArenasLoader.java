package pl.nadwey.nadarenas.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitNadArenasLoader extends JavaPlugin {
    private final BukkitNadArenasPlugin plugin;

    public BukkitNadArenasLoader() {
        plugin = new BukkitNadArenasPlugin(this);
    }

    @Override
    public void onLoad() {
        getDataFolder().mkdir();
        getDataFolder().toPath().resolve("arenas").toFile().mkdir();

        plugin.onLoad();
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
