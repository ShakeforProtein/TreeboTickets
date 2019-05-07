package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class GuiDragStealBlock implements Listener {

    private TreeboTickets pl;

    public GuiDragStealBlock(TreeboTickets main) {
        this.pl = main;
    }

    @EventHandler
    public void invDragEvent(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        String name = e.getView().getTitle();
        if (name.equalsIgnoreCase("Your assigned tickets") ||
                name.equalsIgnoreCase("Unassigned tickets") ||
                name.equalsIgnoreCase("All open tickets") ||
                name.equalsIgnoreCase("All closed tickets")) {
            e.setCancelled(true);
            return;
        }
    }
}
