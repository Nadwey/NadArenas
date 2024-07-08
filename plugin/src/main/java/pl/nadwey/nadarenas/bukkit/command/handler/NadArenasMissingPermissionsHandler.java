package pl.nadwey.nadarenas.bukkit.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;

public class NadArenasMissingPermissionsHandler implements MissingPermissionsHandler<CommandSender> {
    private final BukkitNadArenasPlugin plugin;

    public NadArenasMissingPermissionsHandler(BukkitNadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> resultHandlerChain) {
        String permissions = missingPermissions.asJoinedText();
        CommandSender sender = invocation.sender();

        sender.sendMessage("no permission, " + permissions);
    }
}
