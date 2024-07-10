package pl.nadwey.nadarenas.common.api;

import lombok.Getter;
import pl.nadwey.nadarenas.api.NadArenas;
import pl.nadwey.nadarenas.common.INadArenasPlugin;
import pl.nadwey.nadarenas.common.model.arena.ArenaManager;

public class NadArenasApiProvider implements NadArenas {
    private final INadArenasPlugin plugin;

    @Getter
    private final ArenaManager arenaManager;

    public NadArenasApiProvider(INadArenasPlugin plugin) {
        this.plugin = plugin;

        this.arenaManager = new ArenaManager(plugin);
    }
}
