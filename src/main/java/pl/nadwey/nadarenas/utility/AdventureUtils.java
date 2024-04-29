package pl.nadwey.nadarenas.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.inventoryaccess.component.ComponentWrapper;

public class AdventureUtils {
    public static Component quickDeserialize(String text) {
        if (text == null)
            return Component.empty();

        return MiniMessage.miniMessage().deserialize(text);
    }

    public static ComponentWrapper quickDeserializeToWrapper(String text) {
        return new AdventureComponentWrapper(quickDeserialize(text));
    }
}
