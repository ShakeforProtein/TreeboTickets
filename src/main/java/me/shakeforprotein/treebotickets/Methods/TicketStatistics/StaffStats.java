package me.shakeforprotein.treebotickets.Methods.TicketStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffStats {

    private TreeboTickets pl;
    public StaffStats(TreeboTickets main){this.pl = main;}

    public void staffStats(Player p) {

        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= '" + p.getName() + "'");
            while (response.next()) {
                if (!p.hasPermission("tbtickets.admin")) {
                    p.sendMessage((pl.badge + "XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                }
                /*p.sendMessage(ChatColor.UNDERLINE + "Your" + ChatColor.RESET + " TOTAL assigned tickets: " + response.getInt("TOTAL"));
                 */
            }
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= '" + p.getName() + "' AND STATUS='OPEN'");
            while (response.next()) {
                p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.UNDERLINE + "Your" + ChatColor.RESET + ChatColor.RED + " OPEN" + ChatColor.RESET + " Tickets: " + response.getInt("TOTAL"));
            }
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= '" + p.getName() + "' AND STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage(ChatColor.UNDERLINE + "Your" + ChatColor.RESET + ChatColor.GREEN + " CLOSED" + ChatColor.RESET + " Tickets: " + response.getInt("TOTAL"));
            }*/
        } catch (SQLException e) {
            // p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println(pl.err + "Encountered " + e.toString() + " during staffStats()");
            pl.makeLog(e);
        }
    }
}
