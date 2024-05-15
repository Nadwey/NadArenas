package pl.nadwey.nadarenas.storage.implementation.sql;

import org.bukkit.Material;
import org.flywaydb.core.Flyway;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.model.Position;
import pl.nadwey.nadarenas.model.arena.Arena;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.storage.implementation.sql.connection.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements StorageImplementation {
    public static final String ARENA_INSERT = "INSERT INTO nadarenas_arenas(name, enable_restorer, world, min_x, min_y, min_z, max_x, max_y, max_z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ARENA_SELECT = "SELECT enable_restorer, world, min_x, min_y, min_z, max_x, max_y, max_z, restorer_blocks_per_tick, display_name, description, item FROM nadarenas_arenas WHERE name = ?";
    public static final String ARENA_SELECT_ID = "SELECT id FROM nadarenas_arenas WHERE name = ?";
    public static final String ARENA_SELECT_ALL = "SELECT enable_restorer, name, world, min_x, min_y, min_z, max_x, max_y, max_z, restorer_blocks_per_tick, display_name, description, item FROM nadarenas_arenas";
    public static final String ARENA_UPDATE_RESTORER_BLOCKS_PER_TICK = "UPDATE nadarenas_arenas SET restorer_blocks_per_tick = ? WHERE name = ?";
    public static final String ARENA_UPDATE_RESTORER_SUPPORT = "UPDATE nadarenas_arenas SET enable_restorer = ? WHERE name = ?";
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
        return plugin;
    }

    @Override
    public String getImplementationName() {
        return "";
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void init() {
        connectionFactory.init(plugin);

        migrate();
    }

    private void migrate() {
        Flyway flyway = Flyway
                .configure(getClass().getClassLoader())
                .baselineOnMigrate(true)
                .dataSource(connectionFactory.getDataSource())
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }

    @Override
    public void shutdown() {
        try {
            connectionFactory.shutdown();
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
        Boolean enableRestorer = rs.getBoolean("enable_restorer");
        String world = rs.getString("world");
        String displayName = rs.getString("display_name");
        String description = rs.getString("description");

        Integer minX = rs.getInt("min_x");
        Integer minY = rs.getInt("min_y");
        Integer minZ = rs.getInt("min_z");
        Integer maxX = rs.getInt("max_x");
        Integer maxY = rs.getInt("max_y");
        Integer maxZ = rs.getInt("max_z");

        Integer restorerBlocksPerTick = rs.getInt("restorer_blocks_per_tick");

        Material item = Material.getMaterial(rs.getString("item"));

        Position minPosition = new Position(minX, minY, minZ);
        Position maxPosition = new Position(maxX, maxY, maxZ);

        Arena arena = new Arena(name, enableRestorer, world, minPosition, maxPosition);
        arena.setRestorerBlocksPerTick(restorerBlocksPerTick);
        arena.setDisplayName(displayName);
        arena.setDescription(description);
        arena.setItem(item);

        return arena;
    }

    @Override
    public void createArena(Arena arena) throws SQLException {
        PreparedStatement ps = getConnectionFactory().getConnection().prepareStatement(ARENA_INSERT);

        ps.setString(1, arena.getName());
        ps.setBoolean(2, arena.getEnableRestorer());
        ps.setString(3, arena.getWorld());
        ps.setInt(4, arena.getMinPosition().x());
        ps.setInt(5, arena.getMinPosition().y());
        ps.setInt(6, arena.getMinPosition().z());
        ps.setInt(7, arena.getMaxPosition().x());
        ps.setInt(8, arena.getMaxPosition().y());
        ps.setInt(9, arena.getMaxPosition().z());

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
    public void setArenaRestorerEnabled(String arena, Boolean enabled) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_UPDATE_RESTORER_SUPPORT);

        stmt.setBoolean(1, enabled);
        stmt.setString(2, arena);

        stmt.executeUpdate();
    }

    @Override
    public void setArenaRestorerBlocksPerTick(String arena, Integer restorerBlocksPerTick) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_UPDATE_RESTORER_BLOCKS_PER_TICK);

        stmt.setInt(1, restorerBlocksPerTick);
        stmt.setString(2, arena);

        stmt.executeUpdate();
    }

    @Override
    public void setArenaDisplayName(String arena, String displayName) throws SQLException {
        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_UPDATE_DISPLAY_NAME);

        stmt.setString(1, displayName);
        stmt.setString(2, arena);

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

    @Override
    public void removeArena(String name) throws SQLException {
        Integer arenaID = getArenaID(name);

        if (arenaID == null) return;

        // TODO: remove relations

        PreparedStatement stmt = getConnectionFactory().getConnection().prepareStatement(ARENA_DELETE);
        stmt.setString(1, name);
        stmt.executeUpdate();
    }
}
