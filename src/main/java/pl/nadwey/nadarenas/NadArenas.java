package pl.nadwey.nadarenas;

import org.bukkit.plugin.java.JavaPlugin;
import pl.nadwey.nadarenas.command.CommandHandler;
import pl.nadwey.nadarenas.database.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;

public final class NadArenas extends JavaPlugin {
    private static NadArenas instance;
    private CommandHandler commandHandler;
    private DatabaseHandler databaseHandler;

    public static NadArenas getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        getDataFolder().mkdir();

        commandHandler = new CommandHandler(this);
        databaseHandler = new DatabaseHandler(this);

        databaseHandler.onLoad();
        commandHandler.onLoad();
    }

    @Override
    public void onEnable() {
        databaseHandler.onEnable();
        commandHandler.onEnable();
    }

    @Override
    public void onDisable() {
        commandHandler.onDisable();
        databaseHandler.onDisable();
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}
