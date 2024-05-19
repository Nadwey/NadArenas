package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import org.jooq.generated.tables.records.ArenaRecord;

import java.sql.SQLException;
import java.util.List;

public interface ArenaStorageImplementation {
    void createArena(ArenaRecord arena) throws SQLException;

    ArenaRecord getArenaByName(String name) throws SQLException;

    boolean arenaExists(String name) throws SQLException;

    List<ArenaRecord> getAllArenas() throws SQLException;

    void setArenaRestorerBlocksPerTick(Integer arenaId, Integer restorerBlocksPerTick) throws SQLException;

    void setArenaRestorerEnabled(Integer arenaId, Boolean enabled) throws SQLException;

    void setArenaDisplayName(Integer arenaId, String displayName) throws SQLException;

    void setArenaDescription(Integer arenaId, String description) throws SQLException;

    void setArenaItem(Integer arenaId, Material item) throws SQLException;

    void removeArena(Integer arenaId) throws SQLException;
}
