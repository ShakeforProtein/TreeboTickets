package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.Guis.ListGuis;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TbTAGuiMainMenuLinks implements Listener {

    private TreeboTickets pl;
    private ListGuis listGuis;

    public TbTAGuiMainMenuLinks(TreeboTickets main) {
        pl = main;
        this.listGuis = new ListGuis(main);
    }


    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        String name = inv.getName();
        int slot = e.getSlot();
        if (slot < 0) {
            p.closeInventory();
            return;
        }

        if (name.equalsIgnoreCase("Tickets Main Menu")) {
            e.setCancelled(true);
            if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("Your Assigned Tickets")) {
                listGuis.listAssignedGui(p);
            } else if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("Unassigned Tickets")) {
                listGuis.listUnassignedGui(p);
            } else if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("All Open Tickets")) {
                listGuis.listOpenGui(p);
            } else if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("All Closed Tickets")) {
                listGuis.listClosedGui(p);
            }
        }
    }
}
