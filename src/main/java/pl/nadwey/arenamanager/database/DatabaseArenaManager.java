package pl.nadwey.arenamanager.database;

import pl.nadwey.arenamanager.ArenaManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseArenaManager {
    private final Connection conn;

    public DatabaseArenaManager(ArenaManager plugin) {
        conn = plugin.getDatabaseConnection();
    }

    public void createArena(String name, UUID world) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO arenas(name, world) VALUES (?, ?)");

        stmt.setString(1, name);
        stmt.setString(2, world.toString());

        stmt.executeUpdate();
    }

    public void setArenaDisplayName(String name, String displayName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE arenas SET display_name = ? WHERE name = ?");

        stmt.setString(1, displayName);
        stmt.setString(2, name);

        stmt.executeUpdate();
    }
}
