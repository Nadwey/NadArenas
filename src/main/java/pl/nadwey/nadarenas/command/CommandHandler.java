package pl.nadwey.nadarenas.command;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;
import pl.nadwey.nadarenas.command.commands.CommandArena;
import pl.nadwey.nadarenas.command.commands.CommandArenas;
import net.kyori.adventure.text.Component;
import pl.nadwey.nadarenas.command.arguments.ArenaArgument;
import pl.nadwey.nadarenas.model.arena.Arena;

public class CommandHandler implements Reloadable {
    private final NadArenas plugin;
    private LiteCommands<CommandSender> liteCommands;

    public CommandHandler(NadArenas plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        this.liteCommands = LiteBukkitFactory.builder("nadarenas", plugin)
                .commands(
                        new CommandArenas(plugin),
                        new CommandArena(plugin)
                )
                .argument(Arena.class, new ArenaArgument(plugin))
                .build();
    }

    @Override
    public void onDisable() {
        this.liteCommands.unregister();
    }

    protected static Component getMessagePrefix() {
        return Component.text("[NadArenas] ").color(TextColor.color(0x2080ff));
    }

    public static Component infoMessage(Component message) {
        return getMessagePrefix().append(Component.text("INFO: ").color(NamedTextColor.AQUA)).append(message);
    }

    public static Component infoMessage(String message) {
        return infoMessage(Component.text(message));
    }

    public static Component warnMessage(Component message) {
        return getMessagePrefix().append(Component.text("WARN: ").color(NamedTextColor.YELLOW)).append(message);
    }

    public static Component warnMessage(String message) {
        return warnMessage(Component.text(message));
    }

    public static Component errorMessage(Component message) {
        return getMessagePrefix().append(Component.text("ERROR: ").color(NamedTextColor.RED)).append(message);
    }

    public static Component errorMessage(String message) {
        return errorMessage(Component.text(message));
    }

    public static Component normalMessage(Component message) {
        return getMessagePrefix().append(message);
    }

    public static Component normalMessage(String message) {
        return normalMessage(Component.text(message));
    }
}
