package pl.nadwey.nadarenas.bukkit.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import pl.nadwey.nadarenas.bukkit.BukkitNadArenasPlugin;

public class NadArenasInvalidUsageHandler implements InvalidUsageHandler<CommandSender> {
    private final BukkitNadArenasPlugin plugin;

    public NadArenasInvalidUsageHandler(BukkitNadArenasPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            sender.sendMessage(schematic.first());

            return;
        }

        for (String scheme : schematic.all()) {
            sender.sendMessage(
                    Component.text(" - ").color(NamedTextColor.DARK_GRAY)
                    .append(Component.text(scheme).color(NamedTextColor.GOLD))
            );
        }
    }
}
