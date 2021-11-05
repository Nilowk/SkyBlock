package fr.nilowk.skyblock.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GuiManager implements Listener {

    private Map<Class<? extends GuiInterface>, GuiInterface> guis = new HashMap<>();

    public void registerGui(GuiInterface gui) {

        guis.put(gui.getClass(), gui);

    }

    public void openGui(Player player, Class<? extends GuiInterface> guiClass) {

        GuiInterface gui = guis.get(guiClass);
        String title = gui.title() != null ? gui.title() : "DefaultInventory";
        Inventory inv = Bukkit.createInventory(null, gui.size(), title);
        gui.guiGestion(player, inv);
        player.openInventory(inv);

    }

    @EventHandler
    public void onInteract(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        InventoryAction action = event.getAction();
        InventoryView view = event.getView();
        ClickType clickType = event.getClick();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();

        if (inv == null || item == null) return;

        guis.values().stream()
                .filter(guiInterface -> guiInterface.title().equalsIgnoreCase(view.getTitle().replace("&" , "§")) || guiInterface.title().equalsIgnoreCase("DefaultInventory"))
                .forEach(guiInterface -> {

                     switch (guiInterface.cancelType()) {

                         case ALL:
                             event.setCancelled(true);
                             break;

                         case CUSTOMINVENTORY:

                             if (event.getRawSlot() > 35) {

                                 event.setCancelled(true);

                             }
                             break;

                         case PLAYERINVENTORY:

                             if (event.getRawSlot() <= 35) {

                                 event.setCancelled(true);

                             }
                             break;

                         case ITEMLIST:

                             if (guiInterface.items() != null) {

                                 guiInterface.items().forEach(itemStack -> {

                                     if (item.getItemMeta() == item.getItemMeta()) {

                                         event.setCancelled(true);
                                         guiInterface.onInteract(player, inv, action, view, clickType, item, slot);
                                         return;

                                     }

                                 });

                             }
                             break;

                     }

                     guiInterface.onInteract(player, inv, action, view, clickType, item, slot);

        });

    }

}
