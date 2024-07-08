package pl.nadwey.nadarenas.common.storage;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StorageType {
    SQLITE("SQLite", "sqlite");

    private final String name;

    private final List<String> identifiers;

    StorageType(String name, String... identifiers) {
        this.name = name;
        this.identifiers = Arrays.stream(identifiers).toList();
    }

    public static StorageType parse(String name, StorageType def) {
        for (StorageType t : values()) {
            for (String id : t.getIdentifiers()) {
                if (id.equalsIgnoreCase(name)) {
                    return t;
                }
            }
        }
        return def;
    }
}