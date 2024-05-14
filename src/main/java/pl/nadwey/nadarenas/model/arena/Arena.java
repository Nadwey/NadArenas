package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.model.Position;

public class Arena {
    public static final String ARENA_NAME_REGEX = "^[a-zA-z0-9-_]+$";

    private @NotNull String name;
    private @NotNull Boolean loaderSupport;
    private @NotNull String world;
    private @NotNull Position minPosition;
    private @NotNull Position maxPosition;

    private Integer loaderBlocksPerTick;

    private String displayName;
    private String description;
    private Material item;

    public Arena(@NotNull String name, @NotNull Boolean loaderSupport, @NotNull String world, @NotNull Position minPosition, @NotNull Position maxPosition) {
        this.name = name;
        this.loaderSupport = loaderSupport;
        this.world = world;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
    }

    public Arena(@NotNull String name, @NotNull Boolean loaderSupport, @NotNull World world, @NotNull Position minPosition, @NotNull Position maxPosition) {
        this(name, loaderSupport, world.getUID().toString(), minPosition, maxPosition);
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull Boolean getLoaderSupport() {
        return this.loaderSupport;
    }

    public void setLoaderSupport(@NotNull Boolean loaderSupport) {
        this.loaderSupport = loaderSupport;
    }

    public @NotNull String getWorld() {
        return world;
    }

    public void setWorld(@NotNull String world) {
        this.world = world;
    }

    public @NotNull Position getMinPosition() {
        return minPosition;
    }

    public void setMinPosition(@NotNull Position minPosition) {
        this.minPosition = minPosition;
    }

    public @NotNull Position getMaxPosition() {
        return maxPosition;
    }

    public void setMaxPosition(@NotNull Position maxPosition) {
        this.maxPosition = maxPosition;
    }

    public Integer getLoaderBlocksPerTick() {
        return loaderBlocksPerTick;
    }

    public void setLoaderBlocksPerTick(Integer loaderBlocksPerTick) {
        this.loaderBlocksPerTick = loaderBlocksPerTick;
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
