package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.storage.StorageManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ArenaStorageManager implements ArenaStorageImplementation {
    private final StorageManager storageManager;

    public ArenaStorageManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    public void createArena(@NotNull Arena arena) {
        Objects.requireNonNull(arena, "arena");
        try {
            this.storageManager.getStorage().getImplementation().createArena(arena);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Arena getArena(@NotNull String name) {
        Objects.requireNonNull(name);

        try {
            return this.storageManager.getStorage().getImplementation().getArena(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean arenaExists(@NotNull String name) {
        Objects.requireNonNull(name);

        return getArena(name) != null;
    }

    public List<Arena> getArenas() {
        try {
            return this.storageManager.getStorage().getImplementation().getArenas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDisplayName(@NotNull String arena, @NotNull String displayName) {
        Objects.requireNonNull(arena, "arena");
        Objects.requireNonNull(displayName, "displayName");

        try {
            this.storageManager.getStorage().getImplementation().setArenaDisplayName(arena, displayName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDescription(@NotNull String name, @NotNull String description) throws SQLException {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(description, "description");

        try {
            this.storageManager.getStorage().getImplementation().setArenaDescription(name, description);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaItem(@NotNull String name, @NotNull Material item) throws SQLException {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(item, "item");

        try {
            this.storageManager.getStorage().getImplementation().setArenaItem(name, item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeArena(@NotNull String name) throws SQLException {
        Objects.requireNonNull(name, "name");

        try {
            this.storageManager.getStorage().getImplementation().removeArena(name);

            this.storageManager.getPlugin().getArenaManager().removeArena(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaLoaderBlocksPerTick(String arena, Integer loaderBlocksPerTick) {
        Objects.requireNonNull(arena, "arena");
        Objects.requireNonNull(loaderBlocksPerTick, "loaderBlocksPerTick");

        try {
            this.storageManager.getStorage().getImplementation().setArenaLoaderBlocksPerTick(arena, loaderBlocksPerTick);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaLoaderEnabled(String arena, Boolean enabled) {
        Objects.requireNonNull(arena, "arena");
        Objects.requireNonNull(enabled, "enabled");

        try {
            this.storageManager.getStorage().getImplementation().setArenaLoaderEnabled(arena, enabled);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}