package pl.nadwey.nadarenas.storage.implementation.sql.connection;

import pl.nadwey.nadarenas.NadArenas;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    String getImplementationName();

    String getUrl();

    void init(NadArenas plugin);

    void shutdown() throws Exception;

    Connection getConnection() throws SQLException;
}
