package pl.nadwey.nadarenas.command.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;

@Command(name = "nadarenas reload", aliases = { "nda reload" })
@Permission("nadarenas.command.reload")
public class CommandReload extends CommandBase{
    public CommandReload(NadArenas plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context CommandSender sender) {
        if (this.getPlugin().getArenaManager().isLoading()) {
            this.getPlugin().getLangManager().send(sender, "command-reload-arena-restorer-warning");
        }

        getPlugin().reload();

        this.getPlugin().getLangManager().send(sender,"command-reload-reload-finished");
    }
}
