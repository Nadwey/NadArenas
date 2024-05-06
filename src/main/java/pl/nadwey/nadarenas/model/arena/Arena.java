package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.model.Position;

public class Arena {
    public static final String ARENA_NAME_REGEX = "^[a-zA-z0-9-_]+$";

    private @NotNull final String name;

    private @NotNull final String world;
    private @NotNull final Position minPosition;
    private @NotNull final Position maxPosition;

    private String displayName;
    private String description;
    private Material item;

    public Arena(@NotNull String name, @NotNull String world, @NotNull Position minPosition, @NotNull Position maxPosition) {
        this.name = name;
        this.world = world;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    public Arena(@NotNull String name, @NotNull String world, @NotNull Position minPosition, @NotNull Position maxPosition, String displayName, String description, Material item) {
        this(name, world, minPosition, maxPosition);
        this.displayName = displayName;
        this.description = description;
        this.item = item;
    }

    public Arena(@NotNull String name, @NotNull World world, @NotNull Position minPosition, @NotNull Position maxPosition) {
        this(name, world.getUID().toString(), minPosition, maxPosition);
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getWorld() {
        return world;
    }

    public @NotNull Position getMinPosition() {
        return minPosition;
    }

    public @NotNull Position getMaxPosition() {
        return maxPosition;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Material getItem() {
        return item;
    }

    public void setItem(Material item) {
        this.item = item;
    }

    /**
     * Returns display name, or name if display name is null
     */
    public String getPreferredName() {
        return getDisplayName() != null ? getDisplayName() : getName();
    }
}
