package me.shakeforprotein.treebotickets.Methods;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class AdminDeleteTicket {

    private TreeboTickets pl;

    public AdminDeleteTicket(TreeboTickets main) {
        this.pl = main;
    }

    public void adminDeleteTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.admin")) {
            Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
                @Override
                public void run() {

                    int tId = -1;
                    String tPlayer = "";

                    int response;
                    try {
                        response = pl.connection.createStatement().executeUpdate("UPDATE `" + pl.getConfig().getString("table") + "` SET `STAFF` = 'DELETED', `STATUS` = 'CLOSED' WHERE ID='" + t + "'");
                        p.sendMessage(pl.badge + "Ticket " + t + " deleted. This action can be manually undone.");
                    } catch (SQLException e) {
                        p.sendMessage(pl.err + "Something went wrong");
                        System.out.println(pl.err + "Encountered " + e.toString() + " during adminDeleteTicket()");
                        pl.makeLog(e);
                    }
                }
            });
        } else {
            p.sendMessage(pl.err + "You are not a ticket administrator");
        }
    }
}
