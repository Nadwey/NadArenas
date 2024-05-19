package pl.nadwey.nadarenas.storage.implementation.sql.connection.file;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.SingleConnectionDataSource;
import pl.nadwey.nadarenas.NadArenas;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnectionFactory extends FlatfileConnectionFactory {
    private Connection connection;
    private SingleConnectionDataSource ds;

    public SqliteConnectionFactory(Path file) {
        super(file);
    }

    @Override
    public String getImplementationName() {
        return "SQLite";
    }

    @Override
    public void init(NadArenas plugin) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + getFile().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ds = new SingleConnectionDataSource(connection);
    }

    @Override
    public void shutdown() throws Exception {
        connection.close();
    }

    @Override
    public DSLContext getDSLContext() throws SQLException {
        return DSL.using(getConnection(), SQLDialect.SQLITE);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return ds;
    }
}
