package fr.nilowk.skyblock.listeners;

import fr.nilowk.skyblock.Main;
import fr.nilowk.skyblock.utils.db.DbConnection;
import fr.nilowk.skyblock.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerJoin implements Listener {

    private Main instance;
    private FileConfiguration config;

    public PlayerJoin(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final DbConnection playerConnection = instance.getDatabaseManager().getPlayerConnection();

        player.teleport(instance.getSpawn());

        player.getInventory().setItem(0, new ItemBuilder(Material.ARMOR_STAND, true).setName("§7Totem of Stone").build());

        try {

            final Connection connection = playerConnection.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, grade FROM players WHERE uuid = ?");

            preparedStatement.setString(1, uuid.toString());

            final ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) return;

            final String grade = resultSet.getString("grade");
            loadGrade(uuid, grade);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    private void loadGrade(UUID uuid, String grade) {

        if (instance.getPlayerGrades().containsKey(uuid)) return;
        instance.getPlayerGrades().put(uuid, grade);

    }

}
