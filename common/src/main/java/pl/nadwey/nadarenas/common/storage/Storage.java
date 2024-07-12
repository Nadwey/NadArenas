package pl.nadwey.nadarenas.common.storage;

import lombok.Getter;
import pl.nadwey.nadarenas.common.INadArenasPlugin;
import pl.nadwey.nadarenas.common.lang.LangMessage;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;

public class Storage {
    private final INadArenasPlugin plugin;
    @Getter
    private final StorageImplementation implementation;

    public Storage(INadArenasPlugin plugin, StorageImplementation implementation) {
        this.plugin = plugin;
        this.implementation = implementation;
    }

    public void init() {
        plugin.getLogger().info(plugin.getLangManager().getMessage(LangMessage.STORAGE_ENABLING));
        implementation.init();
    }

    public void shutdown() {
        plugin.getLogger().info(plugin.getLangManager().getMessage(LangMessage.STORAGE_DISABLING));
        implementation.shutdown();
    }
}
