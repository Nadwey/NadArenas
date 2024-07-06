package pl.nadwey.nadarenas.storage.implementation.sql.connection.file;

import pl.nadwey.nadarenas.NadArenas;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnectionFactory extends FlatfileConnectionFactory {
    private Connection connection;

    public SqliteConnectionFactory(Path file) {
        super(file);
    }

    @Override
    public String getImplementationName() {
        return "SQLite";
    }

    @Override
    public String getUrl() {
        return "jdbc:sqlite:" + getFile().toString();
    }

    @Override
    public void init(NadArenas plugin) {
        try {
            connection = DriverManager.getConnection(getUrl());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() throws Exception {
        connection.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }
}
