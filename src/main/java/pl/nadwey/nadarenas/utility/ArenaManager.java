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

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ArenaManager {
    private class LoadTask {
        private final Arena arena;
        private final int blocksAtOnce;
        private final World world;
        private final Scanner arenaScanner;
        private boolean hasFinished = false;

        public LoadTask(Arena arena, int blocksAtOnce) throws FileNotFoundException {
            this.arena = arena;
            this.blocksAtOnce = blocksAtOnce;
            this.world = Bukkit.getWorld(UUID.fromString(arena.getWorld()));

            File arenaFile = getArenaFile(arena);

            arenaScanner = new Scanner(arenaFile);
        }

        private void processLine(String line) {
            String[] parts = line.split(" ");
            if (parts.length != 4) {
                plugin.getLogger().warning("ArenaManager: invalid arena data line: " + line);
                return;
            }

            Position position = new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]))
                    .add(this.arena.getMinPosition()); // get the absolute position from relative

            Location location = position.toLocation(world);

            Material material = Material.matchMaterial(parts[3]);

            if (material == null) {
                plugin.getLogger().warning("ArenaManager: null block material: " + line);
                return;
            }

            Block block = location.getBlock();
            block.setType(material);
        }

        /**
         * Finishes loading arena synchronously
         */
        public void finish() {
            if (hasFinished) {
                plugin.getLogger().warning("ArenaManager: LoadTask#finish called after finishing");
                return;
            }

            while (arenaScanner.hasNextLine()) {
                String line = arenaScanner.nextLine();

                if (line.isBlank())
                    return;

                processLine(line);
            }

            hasFinished = true;
            arenaScanner.close();
        }

        public void run() {
            if (hasFinished) {
                plugin.getLogger().warning("ArenaManager: LoadTask#run called after finishing");
                return;
            }

            for (int i = 0; i < blocksAtOnce; i++) {
                if (!arenaScanner.hasNextLine()) {
                    this.hasFinished = true;
                    arenaScanner.close();
                    return;
                }

                String line = arenaScanner.nextLine();

                if (line.isBlank())
                    return;

                processLine(line);
            }
        }
    }

    private final NadArenas plugin;
    private BukkitTask bukkitTask;
    private final Map<String, LoadTask> loadTasks;

    public ArenaManager(NadArenas plugin) {
        this.plugin = plugin;
        this.loadTasks = new ConcurrentHashMap<>();
    }

    private File getArenaFile(Arena arena) {
        return this.plugin.getDataFolder().toPath().resolve("arenas/" + arena.getName()).toFile();
    }

    public void loadArena(Arena arena, int blocksAtOnce) throws FileNotFoundException {
        this.plugin.getLogger().info("ArenaManager: loading arena " + arena.getName());

        if (loadTasks.containsKey(arena.getName())) {
            throw new IllegalStateException("ArenaManager: Arena " + arena.getName() + " is already being loaded");
        }

        loadTasks.put(arena.getName(), new LoadTask(arena, blocksAtOnce));
    }

    public void saveArena(Arena arena) throws IOException {
        World world = Bukkit.getWorld(UUID.fromString(arena.getWorld()));

        BufferedWriter writer = new BufferedWriter(new FileWriter(getArenaFile(arena)));

        for (int x = arena.getMinPosition().x(); x <= arena.getMaxPosition().x(); x++) {
            for (int y = arena.getMinPosition().y(); y <= arena.getMaxPosition().y(); y++) {
                for (int z = arena.getMinPosition().z(); z <= arena.getMaxPosition().z(); z++) {
                    Location loc = new Location(world, x, y, z);

                    // positions relative to the min position
                    writer.write((x - arena.getMinPosition().x()) + " " + (y - arena.getMinPosition().y()) + " " + (z - arena.getMinPosition().z()) + " " + loc.getBlock().getType() + "\n");
                }
            }
        }

        writer.close();
    }

    public void removeArena(Arena arena) {
        getArenaFile(arena).delete();
    }

    public void onEnable() {
        this.plugin.getLogger().info("ArenaManager: Enabling...");

        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                loadTasks.values().forEach(LoadTask::run);

                loadTasks.entrySet().removeIf(task -> task.getValue().hasFinished);
            }
        }.runTaskTimer(this.plugin, 0, 1);
    }

    public void onDisable() {
        if (isLoading())
            this.plugin.getLogger().warning("ArenaManager: onDisable called during loading arenas, finishing loading every arena, this may take a while...\n(THIS IS NOT A BUG)");

        if (bukkitTask != null) {
            bukkitTask.cancel();
        }

        loadTasks.values().forEach(LoadTask::finish);
        loadTasks.entrySet().removeIf(task -> task.getValue().hasFinished);

        if (!loadTasks.isEmpty()) {
            this.plugin.getLogger().severe("ArenaManager: Some arenas failed to finish when disabling ArenaManager");
        }
    }

    public boolean isLoading() {
        return !loadTasks.isEmpty();
    }

    public boolean isLoading(String arenaName) {
        return loadTasks.containsKey(arenaName);
    }
}
