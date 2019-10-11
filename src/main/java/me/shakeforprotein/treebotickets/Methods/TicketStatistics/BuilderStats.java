package me.shakeforprotein.treebotickets.Methods.TicketStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuilderStats {

    private TreeboTickets pl;
    public BuilderStats(TreeboTickets main){this.pl = main;}

    public void builderStats(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {

                ResultSet response;
                try {
                    response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= 'Builders'");
                    while (response.next()) {
                        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "TOTAL Review tickets: " + response.getInt("TOTAL"));
                    }
                    response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= 'Builders' AND STATUS='OPEN'");
                    while (response.next()) {
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "OPEN ReviewTickets: " + response.getInt("TOTAL"));
                    }
                    response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF= 'Builders' AND STATUS='CLOSED'");
                    while (response.next()) {
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "CLOSED review Tickets: " + response.getInt("TOTAL"));
                    }
                } catch (SQLException e) {
                    // p.sendMessage(ChatColor.RED + "Something went wrong");
                    System.out.println(pl.err + "Encountered " + e.toString() + " during BuilderStats()");
                    pl.makeLog(e);
                }
            }});
    }
}
