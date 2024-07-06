package pl.nadwey.nadarenas.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.Arena;

import java.util.List;

public class ArenaRecordArgument extends ArgumentResolver<CommandSender, Arena> {
    private final NadArenas plugin;

    public ArenaRecordArgument(NadArenas plugin) {
        this.plugin = plugin;
    }

    @Override
    protected ParseResult<Arena> parse(Invocation<CommandSender> invocation, Argument<Arena> context, String argument) {
        if (!plugin.getStorageManager().arena().arenaExists(argument)) return ParseResult.failure("Arena " + argument + " does not exist");

        return ParseResult.success(this.plugin.getStorageManager().arena().getArenaByName(argument));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Arena> argument, SuggestionContext context) {
        List<Arena> arenas = plugin.getStorageManager().arena().getAllArenas();

        return SuggestionResult.of(arenas.stream().map(Arena::getName).toList());
    }
}