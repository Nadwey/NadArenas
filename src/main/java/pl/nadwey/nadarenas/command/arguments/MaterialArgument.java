package pl.nadwey.nadarenas.command.arguments;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.model.arena.Arena;

import java.util.Arrays;
import java.util.List;

public class MaterialArgument extends ArgumentResolver<CommandSender, Material> {
    @Override
    protected ParseResult<Material> parse(Invocation<CommandSender> invocation, Argument<Material> context, String argument) {
        Material material = Material.matchMaterial(argument);
        if (material == null) return ParseResult.failure("Invalid material: " + argument);

        return ParseResult.success(material);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Material> argument, SuggestionContext context) {
        List<String> list = Arrays.stream(Material.values()).map(Enum::toString).toList();

        return SuggestionResult.of(list);
    }
}