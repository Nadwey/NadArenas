package pl.nadwey.nadarenas.bukkit.command.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.api.model.arena.Arena;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@Command(name = "nadarenas arena restorer", aliases = {"nda arena restorer"})
@Permission("nadarenas.command.arena.restorer")
public class CommandArenaRestorer extends CommandBase {
    public CommandArenaRestorer(BukkitNadArenasPlugin plugin) {
        super(plugin);
    }

    @Execute(name = "load")
    @Permission("nadarenas.command.nadarenas.arena.restorer.load")
    public void arenaRestorerLoad(@Context CommandSender sender, @Arg("arena") Arena arena) {
        if (getPlugin().getArenaRestorer().isLoading(arena.getName())) {
            sender.sendMessage("arena already loading");

            return;
        }

        try {
            getPlugin().getArenaRestorer().loadArena(arena, arena.getRestorerBlocksPerTick());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Execute(name = "getEnabled")
    @Permission("nadarenas.command.nadarenas.arena.restorer.getenabled")
    public void arenaRestorerGetEnabled(@Context CommandSender sender, @Arg("arena") Arena arena) {
        sender.sendMessage("restorer enabled: " + arena.getEnableRestorer());

    }

    @Execute(name = "setEnabled")
    @Permission("nadarenas.command.nadarenas.arena.restorer.setenabled")
    public void arenaRestorerSetEnabled(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("enabled") Boolean enabled) throws SQLException {
        if (getPlugin().getArenaRestorer().isLoading(arena.getName())) {
            sender.sendMessage("arena is currently loading");
            return;
        }

        getPlugin().getStorageManager().getImplementation().setArenaRestorerEnabled(arena.getId(), enabled);

        sender.sendMessage("restorer enabled: " + enabled);
    }

    @Execute(name = "setBlocksPerTick")
    @Permission("nadarenas.command.nadarenas.arena.restorer.setblockspertick")
    public void arenaRestorerSetBlocksPerTick(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("blocksPerTick") Integer blocksPerTick) throws SQLException {
        getPlugin().getStorageManager().getImplementation().setArenaRestorerBlocksPerTick(arena.getId(), blocksPerTick);

        sender.sendMessage("restorer blocks per tick: " + blocksPerTick);
    }
}
