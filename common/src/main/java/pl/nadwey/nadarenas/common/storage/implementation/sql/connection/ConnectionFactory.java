package pl.nadwey.nadarenas.common.storage.implementation.sql.connection;

import pl.nadwey.nadarenas.common.INadArenasPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    String getUrl();

    void init(INadArenasPlugin plugin);

    void shutdown() throws Exception;

    Connection getConnection() throws SQLException;
}
