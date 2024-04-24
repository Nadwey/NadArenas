package pl.nadwey.arenamanager;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.arenamanager.command.CommandManager;
import pl.nadwey.arenamanager.database.DatabaseArenaManager;
import pl.nadwey.arenamanager.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.SQLException;

public final class ArenaManager extends JavaPlugin {
    private CommandManager commandManager;
    private DatabaseConnector databaseConnector;
    private DatabaseArenaManager dbArenaManager;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        commandManager = new CommandManager(this);
        commandManager.enable();

        try {
            databaseConnector = new DatabaseConnector(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dbArenaManager = new DatabaseArenaManager(this);
    }

    @Override
    public void onDisable() {
        commandManager.disable();
        try {
            databaseConnector.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getDatabaseConnection() {
        try {
            return databaseConnector.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabaseArenaManager getDbArenaManager() {
        return this.dbArenaManager;
    }
}
