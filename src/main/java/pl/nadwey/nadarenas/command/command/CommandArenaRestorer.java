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
        if (this.getPlugin().getArenaManager().isLoading(arena.getName())) {
            this.getPlugin().getLangManager().send(sender, "command-arena-load-already-loading", Map.of(
                    "arena", arena.getName()
            ));

            return;
        }

        try {
            this.getPlugin().getArenaManager().loadArena(arena, arena.getRestorerBlocksPerTick());
        } catch (FileNotFoundException e) {
            this.getPlugin().getLangManager().send(sender, "command-arena-load-failed-to-load-file", Map.of(
                    "arena", arena.getName()
            ));
            throw new RuntimeException(e);
        }
    }

    @Execute(name = "setEnabled")
    @Permission("nadarenas.command.nadarenas.arena.restorer.setenabled")
    public void arenaRestorerSetEnabled(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("enabled") Boolean isEnabled) {
        
    }

    @Execute(name = "setBlocksPerTick")
    @Permission("nadarenas.command.nadarenas.arena.restorer.setblockspertick")
    public void arenaRestorerSetBlocksPerTick(@Context CommandSender sender, @Arg("arena") Arena arena) {

    }
}
