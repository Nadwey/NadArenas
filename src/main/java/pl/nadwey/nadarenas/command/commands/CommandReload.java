package pl.nadwey.nadarenas.command.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.command.CommandHandler;

@Command(name = "nadarenas reload", aliases = { "nda reload" })
@Permission("nadarenas.command.reload")
public class CommandReload extends CommandBase{
    public CommandReload(NadArenas plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context CommandSender sender) {
        if (this.getPlugin().getArenaLoader().isLoading()) {
            sender.sendMessage(CommandHandler.warnMessage(this.getPlugin().getLangManager().getString("command-reload-arena-loader-warning")));
        }

        getPlugin().reload();

        sender.sendMessage(CommandHandler.warnMessage(this.getPlugin().getLangManager().getString("command-reload-reload-finished")));
    }
}
