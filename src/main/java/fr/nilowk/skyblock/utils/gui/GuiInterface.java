package fr.nilowk.skyblock.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface GuiInterface {

    String title();
    int size();
    CancelType cancelType();
    List<ItemStack> items();
    void guiGestion(Player player, Inventory inventory);
    void onInteract(Player player, Inventory inventory, InventoryAction action, InventoryView view, ClickType clickType, ItemStack clickedItem, int slot);

}
