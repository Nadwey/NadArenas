package pl.nadwey.nadarenas.common.storage.implementation.sql;

import lombok.Getter;
import org.apache.commons.text.StringSubstitutor;
import org.flywaydb.core.Flyway;
import pl.nadwey.nadarenas.common.INadArenasPlugin;
import pl.nadwey.nadarenas.api.model.arena.Arena;
import pl.nadwey.nadarenas.common.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.common.storage.implementation.sql.connection.ConnectionFactory;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements StorageImplementation {
    private final String tablePrefix;

    public static final String ARENA_INSERT = "INSERT INTO ${table_prefix}arenas(name, enable_restorer, world, min_x, min_y, min_z, max_x, max_y, max_z, restorer_blocks_per_tick, display_name, description, item) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ARENA_SELECT = "SELECT id, name, enable_restorer, world, min_x, min_y, min_z, max_x, max_y, max_z, restorer_blocks_per_tick, display_name, description, item FROM ${table_prefix}arenas WHERE name = ?";
    public static final String ARENA_SELECT_ALL = "SELECT id, name, enable_restorer, world, min_x, min_y, min_z, max_x, max_y, max_z, restorer_blocks_per_tick, display_name, description, item FROM ${table_prefix}arenas";
    public static final String ARENA_UPDATE_ENABLE_RESTORER = "UPDATE ${table_prefix}arenas SET enable_restorer = ? WHERE id = ?";
    public static final String ARENA_UPDATE_RESTORER_BLOCKS_PER_TICK = "UPDATE ${table_prefix}arenas SET restorer_blocks_per_tick = ? WHERE id = ?";
    public static final String ARENA_UPDATE_DISPLAY_NAME = "UPDATE ${table_prefix}arenas SET display_name = ? WHERE id = ?";
    public static final String ARENA_UPDATE_DESCRIPTION = "UPDATE ${table_prefix}arenas SET description = ? WHERE id = ?";
    public static final String ARENA_UPDATE_ITEM = "UPDATE ${table_prefix}arenas SET item = ? WHERE id = ?";
    public static final String ARENA_DELETE = "DELETE FROM ${table_prefix}arenas WHERE id = ?";

    @Getter
    private final INadArenasPlugin plugin;

    @Getter
    private final ConnectionFactory connectionFactory;

    public SqlStorage(INadArenasPlugin plugin, ConnectionFactory connectionFactory) {
        this.plugin = plugin;
        this.connectionFactory = connectionFactory;

        tablePrefix = plugin.getConfigManager().getStorageConfig().getTablePrefix();
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
                .dataSource(connectionFactory.getUrl(), "", "")
                .locations("classpath:db/migration")
                .table(tablePrefix + "schema_history")
                .placeholders(Map.of(
                        "table_prefix", tablePrefix
                ))
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

    private String prepareQueryString(String string) {
        return StringSubstitutor.replace(string, Map.of(
                "table_prefix", tablePrefix
        ));
    }

    private Arena getArenaFromResultSet(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");

        Boolean enableRestorer = rs.getBoolean("enable_restorer");
        String world = rs.getString("world");
        String displayName = rs.getString("display_name");
        String description = rs.getString("description");
        String item = rs.getString("item");

        Integer minX = rs.getInt("min_x");
        Integer minY = rs.getInt("min_y");
        Integer minZ = rs.getInt("min_z");
        Integer maxX = rs.getInt("max_x");
        Integer maxY = rs.getInt("max_y");
        Integer maxZ = rs.getInt("max_z");

        Integer restorerBlocksPerTick = rs.getInt("restorer_blocks_per_tick");


        Arena arena = new Arena(name, world, enableRestorer, minX, minY, minZ, maxX, maxY, maxZ);
        arena.setId(id);
        arena.setRestorerBlocksPerTick(restorerBlocksPerTick);
        arena.setDisplayName(displayName);
        arena.setDescription(description);
        arena.setItem(item);

        return arena;
    }

    @Override
    public void createArena(Arena arena) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_INSERT));

        ps.setString(1, arena.getName());
        ps.setBoolean(2, arena.getEnableRestorer());
        ps.setString(3, arena.getWorld());
        ps.setInt(4, arena.getMinPos().x());
        ps.setInt(5, arena.getMinPos().y());
        ps.setInt(6, arena.getMinPos().z());
        ps.setInt(7, arena.getMaxPos().x());
        ps.setInt(8, arena.getMaxPos().y());
        ps.setInt(9, arena.getMaxPos().z());
        ps.setInt(10, arena.getRestorerBlocksPerTick() == null ? 250 : arena.getRestorerBlocksPerTick());
        ps.setString(11, arena.getDisplayName());
        ps.setString(12, arena.getDescription());
        ps.setString(13, arena.getItem() != null ? arena.getItem() : null);

        ps.executeUpdate();
    }

    @Override
    public Arena getArenaByName(String name) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_SELECT));

        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) return null;

        return getArenaFromResultSet(rs);
    }

    @Override
    public List<Arena> getAllArenas() throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_SELECT_ALL));

        ResultSet rs = ps.executeQuery();

        List<Arena> arenas = new ArrayList<>();
        while (rs.next()) {
            arenas.add(getArenaFromResultSet(rs));
        }

        return arenas;
    }

    @Override
    public void setArenaRestorerEnabled(Integer arenaId, Boolean enabled) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_UPDATE_ENABLE_RESTORER));

        ps.setBoolean(1, enabled);
        ps.setInt(2, arenaId);

        ps.executeUpdate();
    }

    @Override
    public void setArenaRestorerBlocksPerTick(Integer arenaId, Integer restorerBlocksPerTick) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_UPDATE_RESTORER_BLOCKS_PER_TICK));

        ps.setInt(1, restorerBlocksPerTick);
        ps.setInt(2, arenaId);

        ps.executeUpdate();
    }

    @Override
    public void setArenaDisplayName(Integer arenaId, String displayName) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_UPDATE_DISPLAY_NAME));

        ps.setString(1, displayName);
        ps.setInt(2, arenaId);

        ps.executeUpdate();
    }

    @Override
    public void setArenaDescription(Integer arenaId, String description) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_UPDATE_DESCRIPTION));

        ps.setString(1, description);
        ps.setInt(2, arenaId);

        ps.executeUpdate();
    }

    @Override
    public void setArenaItem(Integer arenaId, String item) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_UPDATE_ITEM));

        ps.setString(1, item);
        ps.setInt(2, arenaId);

        ps.executeUpdate();
    }

    @Override
    public void removeArena(Integer arenaId) throws SQLException {
        Connection conn = getConnectionFactory().getConnection();
        PreparedStatement ps = conn.prepareStatement(prepareQueryString(ARENA_DELETE));

        ps.setInt(1, arenaId);
        ps.executeUpdate();
    }
}
