package pl.nadwey.nadarenas.common.plugin;

import lombok.Getter;
import pl.nadwey.nadarenas.common.storage.StorageManager;

public abstract class AbstractNadArenasPlugin implements NadArenasPlugin {
    @Getter
    private StorageManager storageManager;

    public final void onLoad() {
        load();
    }

    public final void onEnable() {
        storageManager = new StorageManager(this);
        storageManager.onEnable();

        enable();
    }

    public final void onDisable() {
        disable();

        storageManager.onDisable();
    }

    public final void reload() {
        onDisable();

        storageManager.onDisable();

        storageManager = new StorageManager(this);
        storageManager.onEnable();

        onEnable();
    }

    protected abstract void load();

    protected abstract void enable();

    protected abstract void disable();
}
