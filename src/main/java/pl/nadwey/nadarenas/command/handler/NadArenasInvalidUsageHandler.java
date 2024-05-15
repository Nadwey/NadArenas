package pl.nadwey.nadarenas.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.NadArenas;

import java.util.Map;

public class NadArenasInvalidUsageHandler implements InvalidUsageHandler<CommandSender> {
    private final NadArenas plugin;

    public NadArenasInvalidUsageHandler(NadArenas plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            plugin.getLangManager().send(sender, "invalid-command-usage-single-schematic", Map.of(
                    "schematic", schematic.first()
            ));

            return;
        }

        plugin.getLangManager().send(sender, "invalid-command-usage-multiple-schematics");
        for (String scheme : schematic.all()) {
            sender.sendMessage(
                    Component.text(" - ").color(NamedTextColor.DARK_GRAY)
                    .append(Component.text(scheme).color(NamedTextColor.GOLD))
            );
        }
    }
}
