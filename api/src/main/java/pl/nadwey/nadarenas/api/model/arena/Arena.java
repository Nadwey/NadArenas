package pl.nadwey.nadarenas.api.model.arena;

import lombok.Getter;
import lombok.Setter;
import pl.nadwey.nadarenas.api.math.Position;
import pl.nadwey.nadarenas.api.math.Region;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public class Arena {
    public static final String ARENA_NAME_REGEX = "^[a-zA-z0-9-_]+$";

    private @Nullable Integer id;
    private @Nonnull String name;

    private @Nonnull Boolean enableRestorer;

    private @Nonnull String world;
    private @Nonnull Position minPos;
    private @Nonnull Position maxPos;

    private @Nullable Integer restorerBlocksPerTick;

    private @Nullable String displayName;
    private @Nullable String description;
    private @Nullable String item;

    public Arena(@Nonnull String name, @Nonnull String world, @Nonnull Boolean enableRestorer, @Nonnull Position minPos, @Nonnull Position maxPos) {
        this.name = name;
        this.world = world;
        this.enableRestorer = enableRestorer;
        this.minPos = minPos;
        this.maxPos = maxPos;
    }

    public Arena(@Nonnull String name, @Nonnull String world, @Nonnull Boolean enableRestorer, Integer minX, Integer minY, Integer minZ, Integer maxX, Integer maxY, Integer maxZ) {
        this(name, world, enableRestorer, new Position(minX, minY, minZ), new Position(maxX, maxY, maxZ));
    }

    public Arena(@Nonnull String name, @Nonnull String world, @Nonnull Boolean enableRestorer, Region region) {
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
