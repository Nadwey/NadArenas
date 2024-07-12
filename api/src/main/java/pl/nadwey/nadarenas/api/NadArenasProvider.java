package pl.nadwey.nadarenas.api;

import org.jetbrains.annotations.NotNull;

public class NadArenasProvider {
    private static NadArenas instance = null;

    public static @NotNull NadArenas get() {
        NadArenas instance = NadArenasProvider.instance;
        if (instance == null) {
            throw new IllegalStateException("NadArenasProvider has not been initialized");
        }
        return instance;
    }

    static void register(NadArenas instance) {
        NadArenasProvider.instance = instance;
    }

    static void unregister() {
        NadArenasProvider.instance = null;
    }
}
