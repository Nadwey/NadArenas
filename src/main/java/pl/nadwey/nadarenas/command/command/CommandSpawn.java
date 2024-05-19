package pl.nadwey.nadarenas.command.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.conversation.CreateArenaConversation;

@Command(name = "nadarenas spawn", aliases = {"nda spawn"})
@Permission("nadarenas.command.spawn")
public class CommandSpawn extends CommandBase {
    public CommandSpawn(NadArenas plugin) {
        super(plugin);
    }

    @Execute(name = "create")
    @Permission("nadarenas.command.nadarenas.spawn.create")
    public void arenaCreate(@Context Player sender) {
        new CreateArenaConversation(sender).begin();
    }
}
