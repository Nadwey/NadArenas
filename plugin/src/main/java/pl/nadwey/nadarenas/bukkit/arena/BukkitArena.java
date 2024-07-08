package pl.nadwey.nadarenas.bukkit.arena;

import lombok.Getter;
import org.bukkit.Material;
import pl.nadwey.nadarenas.api.model.arena.Arena;

@Getter
public class BukkitArena {
    private final Arena arena;

    public BukkitArena(Arena arena) {
        this.arena = arena;
    }

    public Material getItemAsMaterial() {
        if (arena.getItem() == null) {
            return null;
        }

        return Material.matchMaterial(arena.getItem());
    }

    public void setItem(Material material) {
        arena.setItem(material.toString());
    }
}
