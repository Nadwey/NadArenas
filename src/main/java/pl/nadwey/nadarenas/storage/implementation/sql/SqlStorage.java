package pl.nadwey.nadarenas.storage.implementation.sql;

import org.bukkit.Material;
import org.flywaydb.core.Flyway;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.storage.implementation.sql.connection.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements StorageImplementation {
    public static final String ARENA_INSERT = "INSERT INTO nadarenas_arenas(name, world) VALUES (?, ?)";
    public static final String ARENA_SELECT = "SELECT world, display_name, description, item FROM nadarenas_arenas WHERE name = ?";
    public static final String ARENA_SELECT_ID = "SELECT id FROM nadarenas_arenas WHERE name = ?";
    public static final String ARENA_SELECT_ALL = "SELECT name, world, display_name, description, item FROM nadarenas_arenas";
    public static final String ARENA_UPDATE_DISPLAY_NAME = "UPDATE nadarenas_arenas SET display_name = ? WHERE name = ?";
    public static final String ARENA_UPDATE_DESCRIPTION = "UPDATE nadarenas_arenas SET description = ? WHERE name = ?";
    public static final String ARENA_UPDATE_ITEM = "UPDATE nadarenas_arenas SET item = ? WHERE name = ?";
    public static final String ARENA_DELETE = "DELETE FROM nadarenas_arenas WHERE name = ?";

    private final NadArenas plugin;

    private final ConnectionFactory connectionFactory;

    public SqlStorage(NadArenas plugin, ConnectionFactory connectionFactory) {
        this.plugin = plugin;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public NadArenas getPlugin() {
        return this.plugin;
    }

    @Override
    public String getImplementationName() {
        return "";
    }

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    @Override
    public void init() {
        this.connectionFactory.init(plugin);

        migrate();
    }

    private void migrate() {
        Flyway flyway = Flyway
                .configure(getClass().getClassLoader())
                .baselineOnMigrate(true)
                .dataSource(this.connectionFactory.getDataSource())
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }

    @Override
    public void shutdown() {
        try {
            this.connectionFactory.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getArenaID(String name) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_SELECT_ID);

        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) return null;

        return rs.getInt("id");
    }

    @Override
    public void createArena(Arena arena) throws SQLException {
        PreparedStatement ps = getConnectionFactory().getConnection().prepareStatement(ARENA_INSERT);

        ps.setString(1, arena.getName());
        ps.setString(2, arena.getWorld());

        ps.executeUpdate();
    }

    @Override
    public Arena getArena(String name) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_SELECT);

        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;

        String world = rs.getString("world");
        String displayName = rs.getString("display_name");
        String description = rs.getString("description");
        Material item = Material.getMaterial(rs.getString("item"));

        return new Arena(name, world, displayName, description, item);
    }

    @Override
    public List<Arena> getArenas() throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_SELECT_ALL);
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

    @Override
    public void setArenaDisplayName(String arena, String displayName) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_UPDATE_DISPLAY_NAME);

        stmt.setString(1, displayName);
        stmt.setString(2, arena);

        stmt.executeUpdate();
    }

    @Override
    public void removeArena(String name) throws SQLException {
        Integer arenaID = getArenaID(name);

        if (arenaID == null) return;

        // TODO: remove relations

        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_DELETE);
        stmt.setString(1, name);
        stmt.executeUpdate();
    }

    public void setArenaDescription(String name, String description) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_UPDATE_DESCRIPTION);

        stmt.setString(1, description);
        stmt.setString(2, name);

        stmt.executeUpdate();
    }

    public void setArenaItem(String name, Material item) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_UPDATE_ITEM);

        stmt.setString(1, item.name());
        stmt.setString(2, name);

        stmt.executeUpdate();
    }
}
