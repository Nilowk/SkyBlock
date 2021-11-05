package fr.nilowk.skyblock.listeners;

import fr.nilowk.skyblock.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChat implements Listener {

    private Main instance;
    private FileConfiguration config;

    public PlayerChat(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (!instance.getPlayerGrades().containsKey(uuid)) return;

        final String grade = instance.getPlayerGrades().get(uuid);
        final String format = config.getString("style.grade").replace("{GRADE}", grade).replace("{NAME}", player.getDisplayName()).replace("{MESSAGE}", event.getMessage());

        event.setFormat(format);

    }

}
