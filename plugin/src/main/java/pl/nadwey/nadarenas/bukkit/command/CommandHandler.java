package pl.nadwey.nadarenas.bukkit.command;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.api.model.arena.Arena;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;
import pl.nadwey.nadarenas.bukkit.command.argument.ArenaRecordArgument;
import pl.nadwey.nadarenas.bukkit.command.argument.MaterialArgument;
import pl.nadwey.nadarenas.bukkit.command.command.CommandArenas;
import pl.nadwey.nadarenas.bukkit.command.command.CommandReload;
import pl.nadwey.nadarenas.bukkit.command.handler.NadArenasInvalidUsageHandler;
import pl.nadwey.nadarenas.bukkit.command.handler.NadArenasMissingPermissionsHandler;

public class CommandHandler {
    private final LiteCommands<CommandSender> liteCommands;

    public CommandHandler(BukkitNadArenasPlugin plugin) {
        liteCommands = LiteBukkitFactory.builder("nadarenas", plugin.getLoader())
                .commands(
                        new CommandArenas(plugin),
                        new CommandReload(plugin)
                )
                .argument(Arena.class, new ArenaRecordArgument(plugin))
                .argument(Material.class, new MaterialArgument())
                .invalidUsage(new NadArenasInvalidUsageHandler(plugin))
                .missingPermission(new NadArenasMissingPermissionsHandler(plugin))
                .build();
    }

    public void onDisable() {
        liteCommands.unregister();
    }
}
