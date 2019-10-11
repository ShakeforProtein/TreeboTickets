package me.shakeforprotein.treebotickets.Methods.TicketAssignments;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffClaim {

    private TreeboTickets pl;

    public StaffClaim(TreeboTickets main) {
        this.pl = main;
    }

    public void staffClaim(Player p, int t) {
        if (p.hasPermission("tbtickets.mod.view")) {
            Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
                @Override
                public void run() {
                    String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                    ResultSet response;
                    int response2 = 0;
                    try {
                        response = pl.connection.createStatement().executeQuery(query);
                        while (response.next()) {
                            int tId = response.getInt("ID");
                            String tStaff = response.getString("STAFF");
                            if (tStaff.equalsIgnoreCase("unassigned")) {
                                String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFF` =  '" + p.getName() + "' WHERE  `ID` =" + tId);
                                try {
                                    pl.connection.createStatement().executeUpdate(query2);
                                    p.sendMessage(pl.badge + "Ticket " + t + " Claimed.");
                                } catch (SQLException e) {
                                    p.sendMessage(ChatColor.RED + "Something went wrong");
                                    System.out.println(pl.err + "Encountered " + e.toString() + " during staffClaim()");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "That ticket is already assigned to " + tStaff);
                            }
                        }
                    } catch (SQLException e) {
                        p.sendMessage(pl.err + "Something went wrong");
                        System.out.println(pl.err + "Encountered " + e.toString() + " during staffClaim()");
                        pl.makeLog(e);

                    }
                }
            });
        }
    }
}
