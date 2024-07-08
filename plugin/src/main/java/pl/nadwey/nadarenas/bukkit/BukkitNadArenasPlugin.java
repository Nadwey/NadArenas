package pl.nadwey.nadarenas.bukkit;

import lombok.Getter;
import pl.nadwey.nadarenas.bukkit.command.CommandHandler;
import pl.nadwey.nadarenas.bukkit.restorer.ArenaRestorer;
import pl.nadwey.nadarenas.common.plugin.AbstractNadArenasPlugin;

public final class BukkitNadArenasPlugin extends AbstractNadArenasPlugin {
    @Getter
    private BukkitNadArenasBootstrap bootstrap;
    @Getter
    private final BukkitNadArenasLoader loader;
    @Getter
    private ArenaRestorer arenaRestorer;
    private CommandHandler commandHandler;

    public BukkitNadArenasPlugin(BukkitNadArenasLoader loader) {
        bootstrap = new BukkitNadArenasBootstrap(loader);

        this.loader = loader;
    }

    @Override
    protected void load() {

    }

    @Override
    protected void enable() {
        loader.saveDefaultConfig();

        arenaRestorer = new ArenaRestorer(this);
        arenaRestorer.onEnable();

        commandHandler = new CommandHandler(this);
        commandHandler.onEnable();
    }

    @Override
    protected void disable() {
        commandHandler.onDisable();

        arenaRestorer.onDisable();
    }
}
