package pl.nadwey.nadarenas.api.model.arena;

import lombok.Getter;
import lombok.Setter;
import pl.nadwey.nadarenas.api.math.Position;
import pl.nadwey.nadarenas.api.math.Region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class ArenaRecord {
    public static final String ARENA_NAME_REGEX = "^[a-zA-z0-9-_]+$";

    private @Nullable Integer id;
    private @NotNull String name;

    private @NotNull Boolean enableRestorer;

    private @NotNull String world;
    private @NotNull Position minPos;
    private @NotNull Position maxPos;

    private @Nullable Integer restorerBlocksPerTick;

    private @Nullable String displayName;
    private @Nullable String description;
    private @Nullable String item;

    public ArenaRecord(@NotNull String name, @NotNull String world, @NotNull Boolean enableRestorer, @NotNull Position minPos, @NotNull Position maxPos) {
        this.name = name;
        this.world = world;
        this.enableRestorer = enableRestorer;
        this.minPos = minPos;
        this.maxPos = maxPos;
    }

    public ArenaRecord(@NotNull String name, @NotNull String world, @NotNull Boolean enableRestorer, Integer minX, Integer minY, Integer minZ, Integer maxX, Integer maxY, Integer maxZ) {
        this(name, world, enableRestorer, new Position(minX, minY, minZ), new Position(maxX, maxY, maxZ));
    }

    public ArenaRecord(@NotNull String name, @NotNull String world, @NotNull Boolean enableRestorer, Region region) {
        this(name, world, enableRestorer, region.getMinPosition(), region.getMaxPosition());
    }

    public Region getRegion() {
        return new Region(minPos, maxPos);
    }

    /**
     * Returns display name if set, otherwise returns name
     */
    public String getPreferredName() {
        return displayName == null ? name : displayName;
    }
}
