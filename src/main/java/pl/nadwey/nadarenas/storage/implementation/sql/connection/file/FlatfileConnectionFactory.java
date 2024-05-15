package pl.nadwey.nadarenas.storage.implementation.sql.connection.file;

import pl.nadwey.nadarenas.storage.implementation.sql.connection.ConnectionFactory;

import java.nio.file.Path;

public abstract class FlatfileConnectionFactory implements ConnectionFactory {
    private final Path file;

    FlatfileConnectionFactory(Path file) {
        this.file = file;
    }

    protected Path getFile() {
        return file;
    }
}
