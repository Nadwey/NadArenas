package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ArenaStorageImplementation {
    void createArena(Arena arena) throws SQLException;

    Arena getArena(String name) throws SQLException;

    List<Arena> getArenas() throws SQLException;

    void setArenaDisplayName(String arena, String displayName) throws SQLException;

    void removeArena(String name) throws SQLException;

    void setArenaDescription(String name, String description) throws SQLException;

    void setArenaItem(String name, Material item) throws SQLException;
}
