package pl.nadwey.nadarenas.storage;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum StorageType {
    SQLITE("SQLite", "sqlite");

    private final String name;

    private final List<String> identifiers;

    StorageType(String name, String... identifiers) {
        this.name = name;
        this.identifiers = ImmutableList.copyOf(identifiers);
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

    public String getName() {
        return name;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }
}