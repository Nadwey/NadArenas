package pl.nadwey.nadarenas.bukkit.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;

public class AdventureUtils {
    public static Component deserializeMiniMessage(String text) {
        if (text == null || text.isEmpty())
            return Component.empty();

        return MiniMessage.miniMessage().deserialize(text);
    }

    public static ComponentWrapper deserializeMiniMessageToWrapper(String text) {
        return new AdventureComponentWrapper(deserializeMiniMessage(text));
    }

    public static Component deserializeLegacy(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    public static ComponentWrapper deserializeLegacyToWrapper(String text) {
        return new AdventureComponentWrapper(deserializeLegacy(text));
    }

    public static String legacyAmpersandToMiniMessage(String legacyString) {
        return MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(legacyString));
    }

    public static String legacySectionToMiniMessage(String legacyString) {
        return MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacySection().deserialize(legacyString));
    }
}
