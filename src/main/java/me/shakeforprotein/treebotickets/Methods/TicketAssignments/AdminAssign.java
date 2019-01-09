package me.shakeforprotein.treebotickets.Methods.TicketAssignments;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAssign {

    private TreeboTickets pl;
    public AdminAssign(TreeboTickets main){this.pl = main;}

    public void adminAssign(Player p, int t, String staffName) {
        if (p.hasPermission("tbtickets.admin")) {
            String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            int response2 = 0;
            try {
                response = pl.connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    if (1 == 1) {
                        String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFF` =  '" + staffName + "' WHERE  `ID` =" + tId);
                        try {
                            pl.connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " assigned to " + staffName + ".");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during adminAssign()");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That ticket is already assigned to " + tStaff);
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during adminAssign()");
                pl.makeLog(e);
            }
        }
    }
}
