package fr.nilowk.skyblock.utils.db;

import fr.nilowk.skyblock.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.SQLException;

public class DatabaseManager {

    private DbConnection playerConnection;
    private Main instance;
    private FileConfiguration config;

    public DatabaseManager(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.playerConnection = new DbConnection(new DbCredentials(config.getString("db.host"), config.getString("db.user"), config.getString("db.password"), config.getString("db.db-name"), config.getInt("db.port")));

    }

    public DbConnection getPlayerConnection() {

        return playerConnection;

    }

    public void close() {

        try {

            this.playerConnection.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}
