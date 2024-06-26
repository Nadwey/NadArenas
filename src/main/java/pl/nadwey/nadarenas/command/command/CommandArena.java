package pl.nadwey.nadarenas.command.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.conversation.CreateArenaConversation;
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
        var arenas = getPlugin().getStorageManager().arena().getAllArenas().iterator();

        Component textComponent = getPlugin().getLangManager().getAsComponent("command-arena-list-top").appendNewline();

        while (arenas.hasNext()) {
            ArenaRecord arena = arenas.next();

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
    public void arenaSetDisplayName(@Context CommandSender sender, @Arg("arena") ArenaRecord arena, @Join("displayName") String displayName) {
        getPlugin().getStorageManager().arena().setArenaDisplayName(arena.getId(), displayName);

        getPlugin().getLangManager().send(sender, "command-arena-setdisplayname-successful", Map.of(
                "arena", arena.getName(),
                "displayName", AdventureUtils.legacyAmpersandToMiniMessage(displayName)
        ));
    }

    @Execute(name = "remove")
    @Permission("nadarenas.command.nadarenas.arena.remove")
    public void arenaRemove(@Context CommandSender sender, @Arg("arena") ArenaRecord arena) throws SQLException {
        getPlugin().getStorageManager().arena().removeArena(arena.getId());
        getPlugin().getArenaRestorer().removeArena(arena);

        getPlugin().getLangManager().send(sender, "command-arena-remove-successful", Map.of(
                "arena", arena.getName()
        ));
    }

    @Execute(name = "setDescription")
    @Permission("nadarenas.command.nadarenas.arena.setdescription")
    public void arenaSetDescription(@Context CommandSender sender, @Arg("arena") ArenaRecord arena, @Join("description") String description) throws SQLException {
        getPlugin().getStorageManager().arena().setArenaDescription(arena.getId(), description);

        getPlugin().getLangManager().send(sender, "command-arena-setdescription-successful", Map.of(
                "arena", arena.getName(),
                "description", description
        ));
    }

    @Execute(name = "setItem")
    @Permission("nadarenas.command.nadarenas.arena.setitem")
    public void arenaSetItem(@Context CommandSender sender, @Arg("arena") ArenaRecord arena, @Arg("item") Material material) throws SQLException {
        getPlugin().getStorageManager().arena().setArenaItem(arena.getId(), material);

        getPlugin().getLangManager().send(sender,"command-arena-setitem-successful", Map.of(
                "arena", arena.getName(),
                "item", material.toString()
        ));
    }
}
