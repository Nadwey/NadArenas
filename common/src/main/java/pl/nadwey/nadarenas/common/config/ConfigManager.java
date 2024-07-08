package pl.nadwey.nadarenas.common.config;

import eu.okaeri.configs.serdes.standard.StandardSerdes;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import lombok.Getter;
import pl.nadwey.nadarenas.common.INadArenasPlugin;

public class ConfigManager {
    @Getter
    private final StorageConfig storageConfig;

    public ConfigManager(INadArenasPlugin plugin) {
        storageConfig = eu.okaeri.configs.ConfigManager.create(StorageConfig.class, (it) -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer(), new StandardSerdes());
            it.withBindFile(plugin.getDataDir().resolve("storage.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    public void onDisable() {
        
    }
}
