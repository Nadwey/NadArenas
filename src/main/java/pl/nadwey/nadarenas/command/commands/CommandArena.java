package pl.nadwey.nadarenas.command.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.command.types.ArenaArgument;
import pl.nadwey.nadarenas.database.queries.ArenaManager;
import pl.nadwey.nadarenas.database.types.Arena;

import java.sql.SQLException;
import java.util.List;

@Command(name = "nadarenas", aliases = { "na", "nda" })
@Permission("nadarenas.command.arena")
public class CommandArena {
    private final NadArenas plugin;

    public CommandArena(NadArenas plugin) {
        this.plugin = plugin;
    }

    @Execute
    public void arena(@Context Player sender) {
        sender.sendPlainMessage("NadArenas");
    }

    @Execute(name = "arena create")
    @Permission("nadarenas.command.nadarenas.arena.create")
    public void arenaCreate(@Context Player sender, @Arg("name") String name) throws SQLException {
        if (ArenaManager.arenaExists(name)) {
            sender.sendMessage(CommandHandler.errorMessage("Arena " + name + " already exists"));
            return;
        }

        ArenaManager.createArena(name, sender.getWorld());
        sender.sendMessage(CommandHandler.infoMessage("Created " + name + " in world " + sender.getWorld().getName()));
    }

    @Execute(name = "arena list")
    @Permission("nadarenas.command.nadarenas.arena.list")
    public void arenaList(@Context Player sender) throws SQLException {
        List<Arena> arenas = ArenaManager.listArenas();

        MiniMessage mm = MiniMessage.miniMessage();
        Component textComponent = CommandHandler.infoMessage("Arenas:\n");
        for (Arena arena : arenas) {
            textComponent = textComponent.append(Component.text(arena.name()));

            if (arena.displayName() != null) {
                textComponent = textComponent.append(Component.text(": ")).append(mm.deserialize(arena.displayName()));
            }

            textComponent = textComponent.appendNewline();
        }
        sender.sendMessage(textComponent);
    }

    @Execute(name = "arena displayName")
    @Permission("nadarenas.command.nadarenas.arena.displayname")
    public void arenaSetDisplayName(@Context Player sender, @Arg("arena") Arena arena, @Join("displayName") String displayName) throws SQLException {
        ArenaManager.setArenaDisplayName(arena.name(), displayName);

        MiniMessage mm = MiniMessage.miniMessage();
        sender.sendMessage(CommandHandler.infoMessage(Component.text("Set " + arena.name() + "'s display name to ").append(mm.deserialize(displayName))));
    }

    @Execute(name = "arena remove")
    @Permission("nadarenas.command.nadarenas.arena.remove")
    public void arenaRemove(@Context Player sender, @Arg("arena") Arena arena) throws SQLException {
        ArenaManager.removeArena(arena.name());

        sender.sendMessage(CommandHandler.warnMessage(Component.text("Removed " + arena.name())));
    }

    @Execute(name = "arena description")
    @Permission("nadarenas.command.nadarenas.arena.description")
    public void arenaSetDescription(@Context Player sender, @Arg("arena") Arena arena, @Join("description") String description) throws SQLException {
        ArenaManager.setArenaDescription(arena.name(), description);

        MiniMessage mm = MiniMessage.miniMessage();
        sender.sendMessage(CommandHandler.infoMessage(Component.text("Set " + arena.name() + "'s description to ").append(mm.deserialize(description))));
    }

    @Execute(name = "arena item")
    @Permission("nadarenas.command.nadarenas.arena.item")
    public void arenaSetItem(@Context Player sender, @Arg("arena") Arena arena, @Join("item") String item) throws SQLException {
        Material material = Material.getMaterial(item);
        if (material == null) {
            sender.sendMessage(CommandHandler.errorMessage("Item " + item + " is not a valid material"));
            return;
        }

        ArenaManager.setArenaItem(arena.name(), material);

        sender.sendMessage(CommandHandler.infoMessage("Set " + arena.name() + "'s item to " + item));
    }
//
//    @Execute(name = "create spawn")
//    @Permission("nadarenas.command.arena.createspawn")
//    public void createArenaSpawn(@Context Player sender, @Arg("arenaName") String arenaName) throws SQLException {
//        ArenaManager arenaManager = plugin.getDataManager().getArenaManager();
//        SpawnManager spawnManager = plugin.getDataManager().getSpawnManager();
//
//        if (!arenaManager.arenaExists(arenaName)) {
//            sender.sendMessage(CommandHandler.errorMessage("Arena " + arenaName + " doesn't exist"));
//            return;
//        }
//
//        Spawn spawn = new Spawn(arenaName, sender.getLocation());
//        Integer id = spawnManager.createArenaSpawn(spawn);
//
//        sender.sendMessage(CommandHandler.infoMessage(
//                Component.text("Created a spawn for arena ")
//                        .append(Component.text(arenaName))
//                        .append(Component.text(" with ID: "))
//                        .append(Component.text(id.toString()
//                                )
//                        )
//        ));
//
//    }
}
