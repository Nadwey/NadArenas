package pl.nadwey.arenamanager.windows.teleport;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class TeleportItem extends AbstractItem {
    private final Material item;
    private final String title;
    private final int players;

    public TeleportItem(Material item, String title, int players) {
        this.item = item;
        this.title = title;
        this.players = players;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(item).setDisplayName(title).setAmount(Math.min(64, Math.max(1, players)));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.sendPlainMessage("fuecmwef");
    }
}
