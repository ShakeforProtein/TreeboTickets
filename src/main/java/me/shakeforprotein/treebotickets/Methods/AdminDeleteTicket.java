package me.shakeforprotein.treebotickets.Methods;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class AdminDeleteTicket {

    private TreeboTickets pl;
    public AdminDeleteTicket(TreeboTickets main){this.pl = main;}

    public void adminDeleteTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.admin")) {
            int tId = -1;
            String tPlayer = "";

            int response;
            try {response = pl.connection.createStatement().executeUpdate("UPDATE `" + pl.getConfig().getString("table") + "` SET `STAFF` = 'DELETED', `STATUS` = 'CLOSED' WHERE ID='" + t + "'");
                p.sendMessage("Ticket " + t + " deleted. This action can be manually undone.");
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during adminDeleteTicket()");
                pl.makeLog(e);
            }
        } else {
            p.sendMessage("You are not a ticket administrator");
        }
    }
}
