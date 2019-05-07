package me.shakeforprotein.treebotickets.Listeners.InventoryEvents;

import me.shakeforprotein.treebotickets.Methods.Guis.OpenTicketGui;
import me.shakeforprotein.treebotickets.Methods.Guis.SpecificTicketGui;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TbTAGuiListLinks implements Listener {
    private TreeboTickets pl;
    private OpenTicketGui openTicketGui;
    private SpecificTicketGui specificTicketGui;


    public TbTAGuiListLinks(TreeboTickets main) {
        pl = main;
        this.openTicketGui = new OpenTicketGui(main);
        this.specificTicketGui = new SpecificTicketGui(main);
    }


    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        String name = e.getView().getTitle();
        int slot = e.getSlot();
        if (slot < 0) {
            p.closeInventory();
            return;
        }

        if (name.equalsIgnoreCase("Ticket List - Assigned to you") ||
                name.equalsIgnoreCase("Ticket List - Unassigned ALL") ||
                name.equalsIgnoreCase("Ticket List - Open ALL") ||
                name.equalsIgnoreCase("Ticket List - Closed ALL") ||
                name.equalsIgnoreCase("Ticket List - Builder List")) {
            e.setCancelled(true);
            if (e.getClickedInventory().getItem(slot).hasItemMeta() && e.getClickedInventory().getItem(slot).getItemMeta().getLore().toArray().length == 2 && e.getClickedInventory().getItem(slot).getItemMeta().getLore().get(1).equalsIgnoreCase("Main Menu")) {
                openTicketGui.openTicketGui(p);
            } else {
                int ticketID = Integer.parseInt(e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().split(" - ")[1]);
                specificTicketGui.specificTicketGui(p, ticketID, "Ticket - " + ticketID, name.split(" - ")[1].split(" ")[0]);
            }
        }
    }
}
