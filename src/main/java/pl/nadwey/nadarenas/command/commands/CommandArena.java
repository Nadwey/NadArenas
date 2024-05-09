package pl.nadwey.nadarenas.command.commands;

import com.sk89q.worldedit.IncompleteRegionException;
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
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.conversation.CreateArenaConversation;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.utility.AdventureUtils;

import java.sql.SQLException;
import java.util.Map;

@Command(name = "nadarenas arena", aliases = { "nda arena" })
@Permission("nadarenas.command.arena")
public class CommandArena extends CommandBase{
    public CommandArena(NadArenas plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context CommandSender sender) {
        sender.sendMessage(CommandHandler.infoMessage("nadawenas pwugin iws wowking uwu"));
    }

    @Execute(name = "create")
    @Permission("nadarenas.command.nadarenas.arena.create")
    public void arenaCreate(@Context Player sender) {
        new CreateArenaConversation(sender).begin();
    }

    @Execute(name = "list")
    @Permission("nadarenas.command.nadarenas.arena.list")
    public void arenaList(@Context CommandSender sender) {
        var arenas = this.getPlugin().getArenaManager().getArenas().iterator();

        Component textComponent = CommandHandler.infoMessage("Arenas:\n");

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

    @Execute(name = "displayName")
    @Permission("nadarenas.command.nadarenas.arena.displayname")
    public void arenaSetDisplayName(@Context CommandSender sender, @Arg("arena") Arena arena, @Join("displayName") String displayName) {
        this.getPlugin().getArenaManager().setArenaDisplayName(arena.getName(), displayName);

        sender.sendMessage(CommandHandler.infoMessage(
                Component.text("Set " + arena.getName() + "'s display name to ")
                        .append(AdventureUtils.deserializeLegacy(displayName))));
    }

    @Execute(name = "remove")
    @Permission("nadarenas.command.nadarenas.arena.remove")
    public void arenaRemove(@Context CommandSender sender, @Arg("arena") Arena arena) throws SQLException {
        this.getPlugin().getArenaManager().removeArena(arena.getName());

        sender.sendMessage(CommandHandler.warnMessage(Component.text("Removed " + arena.getName())));
    }

    @Execute(name = "description")
    @Permission("nadarenas.command.nadarenas.arena.description")
    public void arenaSetDescription(@Context CommandSender sender, @Arg("arena") Arena arena, @Join("description") String description) throws SQLException {
        this.getPlugin().getArenaManager().setArenaDescription(arena.getName(), description);

        sender.sendMessage(CommandHandler.infoMessage(
                Component.text("Set " + arena.getName() + "'s description to:")
                        .appendNewline()
                        .append(AdventureUtils.deserializeMiniMessage(description))));
    }

    @Execute(name = "item")
    @Permission("nadarenas.command.nadarenas.arena.item")
    public void arenaSetItem(@Context CommandSender sender, @Arg("arena") Arena arena, @Arg("item") Material material) throws SQLException {
        this.getPlugin().getArenaManager().setArenaItem(arena.getName(), material);

        sender.sendMessage(CommandHandler.infoMessage("Set " + arena.getName() + "'s item to " + material));
    }

    @Execute(name = "load")
    @Permission("nadarenas.command.nadarenas.arena.load")
    public void arenaLoad(@Context CommandSender sender, @Arg("arena") Arena arena) {
        if (this.getPlugin().getArenaLoader().isLoading(arena.getName())) {
            sender.sendMessage(CommandHandler.errorMessage(
                    this.getPlugin().getLangManager().getAsComponent("command-arena-load-already-loading", Map.of(
                            "arena", arena.getName()
                    ))
            ));
            return;
        }

        this.getPlugin().getArenaLoader().load(arena, 250);
    }
}
