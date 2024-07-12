package pl.nadwey.nadarenas.common.lang;

import lombok.Getter;

@Getter
public enum LangMessage {
    STORAGE_ENABLING("storage-enabling"),
    STORAGE_DISABLING("storage-disabling"),
    STORAGE_LOADING_PROVIDER("storage-loading-provider"),

    INITIALIZED("initialized");

    private final String key;

    LangMessage(String key) {
        this.key = key;
    }
}
