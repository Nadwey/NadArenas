package pl.nadwey.nadarenas.database;

import org.jetbrains.annotations.NotNull;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.Reloadable;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DatabaseHandler implements Reloadable {
    private final NadArenas plugin;
    private boolean isConnected = false;
    private Connection connection;

    public DatabaseHandler(NadArenas plugin) {
        this.plugin = plugin;
    }

    public String getConnectionString() {
        return "jdbc:sqlite:" + plugin.getDataFolder().toPath().resolve("nadarenas.db");
    }

    @Override
    public void onLoad() {
        try {
            openConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        isConnected = true;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        try {
            if (isConnected) closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void openConnection() throws SQLException {
        if (isConnected) return;
        this.connection = DriverManager.getConnection(getConnectionString());

        new DatabaseMigrations(this).migrate();

        isConnected = true;
    }

    private void closeConnection() throws SQLException {
        this.connection.close();
        isConnected = false;
    }
}