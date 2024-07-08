package pl.nadwey.nadarenas.common.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import lombok.Setter;
import pl.nadwey.nadarenas.common.storage.StorageType;

@Header("##################################")
@Header("#                                #")
@Header("#     NadArenas                  #")
@Header("#                                #")
@Header("#     Database configuration     #")
@Header("#                                #")
@Header("##################################")
@Getter
@Setter
public class StorageConfig extends OkaeriConfig {
    @Comment
    @Comment
    @Comment
    @Comment("Storage method")
    @Comment("  Available types are:")
    @Comment("    - SQLite - local, doesn't require any further configuration")
    @Comment("    - MariaDB - soon™")
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
    @Comment("Changing this after the first run will cause issues.")
    @Comment("Please be careful with this setting as it is directly injected into the SQL.")
    @CustomKey("table-prefix")
    String tablePrefix = "nadarenas_";
}
