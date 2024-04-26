package pl.nadwey.nadarenas.gui.teleport;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.database.types.Arena;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.Arrays;

public class TeleportItem extends AbstractItem {
    private final Arena arena;
    private final int players;

    public TeleportItem(Arena arena, int players) {
        this.arena = arena;
        this.players = players;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(arena.item() != null ? arena.item() : Material.BARRIER)
                .setDisplayName(new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(arena.displayName())))
                .setLore(Arrays.stream(arena.description().split("\n")).map(line -> (ComponentWrapper) new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize(line))).toList())
                .setAmount(Math.min(64, Math.max(1, players)));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.sendMessage(CommandHandler.normalMessage("Teleporting to " + arena.name() + "..."));
    }
}
