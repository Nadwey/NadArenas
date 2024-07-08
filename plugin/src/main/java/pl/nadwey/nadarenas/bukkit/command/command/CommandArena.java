package pl.nadwey.nadarenas.bukkit.command.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.nadwey.nadarenas.api.model.arena.Arena;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;
import pl.nadwey.nadarenas.bukkit.utility.AdventureUtils;

import java.sql.SQLException;

@Command(name = "nadarenas arena", aliases = {"nda arena"})
@Permission("nadarenas.command.arena")
public class CommandArena extends CommandBase {
    public CommandArena(BukkitNadArenasPlugin plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context CommandSender sender) {
        sender.sendMessage("nadawenas pwugin iws wowking uwu");
    }

    @Execute(name = "create")
    @Permission("nadarenas.command.nadarenas.arena.create")
    public void arenaCreate(@Context Player sender) {

    }

    @Execute(name = "list")
    @Permission("nadarenas.command.nadarenas.arena.list")
    public void arenaList(@Context CommandSender sender) throws SQLException {
        var arenas = getPlugin().getStorage().getImplementation().getAllArenas().iterator();

        Component textComponent = Component.text("arenas:").appendNewline();

        while (arenas.hasNext()) {
            Arena arena = arenas.next();

            textComponent = textComponent.append(Component.text(arena.getName()));

            if (arena.getDisplayName() != null) {
                textComponent = textComponent.append(Component.text(": ")).append(AdventureUtils.deserializeLegacy(arena.getDisplayName()));
            }

            if (arenas.hasNext()) textComponent = textComponent.appendNewline();
        }

        sender.sendMessage(textComponent);
    }

    @Execute(name = "setDisplayName")
    @Permission("nadarenas.command.nadarenas.arena.setdisplayname")
    public void arenaSetDisplayName(@Context CommandSender sender, @Arg("arena") Arena arena, @Join("displayName") String displayName) throws SQLException {
        getPlugin().getStorage().getImplementation().setArenaDisplayName(arena.getId(), displayName);

        sender.sendMessage("set arena's display name to " + displayName);
    }

    @Execute(name = "remove")
    @Permission("nadarenas.command.nadarenas.arena.remove")
    public void arenaRemove(@Context CommandSender sender, @Arg("arena") Arena arena) throws SQLException {
        getPlugin().getStorage().getImplementation().removeArena(arena.getId());
        getPlugin().getArenaRestorer().removeArena(arena.getName());

        sender.sendMessage("successfully removed");
    }

    @Execute(name = "setDescription")
    @Permission("nadarenas.command.nadarenas.arena.setdescription")
    public void arenaSetDescription(@Context CommandSender sender, @Arg("arena") Arena arena, @Join("description") String description) throws SQLException {
        getPlugin().getStorage().getImplementation().setArenaDescription(arena.getId(), description);

        sender.sendMessage("set arena's description to " + description);
    }

    @Execute(name = "setItem")
    @Permission("nadarenas.command.nadarenas.arena.setitem")
    public void arenaSetItem(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("item") Material material) throws SQLException {
        getPlugin().getStorage().getImplementation().setArenaItem(arena.getId(), material.toString());

        sender.sendMessage("set arena's item to " + material.toString());
    }
}
