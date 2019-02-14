package me.shakeforprotein.treebotickets.Methods.TicketStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuilderStats {

    private TreeboTickets pl;
    public BuilderStats(TreeboTickets main){this.pl = main;}

    public void builderStats(Player p) {
        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= 'Builders'");
            while (response.next()) {
                p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                p.sendMessage("TOTAL Review tickets: " + response.getInt("TOTAL"));
            }
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= 'Builders' AND STATUS='OPEN'");
            while (response.next()) {
                p.sendMessage("OPEN ReviewTickets: " + response.getInt("TOTAL"));
            }
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= 'Builders' AND STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage("CLOSED review Tickets: " + response.getInt("TOTAL"));
            }
        } catch (SQLException e) {
            // p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during BuilderStats()");
            pl.makeLog(e);
        }
    }
}
