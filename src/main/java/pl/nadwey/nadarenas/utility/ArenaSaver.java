package pl.nadwey.nadarenas.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.Arena;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class ArenaSaver {
    public static void saveArena(Arena arena) throws IOException {
        World world = Bukkit.getWorld(UUID.fromString(arena.getWorld()));

        BufferedWriter writer = new BufferedWriter(new FileWriter(NadArenas.getInstance().getDataFolder().toPath().resolve("arenas/" + arena.getName()).toFile()));

        for (int x = arena.getMinPosition().x(); x <= arena.getMaxPosition().x(); x++) {
            for (int y = arena.getMinPosition().y(); y <= arena.getMaxPosition().y(); y++) {
                for (int z = arena.getMinPosition().z(); z <= arena.getMaxPosition().z(); z++) {
                    Location loc = new Location(world, x, y, z);

                    writer.write(x + " " + y + " " + z + " " + loc.getBlock().getType() + "\n");
                }
            }
        }

        writer.close();
    }
}
