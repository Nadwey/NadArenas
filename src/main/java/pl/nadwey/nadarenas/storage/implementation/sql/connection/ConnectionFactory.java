package pl.nadwey.nadarenas.storage.implementation.sql.connection;

import org.jooq.DSLContext;
import pl.nadwey.nadarenas.NadArenas;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    String getImplementationName();

    void init(NadArenas plugin);

    void shutdown() throws Exception;

    DSLContext getDSLContext() throws SQLException;

    Connection getConnection() throws SQLException;

    DataSource getDataSource();
}
