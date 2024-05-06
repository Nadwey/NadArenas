package pl.nadwey.nadarenas.storage.implementation.sql;

import io.papermc.paper.math.BlockPosition;
import org.bukkit.Material;
import org.flywaydb.core.Flyway;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.storage.implementation.sql.connection.ConnectionFactory;

import java.awt.print.Paper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements StorageImplementation {
    public static final String ARENA_INSERT = "INSERT INTO nadarenas_arenas(name, world, min_x, min_y, min_z, max_x, max_y, max_z) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ARENA_SELECT = "SELECT world, display_name, min_x, min_y, min_z, max_x, max_y, max_z, description, item FROM nadarenas_arenas WHERE name = ?";
    public static final String ARENA_SELECT_ID = "SELECT id FROM nadarenas_arenas WHERE name = ?";
    public static final String ARENA_SELECT_ALL = "SELECT name, world, min_x, min_y, min_z, max_x, max_y, max_z, display_name, description, item FROM nadarenas_arenas";
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

    private Arena getArenaFromResultSet(String name, ResultSet rs) throws SQLException {
        String world = rs.getString("world");
        String displayName = rs.getString("display_name");
        String description = rs.getString("description");

        Integer minX = rs.getInt("min_x");
        Integer minY = rs.getInt("min_y");
        Integer minZ = rs.getInt("min_z");
        Integer maxX = rs.getInt("max_x");
        Integer maxY = rs.getInt("max_y");
        Integer maxZ = rs.getInt("max_z");

        Material item = Material.getMaterial(rs.getString("item"));

        Position minPosition = new Position(minX, minY, minZ);
        Position maxPosition = new Position(maxX, maxY, maxZ);

        return new Arena(name, world, minPosition, maxPosition, displayName, description, item);
    }

    @Override
    public void createArena(Arena arena) throws SQLException {
        PreparedStatement ps = getConnectionFactory().getConnection().prepareStatement(ARENA_INSERT);

        ps.setString(1, arena.getName());
        ps.setString(2, arena.getWorld());
        ps.setInt(3, arena.getMinPosition().x());
        ps.setInt(4, arena.getMinPosition().y());
        ps.setInt(5, arena.getMinPosition().z());
        ps.setInt(6, arena.getMaxPosition().x());
        ps.setInt(7, arena.getMaxPosition().y());
        ps.setInt(8, arena.getMaxPosition().z());

        ps.executeUpdate();
    }

    @Override
    public Arena getArena(String name) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_SELECT);

        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;

        return getArenaFromResultSet(name, rs);
    }

    @Override
    public List<Arena> getArenas() throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_SELECT_ALL);
        ResultSet rs = stmt.executeQuery();

        List<Arena> arenas = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");

            arenas.add(getArenaFromResultSet(name, rs));
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
