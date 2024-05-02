package pl.nadwey.nadarenas.model.arena;

import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.utility.AdventureUtils;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;

import java.awt.*;

public class Arena {
    private @NotNull final String name;
    private @NotNull final String world;
    private String displayName;
    private String description;
    private Material item;

    public Arena(@NotNull String name, @NotNull String world) {
        this.name = name;
        this.world = world;
    }

    public Arena(@NotNull String name, @NotNull String world, String displayName, String description, Material item) {
        this(name, world);
        this.displayName = displayName;
        this.description = description;
        this.item = item;
    }

    public Arena(@NotNull String name, @NotNull World world) {
        this(name, world.getUID().toString());
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getWorld() {
        return world;
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
