package pl.nadwey.nadarenas.common.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.standard.StandardSerdes;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import lombok.Getter;
import pl.nadwey.nadarenas.common.INadArenasPlugin;

public class ConfigManager {
    private final INadArenasPlugin plugin;

    @Getter
    private final GeneralConfig generalConfig;
    @Getter
    private final StorageConfig storageConfig;

    public ConfigManager(INadArenasPlugin plugin) {
        this.plugin = plugin;

        generalConfig = createConfig(GeneralConfig.class, "config.yml");
        storageConfig = createConfig(StorageConfig.class, "storage.yml");
    }

    private <T extends OkaeriConfig> T createConfig(Class<T> configClass, String filename) {
        return eu.okaeri.configs.ConfigManager.create(configClass, (it) -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer(), new StandardSerdes());
            it.withBindFile(plugin.getDataDir().resolve(filename));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    public void onDisable() {
        
    }
}
