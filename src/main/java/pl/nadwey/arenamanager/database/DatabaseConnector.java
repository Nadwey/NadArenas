package pl.nadwey.arenamanager.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pl.nadwey.arenamanager.ArenaManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private final HikariConfig config = new HikariConfig();
    private final HikariDataSource ds;
    private final Connection conn;

    public DatabaseConnector(ArenaManager plugin) throws SQLException {
        config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder().toPath().resolve("arenamanager.db"));
        ds = new HikariDataSource(config);
        conn = ds.getConnection();

        createSchema();
    }

    public Connection getConnection() throws SQLException {
        return conn;
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    void createSchema() throws SQLException {
        final Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS arenas (id INTEGER NOT NULL, name TEXT NOT NULL UNIQUE, world TEXT NOT NULL, display_name TEXT, description TEXT, PRIMARY KEY(id))");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS spawns (id INTEGER NOT NULL, arena_id INTEGER NOT NULL, x DOUBLE NOT NULL, y DOUBLE NOT NULL, z DOUBLE NOT NULL, yaw DOUBLE NOT NULL, pitch DOUBLE NOT NULL, PRIMARY KEY(id))");
    }
}
