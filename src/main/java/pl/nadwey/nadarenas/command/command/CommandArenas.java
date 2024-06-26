package pl.nadwey.nadarenas.command.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.gui.teleport.TeleportItem;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.window.Window;

import java.sql.SQLException;
import java.util.List;

@Command(name = "arenas")
@Permission("nadarenas.command.arenas")
public class CommandArenas extends CommandBase {
    public CommandArenas(NadArenas plugin) {
        super(plugin);
    }

    @Execute
    public void arenas(@Context Player sender) throws SQLException {
        Gui gui = Gui.normal()
                .setStructure(
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . ."
                )
                .build();

        gui.setBackground(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));

        List<ArenaRecord> arenas = getPlugin().getStorageManager().arena().getAllArenas();

        for (int i = 0; i < arenas.size(); i++) {
            ArenaRecord arena = arenas.get(i);

            gui.setItem(i, 1, new TeleportItem(arena, 1));
        }

        Window window = Window.single()
                .setViewer(sender)
                .setTitle(new AdventureComponentWrapper(Component.text("Arenas").color(TextColor.color(0x2080ff)).decorate(TextDecoration.BOLD)))
                .setGui(gui)
                .build();

        window.open();
    }
}
