package pl.nadwey.nadarenas.command.types;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.database.queries.ArenaManager;
import pl.nadwey.nadarenas.database.types.Arena;

import java.sql.SQLException;
import java.util.List;

public class ArenaArgument extends ArgumentResolver<CommandSender, Arena> {
    @Override
    protected ParseResult<Arena> parse(Invocation<CommandSender> invocation, Argument<Arena> context, String argument) {
        try {
            if (!ArenaManager.arenaExists(argument)) return ParseResult.failure("Arena " + argument + " does not exist");

            return ParseResult.success(ArenaManager.getArena(argument));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Arena> argument, SuggestionContext context) {
        try {
            List<Arena> arenas = ArenaManager.listArenas();
            return SuggestionResult.of(arenas.stream().map(Arena::name).toList());
        } catch (SQLException e) {
            return SuggestionResult.empty();
        }
    }
}