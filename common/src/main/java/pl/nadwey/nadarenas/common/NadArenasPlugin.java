package pl.nadwey.nadarenas.common;

import lombok.Getter;
import pl.nadwey.nadarenas.common.api.ApiRegistrationUtil;
import pl.nadwey.nadarenas.common.api.NadArenasApiProvider;
import pl.nadwey.nadarenas.common.config.ConfigManager;
import pl.nadwey.nadarenas.common.lang.LangManager;
import pl.nadwey.nadarenas.common.storage.Storage;
import pl.nadwey.nadarenas.common.storage.StorageFactory;

public abstract class NadArenasPlugin implements INadArenasPlugin {
    @Getter
    private LangManager langManager;
    @Getter
    private ConfigManager configManager;
    @Getter
    private Storage storage;
    @Getter
    private NadArenasApiProvider apiProvider;

    protected final void enable() {
        getDataDir().toFile().mkdirs();
        getDataDir().resolve("arenas").toFile().mkdir();
        getDataDir().resolve("lang").toFile().mkdir();

        configManager = new ConfigManager(this);
        langManager = new LangManager(this);
        storage = new StorageFactory(this).getInstance();

        apiProvider = new NadArenasApiProvider(this);
        ApiRegistrationUtil.registerProvider(apiProvider);
    }

    protected final void disable() {
        storage.shutdown();
        configManager.onDisable();
    }
}
