package pl.nadwey.nadarenas.database.types;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public record Arena(@NotNull String name, @NotNull String world, String displayName, String description, Material item) {

}
