package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.Guis.ListGuis;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TbTAGuiIndividualTicketLinks implements Listener {
    private TreeboTickets pl;
    private ListGuis listGuis;

    public TbTAGuiIndividualTicketLinks(TreeboTickets main) {
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


        if (name != null && name.split(" - ")[0].equalsIgnoreCase("Ticket")){
            e.setCancelled(true);
            if(e.getClickedInventory().getItem(slot).hasItemMeta() && e.getClickedInventory().getItem(slot).getItemMeta().getLore().toArray().length > 1){
                String command = e.getClickedInventory().getItem(slot).getItemMeta().getLore().get(1);
                if(command.equalsIgnoreCase("Open")){listGuis.listOpenGui(p);}
                else if(command.equalsIgnoreCase("Closed")){listGuis.listClosedGui(p);}
                else if(command.equalsIgnoreCase("Assigned")){listGuis.listAssignedGui(p);}
                else if(command.equalsIgnoreCase("Unassigned")){listGuis.listUnassignedGui(p);}
                else if(command.equalsIgnoreCase("Builder")){listGuis.builderListOpenGui(p);}

                else if (e.getClickedInventory().getItem(slot).getType() == Material.FLINT_AND_STEEL){
                    Bukkit.dispatchCommand(p,command);
                    command = e.getClickedInventory().getItem(26).getItemMeta().getLore().get(1);
                    if(command.equalsIgnoreCase("Open")){listGuis.listOpenGui(p);}
                    else if(command.equalsIgnoreCase("Closed")){listGuis.listClosedGui(p);}
                    else if(command.equalsIgnoreCase("Assigned")){listGuis.listAssignedGui(p);}
                    else if(command.equalsIgnoreCase("Unassigned")){listGuis.listUnassignedGui(p);}
                    else if(command.equalsIgnoreCase("Builder")){listGuis.builderListOpenGui(p);}

                }
                else if (e.getClickedInventory().getItem(slot).getType() != Material.BOOK){
                    Bukkit.dispatchCommand(p, command);}
                else if (e.getClickedInventory().getItem(slot).getType() == Material.BOOK){
                    if(p.hasPermission("tbtickets.admin")){
                        command = "tbticketadmin view " + name.split(" - ")[1];
                        Bukkit.dispatchCommand(p, command);
                    }
                    else if(p.hasPermission("tbtickets.view.any")){
                        command = "tbta view " + name.split(" - ")[1];
                        Bukkit.dispatchCommand(p, command);
                    }
                    else if(p.hasPermission("tbtickets.builder")){
                        command = "review view " + name.split(" - ")[1];
                        Bukkit.dispatchCommand(p, command);
                    }
                }

            }
        }
    }
}
