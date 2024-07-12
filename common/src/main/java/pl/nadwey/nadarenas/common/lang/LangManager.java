package pl.nadwey.nadarenas.common.lang;

import lombok.Getter;
import org.apache.commons.text.StringSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import pl.nadwey.nadarenas.common.INadArenasPlugin;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class is a dumpster fire
 */
public class LangManager {
    private final INadArenasPlugin plugin;

    @Getter
    private @Nullable Map<String, String> messages;

    public LangManager(INadArenasPlugin plugin) {
        this.plugin = plugin;

        messages = null;

        updateLangFiles();
        loadLanguage(plugin.getConfigManager().getGeneralConfig().getLanguageFile());
    }

    public @NotNull String getMessage(LangMessage key) {
        if (messages == null)
            return key.getKey();

        String message = messages.get(key.getKey());
        if (message == null)
            return key.getKey();

        return messages.get(key.getKey());
    }

    public @NotNull String getMessage(LangMessage key, Map<String, String> placeholders) {
        return StringSubstitutor.replace(getMessage(key), placeholders);
    }

    private Path getLangDir() {
        return plugin.getDataDir().resolve("lang");
    }

    private void loadLanguage(String languageFilePath) {
        Path languageFile = getLangDir().resolve(languageFilePath);
        try {
            messages = loadLanguageFile(languageFile.toFile());

            if (messages == null) {
                plugin.getLogger().warning("LangManager: Language file " + languageFilePath + " is null for some reason.");
                plugin.getLogger().warning("LangManager: You may see keys instead of actual the text.");
            }
        } catch (LangManagerException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }

    private Map<String, String> loadLanguageFile(File file) throws LangManagerException {
        try (InputStream inputStream = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            Object object = yaml.load(inputStream);

            if (object == null)
                return null;

            if (!(object instanceof Map)) {
                throw new LangManagerException("LangManager: Language file " + file + " is not a valid map!");
            }

            Map<String, String> result = new HashMap<>();

            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) object).entrySet()) {
                if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
                    throw new LangManagerException("LangManager: Invalid key-value pair in language file:" + file.getName() + ", key: "
                            + entry.getKey() + ", value: " + entry.getValue());
                }
                result.put((String) entry.getKey(), (String) entry.getValue());
            }

            return result;
        } catch (FileNotFoundException e) {
            throw new LangManagerException("LangManager: Language file " + file + " doesn't exist!");
        } catch (IOException e) {
            throw new LangManagerException("LangManager: Failed to load language file " + file);
        } catch (YAMLException e) {
            throw new LangManagerException("LangManager: Failed to parse language file " + file);
        }
    }

    private void updateLangFiles() {
        File jarPath = getJarPath();

        if (jarPath == null) {
            plugin.getLogger().severe("LangManager: Cannot find JAR path");
            return;
        }

        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith("lang/") && !entry.isDirectory()) {
                    processJarEntry(jarFile, entry);
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("LangManager: Cannot process language files");

            e.printStackTrace();
        }
    }

    private void processJarEntry(JarFile jarFile, JarEntry entry) throws IOException {
        String fileName = Paths.get(entry.getName()).getFileName().toString();
        Path dataDirFilePath = getLangDir().resolve(fileName);
        File dataDirFile = dataDirFilePath.toFile();
        plugin.getLogger().info("LangManager: Loading " + fileName);

        if (!dataDirFile.exists()) {
            try (InputStream in = jarFile.getInputStream(entry)) {
                Files.copy(in, dataDirFilePath);
            }
        } else {
            updateLanguageFile(jarFile, entry, dataDirFile);
        }
    }

    private void updateLanguageFile(JarFile jarFile, JarEntry entry, File dataDirFile) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        // messages loaded from the classpath
        Map<String, String> jarMessages = yaml.load(jarFile.getInputStream(entry));
        // messages loaded from the plugins/ directory
        Map<String, String> dataDirMessages;
        try {
            dataDirMessages = loadLanguageFile(dataDirFile);
        } catch (LangManagerException e) {
            plugin.getLogger().severe(e.getMessage());
            return;
        }

        if (dataDirMessages == null) {
            dataDirMessages = jarMessages;
        } else {
            for (Map.Entry<String, String> messageEntry : jarMessages.entrySet()) {
                dataDirMessages.putIfAbsent(messageEntry.getKey(), messageEntry.getValue());
            }
        }

        try (FileWriter writer = new FileWriter(dataDirFile)) {
            yaml.dump(dataDirMessages, writer);
        }
    }

    private File getJarPath() {
        try {
            return Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).toFile();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
