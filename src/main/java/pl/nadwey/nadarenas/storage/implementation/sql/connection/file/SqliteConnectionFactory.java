package pl.nadwey.nadarenas.storage.implementation.sql.connection.file;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import pl.nadwey.nadarenas.NadArenas;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnectionFactory extends FlatfileConnectionFactory {
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
        this.ds = new SingleConnectionDataSource("jdbc:sqlite:" + getFile().toString(), true);
    }

    @Override
    public void shutdown() throws Exception {
        this.ds.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return this.ds;
    }
}
