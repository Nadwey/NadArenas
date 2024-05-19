package pl.nadwey.nadarenas.command;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;
import pl.nadwey.nadarenas.command.argument.MaterialArgument;
import pl.nadwey.nadarenas.command.command.CommandArena;
import pl.nadwey.nadarenas.command.command.CommandArenaRestorer;
import pl.nadwey.nadarenas.command.command.CommandArenas;
import pl.nadwey.nadarenas.command.argument.ArenaRecordArgument;
import pl.nadwey.nadarenas.command.command.CommandReload;
import pl.nadwey.nadarenas.command.handler.NadArenasInvalidUsageHandler;
import pl.nadwey.nadarenas.command.handler.NadArenasMissingPermissionsHandler;

public class CommandHandler implements Reloadable {
    private final NadArenas plugin;
    private LiteCommands<CommandSender> liteCommands;

    public CommandHandler(NadArenas plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        liteCommands = LiteBukkitFactory.builder("nadarenas", plugin)
                .commands(
                        new CommandArenas(plugin),
                        new CommandArena(plugin),
                        new CommandArenaRestorer(plugin),
                        new CommandReload(plugin)
                )
                .argument(ArenaRecord.class, new ArenaRecordArgument(plugin))
                .argument(Material.class, new MaterialArgument())
                .invalidUsage(new NadArenasInvalidUsageHandler(plugin))
                .missingPermission(new NadArenasMissingPermissionsHandler(plugin))
                .build();
    }

    public void onDisable() {
        liteCommands.unregister();
    }

    @Override
    public void reload() {
        onDisable();
        onEnable();
    }
}
