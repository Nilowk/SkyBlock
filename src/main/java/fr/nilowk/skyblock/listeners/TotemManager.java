package fr.nilowk.skyblock.listeners;

import fr.nilowk.skyblock.Main;
import fr.nilowk.skyblock.tasks.RegenTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TotemManager implements Listener {

    private Main instance;
    private FileConfiguration config;
    private int[][] cor;
    private int[] pattern;

    public TotemManager(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.cor = new int[][] {

                {-2, -2}, {-1, -2}, {0, -2}, {1, -2}, {2, -2},

                {-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {2, -1},

                {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0},

                {-2, 1}, {-1, 1}, {0, 1}, {1, 1}, {2, 1},

                {-2, 2}, {-1, 2}, {0, 2}, {1, 2}, {2, 2}

        };
        this.pattern = new int[] {
                0, 1, 0, 1, 0,
                1, 0, 0, 0, 1,
                0, 0, 0, 0, 0,
                1, 0, 0, 0, 1,
                0, 1, 0, 1, 0,
        };

    }

    @EventHandler
    public void PlaceTotem(PlayerInteractEvent event) {

        if (event.getItem() == null) return;
        ItemStack item = event.getItem();

        if (item.getType() != Material.ARMOR_STAND) return;
        if (!item.hasItemMeta()) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Location loc = event.getClickedBlock().getLocation().add(0, 1, 0);

        switch (item.getItemMeta().getDisplayName()) {

            case "§7Totem of Stone":
                event.setCancelled(true);
                ArmorStand stand = player.getWorld().spawn(loc.getBlock().getLocation().add(0.5, 0.0, 0.5), ArmorStand.class);
                stoneTotem(stand, loc);
                break;

        }

    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {

        Block block = event.getBlock();
        if (instance.getStone().contains(block)) breakStone(block);

    }

    private void stoneTotem(ArmorStand stand, Location loc) {

        setBlocks(loc, Material.STONE);

    }

    private void breakStone(Block block) {

        RegenTask regenTask = new RegenTask(instance, block, Material.STONE);
        regenTask.runTaskTimer(instance, 0, 20);

    }

    private void setBlocks(Location loc, Material mat) {

        for (int i = 0; i < this.pattern.length; i++) {

            if (pattern[i] == 1) {

                Block block = loc.getBlock().getLocation().add(cor[i][0], 0, cor[i][1]).getBlock();
                block.getLocation().getBlock().setType(mat);
                instance.getStone().add(block);

            }

        }

    }

}
