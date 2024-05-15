package pl.nadwey.nadarenas.command.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.Arena;

import java.io.FileNotFoundException;
import java.util.Map;

@Command(name = "nadarenas arena restorer", aliases = {"nda arena restorer"})
@Permission("nadarenas.command.arena.restorer")
public class CommandArenaRestorer extends CommandBase {
    public CommandArenaRestorer(NadArenas plugin) {
        super(plugin);
    }

    @Execute(name = "load")
    @Permission("nadarenas.command.nadarenas.arena.restorer.load")
    public void arenaRestorerLoad(@Context CommandSender sender, @Arg("arena") Arena arena) {
        if (getPlugin().getArenaManager().isLoading(arena.getName())) {
            getPlugin().getLangManager().send(sender, "command-arena-load-already-loading", Map.of(
                    "arena", arena.getName()
            ));

            return;
        }

        try {
            getPlugin().getArenaManager().loadArena(arena, arena.getRestorerBlocksPerTick());
        } catch (FileNotFoundException e) {
            getPlugin().getLangManager().send(sender, "command-arena-load-failed-to-load-file", Map.of(
                    "arena", arena.getName()
            ));
            throw new RuntimeException(e);
        }
    }

    @Execute(name = "getEnabled")
    @Permission("nadarenas.command.nadarenas.arena.restorer.getenabled")
    public void arenaRestorerGetEnabled(@Context CommandSender sender, @Arg("arena") Arena arena) {
        getPlugin().getLangManager().send(sender, "command-arena-restorer-getenabled", Map.of(
                "state", arena.getEnableRestorer().toString()
        ));
    }

    @Execute(name = "setEnabled")
    @Permission("nadarenas.command.nadarenas.arena.restorer.setenabled")
    public void arenaRestorerSetEnabled(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("enabled") Boolean enabled) {
        if (getPlugin().getArenaManager().isLoading(arena.getName())) {
            getPlugin().getLangManager().send(sender, "command-arena-restorer-setenabled-currently-loading");
        }

        getPlugin().getStorageManager().arena().setArenaRestorerEnabled(arena.getName(), enabled);

        getPlugin().getLangManager().send(sender, "command-arena-restorer-setenabled-successful", Map.of(
                "state", enabled.toString()
        ));
    }

    @Execute(name = "setBlocksPerTick")
    @Permission("nadarenas.command.nadarenas.arena.restorer.setblockspertick")
    public void arenaRestorerSetBlocksPerTick(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("blocksPerTick") Integer blocksPerTick) {
        getPlugin().getStorageManager().arena().setArenaRestorerBlocksPerTick(arena.getName(), blocksPerTick);

        getPlugin().getLangManager().send(sender, "command-arena-restorer-setenabled-successful", Map.of(
                "blocksPerTick", blocksPerTick.toString()
        ));
    }
}
