package pl.nadwey.nadarenas.database;

import org.flywaydb.core.Flyway;

public class DatabaseMigrations {
    private final Flyway flyway;

    public DatabaseMigrations(DatabaseHandler databaseHandler) {

        this.flyway = Flyway
                .configure(getClass().getClassLoader())
                .baselineOnMigrate(true)
                .dataSource(databaseHandler.getConnectionString(), "", "")
                .locations(
                        "classpath:db/migration"
                )
                .load();
    }

    public void migrate() {
        this.flyway.migrate();
    }
}