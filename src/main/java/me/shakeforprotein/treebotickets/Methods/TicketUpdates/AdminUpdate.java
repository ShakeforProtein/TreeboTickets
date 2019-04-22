package me.shakeforprotein.treebotickets.Methods.TicketUpdates;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AdminUpdate {

    private TreeboTickets pl;

    public AdminUpdate(TreeboTickets main){this.pl = main;}

    public void adminUpdate(Player p, int t, String staffText) {
        if (p.hasPermission("tbtickets.admin")) {
            if(staffText != "" && staffText != null){
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
                    if (1 == 1) {
                        String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + "' WHERE  `ID` =" + tId);
                        try {
                            pl.connection.createStatement().executeUpdate(query2);
                            p.sendMessage(pl.badge + "Ticket " + t + " updated.");
                        } catch (SQLException e) {
                            p.sendMessage(pl.err + "Something went wrong");
                            System.out.println(pl.err + "Encountered " + e.toString() + " during staffClaim()");
                        }
                    } else {
                        p.sendMessage(pl.err + "That ticket is assigned to " + tStaff + " so you are not able to update it.");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(pl.err + "Something went wrong");
                System.out.println(pl.err + "Encountered " + e.toString() + " during adminUpdate()");
                pl.makeLog(e);
            }
        }
    }
        else{p.sendMessage(pl.err + "You do not have the required permissions to run this command");}
    }
}
