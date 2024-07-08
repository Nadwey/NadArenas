package pl.nadwey.nadarenas.bukkit.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.api.model.arena.Arena;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;

import java.sql.SQLException;
import java.util.List;

public class ArenaRecordArgument extends ArgumentResolver<CommandSender, Arena> {
    private final BukkitNadArenasPlugin plugin;

    public ArenaRecordArgument(BukkitNadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected ParseResult<Arena> parse(Invocation<CommandSender> invocation, Argument<Arena> context, String argument) {
        try {
            if (!plugin.getStorageManager().getImplementation().arenaExists(argument)) return ParseResult.failure("Arena " + argument + " does not exist");

            return ParseResult.success(this.plugin.getStorageManager().getImplementation().getArenaByName(argument));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Arena> argument, SuggestionContext context) {
        try {
            List<Arena> arenas = plugin.getStorageManager().getImplementation().getAllArenas();

            return SuggestionResult.of(arenas.stream().map(Arena::getName).toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}