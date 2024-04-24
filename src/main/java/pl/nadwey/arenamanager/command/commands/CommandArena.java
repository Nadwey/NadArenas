package pl.nadwey.arenamanager.command.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.nadwey.arenamanager.ArenaManager;
import pl.nadwey.arenamanager.windows.teleport.TeleportItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Command(name = "arena")
@Permission("arenamanager.command.arena")
public class CommandArena {
    private final ArenaManager plugin;

    public CommandArena(ArenaManager plugin) {
        this.plugin = plugin;
    }

    @Execute
    public void arena(@Context Player sender) {
        sender.sendPlainMessage("ArenaManager");
    }

    @Execute(name = "create")
    @Permission("arenamanager.command.arena.create")
    public void create(@Context Player sender, @Arg("name") String name) throws SQLException {
        plugin.getDbArenaManager().createArena(name, sender.getWorld().getUID());
    }

    @Execute(name = "displayName")
    @Permission("arenamanager.command.arena.displayName")
    public void displayName(@Arg("name") String name, @Join("displayName") String displayName) throws SQLException {
        plugin.getDbArenaManager().setArenaDisplayName(name, displayName);
    }
}
