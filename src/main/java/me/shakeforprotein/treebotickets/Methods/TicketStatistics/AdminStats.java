package me.shakeforprotein.treebotickets.Methods.TicketStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminStats {

    private TreeboTickets pl;

    public AdminStats(TreeboTickets main){this.pl = main;}

    public void adminStats(Player p) {
        ResultSet response;
        try {
            p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE ID!='0'");
            while (response.next()) {
                p.sendMessage("Total Tickets:" + response.getInt("TOTAL"));
            }*/
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF!='UNASSIGNED'");
            while (response.next()) {
                p.sendMessage("Assigned Tickets:" + response.getInt("TOTAL"));
            }*/
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED'");
            while (response.next()) {
                p.sendMessage("UnAssigned Tickets:" + response.getInt("TOTAL"));
            }
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN'");
            while (response.next()) {
                p.sendMessage("Open Tickets:" + response.getInt("TOTAL"));
            }
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage("Closed Tickets:" + response.getInt("TOTAL"));
            }*/
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during AdminStats()");
            pl.makeLog(e);
        }
    }
}
