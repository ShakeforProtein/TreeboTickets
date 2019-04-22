package me.shakeforprotein.treebotickets.Methods;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class NotifyOnline {

    private TreeboTickets pl;

    public NotifyOnline(TreeboTickets main){this.pl = main;}


    public void notifyOnline(String perm, String ticketNumber, int pos, int total) {
        for (Player p : getServer().getOnlinePlayers()) {
            if (p.hasPermission("tbtickets." + perm)) {
                System.out.println(pl.badge  + "TicketDb cleanup in progress:  Ticket id - " + ticketNumber + " (" + pos + " of " + total + ") deleted.");
                p.sendMessage(pl.badge + "TicketDb cleanup in progress:  Ticket id - " + ticketNumber + " (" + pos + " of " + total + ") deleted.");
            }
        }
    }
}
