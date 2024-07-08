package pl.nadwey.nadarenas.common.storage.implementation.sql.connection;

import pl.nadwey.nadarenas.common.plugin.NadArenasPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    String getImplementationName();

    String getUrl();

    void init(NadArenasPlugin plugin);

    void shutdown() throws Exception;

    Connection getConnection() throws SQLException;
}
