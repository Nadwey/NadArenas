package pl.nadwey.nadarenas.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;

import java.util.Map;

public class NadArenasMissingPermissionsHandler implements MissingPermissionsHandler<CommandSender> {
    private final NadArenas plugin;

    public NadArenasMissingPermissionsHandler(NadArenas plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> resultHandlerChain) {
        String permissions = missingPermissions.asJoinedText();
        CommandSender sender = invocation.sender();

        plugin.getLangManager().send(sender, "no-permissions-to-use-command", Map.of(
                "permission", permissions
        ));
    }
}
