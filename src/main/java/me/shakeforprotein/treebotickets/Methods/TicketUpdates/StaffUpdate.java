package me.shakeforprotein.treebotickets.Methods.TicketUpdates;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class StaffUpdate {

    private TreeboTickets pl;

    public StaffUpdate(TreeboTickets main) {
        this.pl = main;
    }

    public void staffUpdate(Player p, int t, String staffText) {
        if (p.hasPermission("tbtickets.view.any")) {
            if (staffText != "" && staffText != null) {
                String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                ResultSet response;
                int response2 = 0;
                try {
                    response = pl.connection.createStatement().executeQuery(query);
                    while (response.next()) {
                        int tId = response.getInt("ID");
                        String tStaff = response.getString("STAFF");
                        String tStaffSteps = response.getString("STAFFSTEPS");
                        String newStaffSteps = (tStaffSteps + "\n" + LocalDateTime.now() + " - " + p.getName() + " - " + staffText);
                        if (tStaff.equalsIgnoreCase(p.getName())) {
                            String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + "' WHERE  `ID` =" + tId);
                            try {
                                pl.connection.createStatement().executeUpdate(query2);
                                p.sendMessage("Ticket " + t + " updated.");
                            } catch (SQLException e) {
                                p.sendMessage(ChatColor.RED + "Something went wrong");
                                System.out.println("Encountered " + e.toString() + " during staffUpdate()");
                                pl.makeLog(e);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "That ticket is assigned to " + tStaff + " so you are not able to update it.");
                        }
                    }
                } catch (SQLException e) {
                    p.sendMessage(ChatColor.RED + "Something went wrong");
                    System.out.println("Encountered " + e.toString() + " during staffUpdate()");
                    pl.makeLog(e);
                }
            }
        } else {
            p.sendMessage("You don't the required permissions to run this command.");
        }
    }
}
