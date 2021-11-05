package fr.nilowk.skyblock.tasks;

import fr.nilowk.skyblock.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class RegenTask extends BukkitRunnable {

    private Main instance;
    private FileConfiguration config;
    private Block block;
    private Material material;
    private int timer = 10;

    public RegenTask(Main instance, Block block, Material material) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.block = block;
        this.material = material;

    }

    @Override
    public void run() {

        if (timer == 0) {

            block.setType(material);
            cancel();

        }
        timer--;

    }

}
