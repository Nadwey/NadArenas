package pl.nadwey.nadarenas.database.queries;

import org.bukkit.Material;
import org.bukkit.World;
import pl.nadwey.nadarenas.database.types.Arena;
import pl.nadwey.nadarenas.utility.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ArenaManager {
    public static void createArena(String name, World world) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("INSERT INTO arenas(name, world) VALUES (?, ?)");

        stmt.setString(1, name);
        stmt.setString(2, world.getUID().toString());

        stmt.executeUpdate();
    }

    public static Integer getArenaID(String name) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT id FROM arenas WHERE name = ?");
        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;
        return rs.getInt("id");
    }

    public static boolean arenaExists(String name) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT id FROM arenas WHERE name = ?");

        stmt.setString(1, name);

        return stmt.executeQuery().next();
    }

    public static Arena getArena(String name) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT name, world, display_name, description, item FROM arenas WHERE name = ?");

        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;

        String world = rs.getString("world");
        String displayName = rs.getString("display_name");
        String description = rs.getString("description");
        Material item = Material.getMaterial(rs.getString("item"));


        return new Arena(name, world, displayName, description, item);
    }

    public static List<Arena> listArenas() throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT name, world, display_name, description, item FROM arenas");
        ResultSet rs = stmt.executeQuery();

        List<Arena> arenas = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            String world = rs.getString("world");
            String displayName = rs.getString("display_name");
            String description = rs.getString("description");
            Material item = Material.getMaterial(rs.getString("item"));

            arenas.add(new Arena(name, world, displayName, description, item));
        }

        return arenas;
    }

    public static void setArenaDisplayName(String name, String displayName) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("UPDATE arenas SET display_name = ? WHERE name = ?");

        stmt.setString(1, displayName);
        stmt.setString(2, name);

        stmt.executeUpdate();
    }

    public static void setArenaDescription(String name, String description) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("UPDATE arenas SET description = ? WHERE name = ?");

        stmt.setString(1, description);
        stmt.setString(2, name);

        stmt.executeUpdate();
    }

    public static void setArenaItem(String name, Material item) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("UPDATE arenas SET item = ? WHERE name = ?");

        stmt.setString(1, item.name());
        stmt.setString(2, name);

        stmt.executeUpdate();
    }

    public static void removeArena(String name) throws SQLException {
        Integer arenaID = getArenaID(name);

        if (arenaID == null) return;

        // TODO: remove relations

        PreparedStatement stmt = DB.getConnection().prepareStatement("DELETE FROM arenas WHERE name = ?");
        stmt.setString(1, name);
        stmt.executeUpdate();
    }
}
