package pl.nadwey.nadarenas.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.arena.Arena;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;

public class ArenaLoader {
    private static class LoadTask {
        private final int blocksAtOnce;
        private final World world;
        private final Scanner arenaReader;
        private boolean hasFinished = false;

        public LoadTask(Arena arena, int blocksAtOnce) throws FileNotFoundException {
            this.blocksAtOnce = blocksAtOnce;
            this.world = Bukkit.getWorld(UUID.fromString(arena.getWorld()));

            File arenaFile = new File(NadArenas.getInstance().getDataFolder(), "arenas/" + arena.getName());

            arenaReader = new Scanner(arenaFile);
        }

        public void run() {
            if (hasFinished) {
                NadArenas.getInstance().getLogger().log(Level.WARNING, "ArenaLoader: LoadTask#run called after finishing");
                return;
            }

            for (int i = 0; i < blocksAtOnce; i++) {
                if (!arenaReader.hasNextLine()) {
                    this.hasFinished = true;
                    arenaReader.close();
                    return;
                }

                String line = arenaReader.nextLine();

                if (line.isBlank()) {
                    return;
                }

                String[] parts = line.split(" ");
                if (parts.length != 4) {
                    NadArenas.getInstance().getLogger().log(Level.WARNING, "ArenaLoader: invalid arena data line: " + line);
                    return;
                }

                Position position = new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                Location location = position.toLocation(world);

                Material material = Material.matchMaterial(parts[3]);

                if (material == null) {
                    NadArenas.getInstance().getLogger().log(Level.WARNING, "ArenaLoader: null block material: " + line);
                    return;
                }

                NadArenas.getInstance().getLogger().log(Level.INFO, "ArenaLoader: loading: " + line);

                Block block = location.getBlock();
                block.setType(material);
            }
        }
    }

    private BukkitTask bukkitTask;
    private final Map<String, LoadTask> loadTasks;

    public ArenaLoader() {
        loadTasks = new HashMap<>();
    }

    private void finished(Arena arena) {
        loadTasks.remove(arena.getName());
    }

    public void load(Arena arena, int blocksAtOnce) {
        if (loadTasks.containsKey(arena.getName())) {
            throw new IllegalStateException("ArenaLoader: Arena " + arena.getName() + " is already being loaded");
        }

        try {
            loadTasks.put(arena.getName(), new LoadTask(arena, blocksAtOnce));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEnable() {
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                loadTasks.values().forEach(LoadTask::run);

                loadTasks.entrySet().removeIf(task -> task.getValue().hasFinished);
            }
        }.runTaskTimer(NadArenas.getInstance(), 0, 1);
    }

    public void onDisable() {
        if (bukkitTask != null) {
            bukkitTask.cancel();
        }
    }
}
