package pl.nadwey.nadarenas.common.model.arena;

import lombok.Getter;
import pl.nadwey.nadarenas.common.INadArenasPlugin;
import pl.nadwey.nadarenas.api.math.Region;
import pl.nadwey.nadarenas.api.model.arena.Arena;
import pl.nadwey.nadarenas.api.model.arena.IArenaManager;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;

import java.sql.SQLException;
import java.util.List;

public class ArenaManager implements IArenaManager {
    private final INadArenasPlugin plugin;

    public ArenaManager(INadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createArena(String name, String world, boolean enableRestorer, Region region) {
        Arena arena = new Arena(name, world, enableRestorer, region);

        try {
            plugin.getStorage().getImplementation().createArena(arena);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Arena getArena(String name) {
        try {
            return plugin.getStorage().getImplementation().getArenaByName(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Arena> getArenas() {
        try {
            return plugin.getStorage().getImplementation().getAllArenas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean arenaExists(String name) {
        return getArena(name) != null;
    }

    @Override
    public void setArenaRestorerEnabled(Integer arenaId, boolean enabled) {
        try {
            plugin.getStorage().getImplementation().setArenaRestorerEnabled(arenaId, enabled);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaBlocksPerTick(Integer arenaId, Integer blocksPerTick) {
        try {
            plugin.getStorage().getImplementation().setArenaRestorerBlocksPerTick(arenaId, blocksPerTick);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDisplayName(Integer arenaId, String displayName) {
        try {
            plugin.getStorage().getImplementation().setArenaDisplayName(arenaId, displayName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDescription(Integer arenaId, String description) {
        try {
            plugin.getStorage().getImplementation().setArenaDescription(arenaId, description);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaItem(Integer arenaId, String material) {
        try {
            plugin.getStorage().getImplementation().setArenaItem(arenaId, material);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeArena(Integer arenaId) {
        try {
            plugin.getStorage().getImplementation().removeArena(arenaId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
