package fr.nilowk.skyblock.listeners;

import fr.nilowk.skyblock.Main;
import fr.nilowk.skyblock.utils.db.DbConnection;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;
import java.util.UUID;

public class NewPlayer implements Listener {

    private Main instance;
    private FileConfiguration config;

    public NewPlayer(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final DbConnection playerConnection = instance.getDatabaseManager().getPlayerConnection();

        try {

            final Connection connection = playerConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, grade FROM players WHERE uuid = ?");

            preparedStatement.setString(1, uuid.toString());

            final ResultSet resultSet = preparedStatement.executeQuery();
            final String grade = config.getString("default.grade");

            if (resultSet.next()) return;

            addPlayerToDb(connection, player);
            if (instance.getPlayerGrades().containsKey(uuid)) return;
            instance.getPlayerGrades().put(uuid, grade);

            createIsland(uuid);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    private void addPlayerToDb(Connection connection, Player player) throws SQLException {

        final UUID uuid = player.getUniqueId();
        final long time = System.currentTimeMillis();
        final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players VALUES (?, ?, ?, ?, ?)");

        preparedStatement.setString(1, uuid.toString());
        preparedStatement.setString(2, player.getDisplayName());
        preparedStatement.setString(3, config.getString("default.grade"));
        preparedStatement.setTimestamp(4, new Timestamp(time));
        preparedStatement.setInt(5, config.getInt("default.money"));

        preparedStatement.executeUpdate();

    }

    private void createIsland(UUID uuid) {

        for (World world : instance.getServer().getWorlds()) {

            if (world.getName().equalsIgnoreCase(uuid.toString())) return;

        }

        World example = instance.getServer().getWorld("example");

        World world = Bukkit.createWorld(new WorldCreator(uuid.toString()).copy(example));

        for (World worl : instance.getServer().getWorlds()) {

            System.out.println(worl.getName());

        }

        instance.getServer().getPlayer(uuid).teleport(new Location(world, 0, 62, 0));

    }

}
