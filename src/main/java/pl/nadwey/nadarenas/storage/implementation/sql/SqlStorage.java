package pl.nadwey.nadarenas.storage.implementation.sql;

import org.bukkit.Material;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.generated.tables.Arena;
import org.jooq.generated.tables.records.ArenaRecord;
import pl.nadwey.nadarenas.NadArenas;
import pl.nadwey.nadarenas.storage.implementation.StorageImplementation;
import pl.nadwey.nadarenas.storage.implementation.sql.connection.ConnectionFactory;

import java.sql.SQLException;

import java.util.List;

public class SqlStorage implements StorageImplementation {
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

        try {
            getPlugin().getLogger().info("Initializing jOOQ...");

            // jOOQ seems to initialize on the first query, so we force it here
            getConnectionFactory().getDSLContext().selectOne().fetch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void createArena(ArenaRecord arena) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        arena.attach(context.configuration());
        arena.store();
    }

    @Override
    public ArenaRecord getArenaByName(String name) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        return context
                .select().from(Arena.ARENA).where(Arena.ARENA.NAME.eq(name))
                .fetchAnyInto(ArenaRecord.class);
    }

    @Override
    public boolean arenaExists(String name) throws SQLException {
        return getArenaByName(name) != null;
    }

    @Override
    public List<ArenaRecord> getAllArenas() throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        return context.select().from(Arena.ARENA).fetchInto(ArenaRecord.class);
    }

    @Override
    public void setArenaRestorerEnabled(Integer arenaId, Boolean enabled) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        context.update(Arena.ARENA)
                .set(Arena.ARENA.ENABLE_RESTORER, enabled)
                .where(Arena.ARENA.ID.eq(arenaId))
                .execute();
    }

    @Override
    public void setArenaRestorerBlocksPerTick(Integer arenaId, Integer restorerBlocksPerTick) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        context.update(Arena.ARENA)
                .set(Arena.ARENA.RESTORER_BLOCKS_PER_TICK, restorerBlocksPerTick)
                .where(Arena.ARENA.ID.eq(arenaId))
                .execute();
    }

    @Override
    public void setArenaDisplayName(Integer arenaId, String displayName) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        context.update(Arena.ARENA)
                .set(Arena.ARENA.DISPLAY_NAME, displayName)
                .where(Arena.ARENA.ID.eq(arenaId))
                .execute();
    }

    @Override
    public void setArenaDescription(Integer arenaId, String description) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        context.update(Arena.ARENA)
                .set(Arena.ARENA.DESCRIPTION, description)
                .where(Arena.ARENA.ID.eq(arenaId))
                .execute();
    }

    @Override
    public void setArenaItem(Integer arenaId, Material item) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        context.update(Arena.ARENA)
                .set(Arena.ARENA.ITEM, item.toString())
                .where(Arena.ARENA.ID.eq(arenaId))
                .execute();
    }

    @Override
    public void removeArena(Integer arenaId) throws SQLException {
        DSLContext context = getConnectionFactory().getDSLContext();

        context.delete(Arena.ARENA).where(Arena.ARENA.ID.eq(arenaId)).execute();
    }
}
