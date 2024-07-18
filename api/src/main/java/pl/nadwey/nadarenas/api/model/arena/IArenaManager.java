package pl.nadwey.nadarenas.api.model.arena;

import pl.nadwey.nadarenas.api.math.Region;

import java.util.List;

public interface IArenaManager {
    void createArena(String name, String world, boolean enableRestorer, Region region);

    ArenaRecord getArena(String name);
    List<ArenaRecord> getArenas();

    boolean arenaExists(String name);

    void setArenaRestorerEnabled(Integer arenaId, boolean enabled);
    void setArenaBlocksPerTick(Integer arenaId, Integer blocksPerTick);
    void setArenaDisplayName(Integer arenaId, String displayName);
    void setArenaDescription(Integer arenaId, String description);
    void setArenaItem(Integer arenaId, String material);

    void removeArena(Integer arenaId);
}
