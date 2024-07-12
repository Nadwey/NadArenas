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

import java.util.List;

public class ArenaRecordArgument extends ArgumentResolver<CommandSender, Arena> {
    private final BukkitNadArenasPlugin plugin;

    public ArenaRecordArgument(BukkitNadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected ParseResult<Arena> parse(Invocation<CommandSender> invocation, Argument<Arena> context, String argument) {
        if (!plugin.getApiProvider().getArenaManager().arenaExists(argument)) return ParseResult.failure("Arena " + argument + " does not exist");

        return ParseResult.success(this.plugin.getApiProvider().getArenaManager().getArena(argument));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Arena> argument, SuggestionContext context) {
        // TODO: Cache this
        List<Arena> arenas = plugin.getApiProvider().getArenaManager().getArenas();

        return SuggestionResult.of(arenas.stream().map(Arena::getName).toList());
    }
}