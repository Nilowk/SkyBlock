package fr.nilowk.skyblock;

import fr.nilowk.skyblock.listeners.NewPlayer;
import fr.nilowk.skyblock.listeners.PlayerChat;
import fr.nilowk.skyblock.listeners.PlayerJoin;
import fr.nilowk.skyblock.listeners.TotemManager;
import fr.nilowk.skyblock.utils.db.DatabaseManager;
import fr.nilowk.skyblock.utils.gui.GuiManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private GuiManager guiManager;
    private DatabaseManager databaseManager;
    private HashMap<UUID, String> playerGrades;
    private List<Block> stone;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.guiManager = new GuiManager();
        this.databaseManager = new DatabaseManager(this);
        this.playerGrades = new HashMap<>();
        this.stone = new ArrayList<>();
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(guiManager, this);
        pm.registerEvents(new NewPlayer(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerChat(this), this);
        pm.registerEvents(new TotemManager(this), this);
        loadGui();



    }

    @Override
    public void onDisable() {

        this.databaseManager.close();

    }

    public DatabaseManager getDatabaseManager() {

        return this.databaseManager;

    }

    public HashMap<UUID, String> getPlayerGrades() {

        return playerGrades;

    }

    public List<Block> getStone() {

        return stone;

    }

    public Location getSpawn() {

        final World world = this.getServer().getWorld(this.getConfig().getString("tp.spawn.world"));
        final Double x = this.getConfig().getDouble("tp.spawn.x");
        final Double y = this.getConfig().getDouble("tp.spawn.y");
        final Double z = this.getConfig().getDouble("tp.spawn.z");
        final float yaw = (float) this.getConfig().getDouble("tp.spawn.yaw");
        final float pitch = (float) this.getConfig().getDouble("tp.spawn.pitch");

        final Location spawn = new Location(world, x, y, z, yaw, pitch);

        return spawn;

    }

    public void loadGui() {



    }

    public GuiManager getGuiManager() {

        return this.guiManager;

    }

}
