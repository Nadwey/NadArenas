package pl.nadwey.nadarenas.gui.teleport;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.model.arena.ArenaRecordUtils;
import pl.nadwey.nadarenas.utility.AdventureUtils;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.Arrays;
import java.util.List;

public class TeleportItem extends AbstractItem {
    private final ArenaRecord arena;
    private final int players;

    public TeleportItem(ArenaRecord arena, int players) {
        this.arena = arena;
        this.players = players;
    }

    @Override
    public ItemProvider getItemProvider() {
        Material material = Material.matchMaterial(arena.getItem());

        ItemBuilder item = new ItemBuilder(material != null ? material : Material.BARRIER)
                .setDisplayName(AdventureUtils.deserializeLegacyToWrapper(ArenaRecordUtils.getPreferredName(arena)))
                .setAmount(Math.min(64, Math.max(1, players)));

        if (arena.getDescription() != null) {
            // TODO: too much javascript
            // Splits the description by <br> or <newline>, then converts each line to ComponentWrapper and makes it a list
            List<ComponentWrapper> lore = Arrays.stream(arena.getDescription().split("(<br>|<newline>)")).map(line -> (ComponentWrapper) new AdventureComponentWrapper(AdventureUtils.deserializeMiniMessage(line))).toList();

            item.setLore(lore);
        }

        return item;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.sendMessage("Teleporting to " + arena.getName() + "...");
    }
}
