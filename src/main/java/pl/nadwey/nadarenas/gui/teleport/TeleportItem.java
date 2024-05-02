package pl.nadwey.nadarenas.gui.teleport;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.utility.AdventureUtils;
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
        ItemBuilder item = new ItemBuilder(arena.getItem() != null ? arena.getItem() : Material.BARRIER)
                .setDisplayName(AdventureUtils.quickDeserializeToWrapper(arena.getPreferredName()))
                .setAmount(Math.min(64, Math.max(1, players)));

        if (arena.getDescription() != null) {
            // Converts description (string) to a List of component wrappers
            var lore = Arrays.stream(arena.getDescription().split("\n"))
                    .map(AdventureUtils::quickDeserializeToWrapper).toList();

            item.setLore(lore);
        }

        return item;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.sendMessage(CommandHandler.normalMessage("Teleporting to " + arena.getName() + "..."));
    }
}
