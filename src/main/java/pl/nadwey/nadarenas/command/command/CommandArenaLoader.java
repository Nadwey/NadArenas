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

@Command(name = "nadarenas arena loader", aliases = {"nda arena loader"})
@Permission("nadarenas.command.arena.loader")
public class CommandArenaLoader extends CommandBase {
    public CommandArenaLoader(NadArenas plugin) {
        super(plugin);
    }

    @Execute(name = "load")
    @Permission("nadarenas.command.nadarenas.arena.loader.load")
    public void arenaLoaderLoad(@Context CommandSender sender, @Arg("arena") Arena arena) {
        if (this.getPlugin().getArenaManager().isLoading(arena.getName())) {
            this.getPlugin().getLangManager().send(sender, "command-arena-load-already-loading", Map.of(
                    "arena", arena.getName()
            ));

            return;
        }

        try {
            this.getPlugin().getArenaManager().loadArena(arena, arena.getLoaderBlocksPerTick());
        } catch (FileNotFoundException e) {
            this.getPlugin().getLangManager().send(sender, "command-arena-load-failed-to-load-file", Map.of(
                    "arena", arena.getName()
            ));
            throw new RuntimeException(e);
        }
    }

    @Execute(name = "setEnabled")
    @Permission("nadarenas.command.nadarenas.arena.loader.setenabled")
    public void arenaLoaderSetEnabled(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("enabled") Boolean isEnabled) {
        
    }

    @Execute(name = "setLoaderBlocksPerTick")
    @Permission("nadarenas.command.nadarenas.arena.loader.setblockspertick")
    public void arenaLoaderSetBlocksPerTick(@Context CommandSender sender, @Arg("arena") Arena arena) {

    }
}
