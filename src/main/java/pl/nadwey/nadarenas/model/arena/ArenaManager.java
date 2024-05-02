package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.NadArenas;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ArenaManager implements ArenaStorageImplementation {
    private final NadArenas plugin;

    public ArenaManager(NadArenas plugin) {
        this.plugin = plugin;
    }

    public void createArena(@NotNull Arena arena) {
        Objects.requireNonNull(arena, "arena");
        try {
            this.plugin.getStorage().getImplementation().createArena(arena);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Arena getArena(@NotNull String name) {
        Objects.requireNonNull(name);

        try {
            return this.plugin.getStorage().getImplementation().getArena(name);
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
            return this.plugin.getStorage().getImplementation().getArenas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDisplayName(@NotNull String arena, @NotNull String displayName) {
        Objects.requireNonNull(arena, "arena");
        Objects.requireNonNull(displayName, "displayName");


        try {
            this.plugin.getStorage().getImplementation().setArenaDisplayName(arena, displayName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeArena(@NotNull String name) throws SQLException {
        Objects.requireNonNull(name, "name");

        try {
            this.plugin.getStorage().getImplementation().removeArena(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaDescription(@NotNull String name, @NotNull String description) throws SQLException {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(description, "description");

        try {
            this.plugin.getStorage().getImplementation().setArenaDescription(name, description);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArenaItem(@NotNull String name, @NotNull Material item) throws SQLException {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(item, "item");

        try {
            this.plugin.getStorage().getImplementation().setArenaItem(name, item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}