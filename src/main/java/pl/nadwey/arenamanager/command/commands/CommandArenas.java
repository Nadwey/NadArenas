package pl.nadwey.arenamanager.command.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.nadwey.arenamanager.windows.teleport.TeleportItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

@Command(name = "arenas")
@Permission("arenamanager.command.arenas")
public class CommandArenas {
    @Execute
    public void arenas(@Context Player sender) {
        Gui gui = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . ."
                )
                .build();

        gui.setBackground(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        gui.setItem(4, 1, new TeleportItem(Material.OAK_SAPLING, "&lLas 1", 1));

        Window window = Window.single()
                .setViewer(sender)
                .setTitle("&c&lArenas")
                .setGui(gui)
                .build();

        window.open();
    }
}
