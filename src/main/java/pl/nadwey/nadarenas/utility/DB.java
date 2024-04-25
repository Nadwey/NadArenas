package pl.nadwey.nadarenas.utility;

import pl.nadwey.nadarenas.NadArenas;

import java.sql.Connection;

public abstract class DB {
    public static Connection getConnection() {
        return NadArenas.getInstance().getDatabaseHandler().getConnection();
    }
}
