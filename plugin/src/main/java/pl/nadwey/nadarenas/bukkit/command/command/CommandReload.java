package pl.nadwey.nadarenas.bukkit.command.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;

@Command(name = "nadarenas reload", aliases = { "nda reload" })
@Permission("nadarenas.command.reload")
public class CommandReload extends CommandBase{
    public CommandReload(BukkitNadArenasPlugin plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context CommandSender sender) {
        getPlugin().reload();

        sender.sendMessage("reloaded");
    }
}
