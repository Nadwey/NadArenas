package pl.nadwey.nadarenas.lang;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LangManager implements Reloadable {
    private final NadArenas plugin;
    private final HashMap<String, FileConfiguration> langs = new HashMap<>();
    private final Locale locale = Locale.ENGLISH;

    public LangManager(NadArenas plugin) {
        this.plugin = plugin;
        loadLanguages();
    }

    public void loadLanguages() {
        this.plugin.getLogger().info("Enabling Language Manager");

        langs.clear();

        JarFile file = null;
        try {
            file = new JarFile(this.plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        var entries = file.entries();

        while (entries.hasMoreElements()) {
            JarEntry e = entries.nextElement();
            if (!e.getName().startsWith("lang/") || e.isDirectory()) continue;

            InputStream langStream = this.plugin.getResource(e.getName());
            if (langStream == null) continue;
            FileConfiguration langStreamConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(langStream));

            File langFile = new File(this.plugin.getDataFolder(), e.getName());
            if (!langFile.exists()) this.plugin.saveResource(e.getName(), false);

            FileConfiguration langFileConfig = YamlConfiguration.loadConfiguration(langFile);

            if (!langFileConfig.getKeys(true).equals(langStreamConfig.getKeys(true))) {
                this.plugin.getLogger().warning("Updating language file " + langFile.getName());
                langFileConfig.setDefaults(langStreamConfig);
                langFileConfig.options().copyDefaults(true);
            }

            try {
                langFileConfig.save(langFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        var langFiles = this.plugin.getDataFolder().toPath().resolve("lang").toFile().listFiles();

        if (langFiles == null) {
            this.plugin.getLogger().severe("Failed to load language files from plugin directory");
            return;
        }

        for (File langFile : langFiles) {
            langs.put(langFile.getName().replace("lang/", "").replace(".yml", ""), YamlConfiguration.loadConfiguration(langFile));
        }
    }

    @Override
    public void reload() {
        loadLanguages();
    }

    /**
     * Returns the language string as an adventure component
     *
     * @param key key of the language string
     * @return the adventure component
     */
    public Component getAsComponent(String key) {
        return MiniMessage.miniMessage().deserialize(getString(key));
    }

    /**
     * Returns the language string as an adventure component
     *
     * @param key key of the language string
     * @param args strings to replace
     * @return the adventure component
     */
    public Component getAsComponent(String key, Map<String, String> args) {
        return MiniMessage.miniMessage().deserialize(getString(key, args));
    }

    /**
     * Returns the language string as a legacy bukkit string
     * e.g. <gray>test</gray> -> §7test
     *
     * @param key key of the language string
     * @return the legacy bukkit string
     */
    public String getAsLegacyString(String key) {
        return LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize(getString(key)));
    }

    /**
     * Returns the language string as a legacy bukkit string
     * e.g. <gray>test</gray> -> §7test
     *
     * @param key key of the language string
     * @param args strings to replace
     * @return the legacy bukkit string
     */
    public String getAsLegacyString(String key, Map<String, String> args) {
        return LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize(getString(key, args)));
    }

    public String getString(String key) {
        return getStringByLocale(locale, key);
    }

    public String getString(String key, Map<String, String> args) {
        String string = getString(key);

        StringSubstitutor sub = new StringSubstitutor(args);
        return sub.replace(string);
    }

    private String getStringByLocale(Locale locale, String key) {
        return this.langs.get(locale.toLanguageTag()).getString(key);
    }

    public void send(CommandSender commandSender, String key) {
        commandSender.sendMessage(getMessagePrefix().append(getAsComponent(key)));
    }

    public void send(CommandSender commandSender, String key, Map<String, String> args) {
        commandSender.sendMessage(getMessagePrefix().append(getAsComponent(key, args)));
    }

    private static Component getMessagePrefix() {
        return Component.text("[NadArenas] ").color(TextColor.color(0x2080ff));
    }
}
