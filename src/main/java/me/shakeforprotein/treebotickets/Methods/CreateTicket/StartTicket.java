package me.shakeforprotein.treebotickets.Methods.CreateTicket;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StartTicket {

    private TreeboTickets pl;

    public StartTicket(TreeboTickets main){this.pl = main;}

    public void startTicketLogic(Player p) {
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
       /* p.sendMessage("Please enter");
        p.sendMessage(ChatColor.GOLD + "1 " + ChatColor.RESET + "-" + ChatColor.RED + " For Server related issues");
        p.sendMessage(ChatColor.GOLD + "2 " + ChatColor.RESET + "-" + ChatColor.RED + " For Griefer related issues");
        p.sendMessage(ChatColor.GOLD + "3 " + ChatColor.RESET + "-" + ChatColor.RED + " For Other issues");*/
        p.sendMessage("In your own words, please give a short explanation of the issue you are suffering");
        p.sendMessage("or enter 'cancel' at any time to stop creating a ticket");
        pl.getConfig().set("players." + p.getName() + ".ticketstate", 2);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
            public void run() {
                pl.getConfig().set("players." + p.getName() + ".ticketstate", 0);
            }
        }, 3000L);
        pl.saveConfig();
    }
}
