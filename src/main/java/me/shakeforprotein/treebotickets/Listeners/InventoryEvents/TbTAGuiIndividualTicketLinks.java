package me.shakeforprotein.treebotickets.Listeners.InventoryEvents;

import me.shakeforprotein.treebotickets.Methods.CreateTicket.CreateTicket;
import me.shakeforprotein.treebotickets.Methods.Guis.ListGuis;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class TbTAGuiIndividualTicketLinks implements Listener {
    private TreeboTickets pl;
    private ListGuis listGuis;
    private CreateTicket createTicket;

    public TbTAGuiIndividualTicketLinks(TreeboTickets main) {
        pl = main;
        this.listGuis = new ListGuis(main);
        this.createTicket = new CreateTicket(main);
    }


    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String name = e.getView().getTitle();
        int slot = e.getSlot();
        /*if (slot < 0) {
            p.closeInventory();
            return;
        }*/


        if (name != null && name.split(" - ")[0].equalsIgnoreCase("Ticket")){
            e.setCancelled(true);
            if(e.getClickedInventory().getItem(slot).getType() == Material.PLAYER_HEAD && e.getClickedInventory().getItem(slot).hasItemMeta() && e.getClickedInventory().getItem(slot).getItemMeta().hasDisplayName() && e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase(pl.getConfig().getItemStack("CustomHeads.Discord").getItemMeta().getDisplayName())){
                List<String> lore = e.getClickedInventory().getItem(slot).getItemMeta().getLore();
                String id = lore.get(0);
                String type = lore.get(1);
                String player = lore.get(2);
                String contents = lore.get(3);
                String server = lore.get(4);

                try {
                    String discordHook = pl.getConfig().getString("discordHook");
                    String p1 = "id=" + URLEncoder.encode(id, "UTF-8");
                    String p2 = "&user=" + URLEncoder.encode(player, "UTF-8");
                    String p3 = "&server=" + URLEncoder.encode(server, "UTF-8");
                    String p4 = "&type=" + URLEncoder.encode(type, "UTF-8");
                    String p5 = "&contents=" + URLEncoder.encode(contents, "UTF-8");

                    String urlParameters = p1 + p2 + p3 + p4 + p5;
                    //URL url = new URL(null, discordHook, new sun.net.www.protocol.https.Handler());
                    //int    postDataLength = postData.length;

                    createTicket.sendGet(discordHook, urlParameters);
                    p.sendMessage(pl.badge + "Ticket " + id + " - ReSubmitted to discord.");
                }
                catch(java.lang.Exception ex){
                    p.sendMessage(pl.err + "Something went wrong");
                }
            }
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

                else if (e.getClickedInventory().getItem(slot).getType() == Material.INK_SAC){
                    Bukkit.dispatchCommand(console, command);
                }

                else if (e.getClickedInventory().getItem(slot).getType() != Material.BOOK){
                    Bukkit.dispatchCommand(p, command);}

                else if (e.getClickedInventory().getItem(slot).getType() == Material.BOOK){
                    if(p.hasPermission("tbtickets.admin")){
                        command = "tbticketadmin view " + name.split(" - ")[1];
                        Bukkit.dispatchCommand(p, command);
                    }
                    else if(p.hasPermission("tbtickets.mod.view")){
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
