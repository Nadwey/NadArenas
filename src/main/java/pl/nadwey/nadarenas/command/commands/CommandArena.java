package pl.nadwey.nadarenas.command.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.conversation.CreateArenaConversation;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.utility.AdventureUtils;

import java.sql.SQLException;
import java.util.List;

@Command(name = "nadarenas", aliases = { "na", "nda" })
@Permission("nadarenas.command.arena")
public class CommandArena extends CommandBase{
    public CommandArena(NadArenas plugin) {
        super(plugin);
    }

    @Execute
    public void arena(@Context Player sender) {
        sender.sendMessage(CommandHandler.infoMessage("nadawenas pwugin iws wowking uwu"));
    }

    @Execute(name = "arena create")
    @Permission("nadarenas.command.nadarenas.arena.create")
    public void arenaCreate(@Context Player sender) throws IncompleteRegionException {
        new CreateArenaConversation(sender).begin();
    }

    @Execute(name = "arena list")
    @Permission("nadarenas.command.nadarenas.arena.list")
    public void arenaList(@Context Player sender) {
        List<Arena> arenas = this.getPlugin().getArenaManager().getArenas();

        MiniMessage mm = MiniMessage.miniMessage();
        Component textComponent = CommandHandler.infoMessage("Arenas:\n");
        for (Arena arena : arenas) {
            textComponent = textComponent.append(Component.text(arena.getName()));

            if (arena.getDisplayName() != null) {
                textComponent = textComponent.append(Component.text(": ")).append(mm.deserialize(arena.getDisplayName()));
            }

            textComponent = textComponent.appendNewline();
        }
        sender.sendMessage(textComponent);
    }

    @Execute(name = "arena displayName")
    @Permission("nadarenas.command.nadarenas.arena.displayname")
    public void arenaSetDisplayName(@Context Player sender, @Arg("arena") Arena arena, @Join("displayName") String displayName) {
        this.getPlugin().getArenaManager().setArenaDisplayName(arena.getName(), displayName);

        sender.sendMessage(CommandHandler.infoMessage(
                Component.text("Set " + arena.getName() + "'s display name to ")
                        .append(AdventureUtils.quickDeserialize(displayName))));
    }

    @Execute(name = "arena remove")
    @Permission("nadarenas.command.nadarenas.arena.remove")
    public void arenaRemove(@Context Player sender, @Arg("arena") Arena arena) throws SQLException {
        this.getPlugin().getArenaManager().removeArena(arena.getName());

        sender.sendMessage(CommandHandler.warnMessage(Component.text("Removed " + arena.getName())));
    }

    @Execute(name = "arena description")
    @Permission("nadarenas.command.nadarenas.arena.description")
    public void arenaSetDescription(@Context Player sender, @Arg("arena") Arena arena, @Join("description") String description) throws SQLException {
        this.getPlugin().getArenaManager().setArenaDescription(arena.getName(), description);

        sender.sendMessage(CommandHandler.infoMessage(
                Component.text("Set " + arena.getName() + "'s description to ")
                        .append(AdventureUtils.quickDeserialize(description))));
    }

    @Execute(name = "arena item")
    @Permission("nadarenas.command.nadarenas.arena.item")
    public void arenaSetItem(@Context Player sender, @Arg("arena") Arena arena, @Arg("item") Material material) throws SQLException {
        this.getPlugin().getArenaManager().setArenaItem(arena.getName(), material);

        sender.sendMessage(CommandHandler.infoMessage("Set " + arena.getName() + "'s item to " + material));
    }

    @Execute(name = "arena load")
    @Permission("nadarenas.command.nadarenas.arena.load")
    public void arenaLoad(@Context Player sender, @Arg("arena") Arena arena) throws SQLException {
        this.getPlugin().getArenaLoader().load(arena, 1000);
    }
}
