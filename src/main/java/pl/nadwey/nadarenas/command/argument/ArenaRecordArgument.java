package pl.nadwey.nadarenas.command.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.NadArenas;

import java.util.List;

public class ArenaRecordArgument extends ArgumentResolver<CommandSender, ArenaRecord> {
    private final NadArenas plugin;

    public ArenaRecordArgument(NadArenas plugin) {
        this.plugin = plugin;
    }

    @Override
    protected ParseResult<ArenaRecord> parse(Invocation<CommandSender> invocation, Argument<ArenaRecord> context, String argument) {
        if (!plugin.getStorageManager().arena().arenaExists(argument)) return ParseResult.failure("Arena " + argument + " does not exist");

        return ParseResult.success(this.plugin.getStorageManager().arena().getArenaByName(argument));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<ArenaRecord> argument, SuggestionContext context) {
        List<ArenaRecord> arenas = plugin.getStorageManager().arena().getAllArenas();

        return SuggestionResult.of(arenas.stream().map(ArenaRecord::getName).toList());
    }
}