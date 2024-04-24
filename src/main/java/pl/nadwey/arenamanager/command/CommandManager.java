package pl.nadwey.arenamanager.command;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import org.bukkit.command.CommandSender;
import pl.nadwey.arenamanager.ArenaManager;
import pl.nadwey.arenamanager.command.commands.CommandArena;
import pl.nadwey.arenamanager.command.commands.CommandArenas;

public class CommandManager {
    private final ArenaManager plugin;
    private LiteCommands<CommandSender> liteCommands;

    public CommandManager(ArenaManager plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        this.liteCommands = LiteBukkitFactory.builder("my-plugin", plugin)
                .commands(
                        new CommandArenas(),
                        new CommandArena(plugin)
                )
                .build();
    }

    public void disable() {
        this.liteCommands.unregister();
    }
}
