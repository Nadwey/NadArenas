package pl.nadwey.nadarenas.command.command;

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
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.conversation.CreateArenaConversation;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.utility.AdventureUtils;

import java.sql.SQLException;
import java.util.Map;

@Command(name = "nadarenas arena", aliases = {"nda arena"})
@Permission("nadarenas.command.arena")
public class CommandArena extends CommandBase {
    public CommandArena(NadArenas plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context CommandSender sender) {
        sender.sendMessage("nadawenas pwugin iws wowking uwu");
    }

    @Execute(name = "create")
    @Permission("nadarenas.command.nadarenas.arena.create")
    public void arenaCreate(@Context Player sender) {
        new CreateArenaConversation(sender).begin();
    }

    @Execute(name = "list")
    @Permission("nadarenas.command.nadarenas.arena.list")
    public void arenaList(@Context CommandSender sender) {
        var arenas = this.getPlugin().getStorageManager().arena().getArenas().iterator();

        Component textComponent = this.getPlugin().getLangManager().getAsComponent("command-arena-list-top").appendNewline();

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
    public void arenaSetDisplayName(@Context CommandSender sender, @Arg("arena") Arena arena, @Join("displayName") String displayName) {
        this.getPlugin().getStorageManager().arena().setArenaDisplayName(arena.getName(), displayName);

        this.getPlugin().getLangManager().send(sender, "command-arena-setdisplayname-successful", Map.of(
                "arena", arena.getName(),
                "displayName", AdventureUtils.legacyAmpersandToMiniMessage(displayName)
        ));
    }

    @Execute(name = "remove")
    @Permission("nadarenas.command.nadarenas.arena.remove")
    public void arenaRemove(@Context CommandSender sender, @Arg("arena") Arena arena) throws SQLException {
        this.getPlugin().getStorageManager().arena().removeArena(arena.getName());
        this.getPlugin().getArenaManager().removeArena(arena);

        this.getPlugin().getLangManager().send(sender, "command-arena-remove-successful", Map.of(
                "arena", arena.getName()
        ));
    }

    @Execute(name = "setDescription")
    @Permission("nadarenas.command.nadarenas.arena.setdescription")
    public void arenaSetDescription(@Context CommandSender sender, @Arg("arena") Arena arena, @Join("description") String description) throws SQLException {
        this.getPlugin().getStorageManager().arena().setArenaDescription(arena.getName(), description);

        this.getPlugin().getLangManager().send(sender, "command-arena-setdescription-successful", Map.of(
                "arena", arena.getName(),
                "description", description
        ));
    }

    @Execute(name = "setItem")
    @Permission("nadarenas.command.nadarenas.arena.setitem")
    public void arenaSetItem(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("item") Material material) throws SQLException {
        this.getPlugin().getStorageManager().arena().setArenaItem(arena.getName(), material);

        this.getPlugin().getLangManager().send(sender,"command-arena-setitem-successful", Map.of(
                "arena", arena.getName(),
                "item", material.toString()
        ));
    }
}
