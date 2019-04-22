package me.shakeforprotein.treebotickets.Methods.TicketAssignments;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffUnclaim {

    private TreeboTickets pl;
    public StaffUnclaim(TreeboTickets main){this.pl = main;}

    public void staffUnclaim(Player p, int t) {
        if (p.hasPermission("tbtickets.mod.view")) {
            String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            try {
                response = pl.connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    if (tStaff.equalsIgnoreCase(p.getName())) {
                        String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFF` =  'UNASSIGNED' WHERE  `ID` =" + tId);
                        try {
                            pl.connection.createStatement().executeUpdate(query2);
                            p.sendMessage(pl.badge + "Ticket " + t + " unclaimed.");
                        } catch (SQLException e) {
                            p.sendMessage(pl.err + "Something went wrong");
                            System.out.println(pl.err + "Encountered " + e.toString() + " during staffUnclaim()");
                        }
                    } else {
                        p.sendMessage(pl.err + "That ticket is assigned to " + tStaff);
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(pl.err + "Something went wrong");
                System.out.println(pl.err + "Encountered " + e.toString() + " during staffUnclaim()");
                pl.makeLog(e);
            }
        }
    }

}
