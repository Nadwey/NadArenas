package pl.nadwey.nadarenas.common.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.nadwey.nadarenas.common.storage.StorageType;

@Getter
@Setter
public class StorageConfig extends OkaeriConfig {
    @Comment("Storage method")
    @CustomKey("storage-method")
    StorageType storageMethod = StorageType.SQLITE;

    @Comment
    @Comment("The address of the database")
    String address = "localhost";

    @Comment
    @Comment("The name of the database to store data in")
    String database = "nadarenas";

    @Comment
    @Comment("Credentials for the database")
    String username = "root";
    String password = "";

    @Comment
    @Comment("The prefix for all NadArenas tables.")
    @CustomKey("table-prefix")
    String tablePrefix = "nadarenas_";
}
