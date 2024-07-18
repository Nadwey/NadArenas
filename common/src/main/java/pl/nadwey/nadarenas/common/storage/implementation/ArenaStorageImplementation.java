package pl.nadwey.nadarenas.common.storage.implementation;

import pl.nadwey.nadarenas.api.model.arena.ArenaRecord;

import java.sql.SQLException;
import java.util.List;

public interface ArenaStorageImplementation {
    void createArena(ArenaRecord arenaRecord) throws SQLException;

    ArenaRecord getArenaByName(String name) throws SQLException;

    List<ArenaRecord> getAllArenas() throws SQLException;

    void setArenaRestorerBlocksPerTick(Integer arenaId, Integer restorerBlocksPerTick) throws SQLException;

    void setArenaRestorerEnabled(Integer arenaId, Boolean enabled) throws SQLException;

    void setArenaDisplayName(Integer arenaId, String displayName) throws SQLException;

    void setArenaDescription(Integer arenaId, String description) throws SQLException;

    void setArenaItem(Integer arenaId, String item) throws SQLException;

    void removeArena(Integer arenaId) throws SQLException;
}
