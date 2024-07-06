package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import pl.nadwey.nadarenas.model.Region;
import pl.nadwey.nadarenas.storage.StorageManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ArenaManager implements ArenaStorageImplementation {
    private final StorageManager storageManager;

    public ArenaManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }


    @Override
    public void createArena(Arena arena) {
        Objects.requireNonNull(arena);

        try {
            storageManager.getStorage().getImplementation().createArena(arena);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Arena getArenaByName(String name) {
        Objects.requireNonNull(name);

        try {
            return storageManager.getStorage().getImplementation().getArenaByName(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean arenaExists(String name) {
        Objects.requireNonNull(name);

        try {
            return storageManager.getStorage().getImplementation().arenaExists(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Arena> getAllArenas() {
        try {
            return Collections.unmodifiableList(storageManager.getStorage().getImplementation().getAllArenas());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaRestorerBlocksPerTick(Integer arenaId, Integer restorerBlocksPerTick) {
        Objects.requireNonNull(arenaId);
        Objects.requireNonNull(restorerBlocksPerTick);

        try {
            storageManager.getStorage().getImplementation().setArenaRestorerBlocksPerTick(arenaId, restorerBlocksPerTick);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaRestorerEnabled(Integer arenaId, Boolean enabled) {
        Objects.requireNonNull(arenaId);
        Objects.requireNonNull(enabled);

        try {
            storageManager.getStorage().getImplementation().setArenaRestorerEnabled(arenaId, enabled);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDisplayName(Integer arenaId, String displayName) {
        Objects.requireNonNull(arenaId);
        Objects.requireNonNull(displayName);

        try {
            storageManager.getStorage().getImplementation().setArenaDisplayName(arenaId, displayName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDescription(Integer arenaId, String description) {
        Objects.requireNonNull(arenaId);
        Objects.requireNonNull(description);

        try {
            storageManager.getStorage().getImplementation().setArenaDescription(arenaId, description);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaItem(Integer arenaId, Material item) {
        Objects.requireNonNull(arenaId);
        Objects.requireNonNull(item);

        try {
            storageManager.getStorage().getImplementation().setArenaItem(arenaId, item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeArena(Integer arenaId)  {
        Objects.requireNonNull(arenaId);

        try {
            storageManager.getStorage().getImplementation().removeArena(arenaId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Arena> getOverlappingArenas(Region region) {
        List<Arena> arenas = getAllArenas();
        List<Arena> overlapping = new ArrayList<>();

        for (Arena arena : arenas) {
            if (arena.getRegion().overlaps(region))
                overlapping.add(arena);
        }

        return overlapping;
    }
}