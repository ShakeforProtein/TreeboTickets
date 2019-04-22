package me.shakeforprotein.treebotickets.Methods.TicketUpdates;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PlayerUpdate {

    private TreeboTickets pl;

    public PlayerUpdate(TreeboTickets main) {
        this.pl = main;
    }

    public void playerUpdate(Player p, int t, String playerText) {
        if (p.hasPermission("tbtickets.player.view")) {
            if (playerText != "" && playerText != null) {
                String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                ResultSet response;
                int response2 = 0;
                try {
                    response = pl.connection.createStatement().executeQuery(query);
                    while (response.next()) {
                        int tId = response.getInt("ID");
                        String tPlayer = response.getString("IGNAME");
                        String tStaffSteps = response.getString("STAFFSTEPS");
                        String newStaffSteps = (tStaffSteps + "\n" + LocalDateTime.now() + " - " + p.getName() + "(Player) - " + playerText);
                        if (tPlayer.equalsIgnoreCase(p.getName())) {
                            String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + ", `ATTN` = 'Staff' WHERE  `ID` =" + tId);
                            try {
                                pl.connection.createStatement().executeUpdate(query2);
                                p.sendMessage(pl.badge + "Ticket " + t + " updated.");
                            } catch (SQLException e) {
                                p.sendMessage(pl.err + "Something went wrong");
                                System.out.println(pl.err + "Encountered " + e.toString() + " during playerUpdate()");
                                pl.makeLog(e);
                            }
                        } else {
                            p.sendMessage(pl.err + "Sorry " + p.getName() + " you do not own this ticket.");
                        }
                    }
                } catch (SQLException e) {
                    p.sendMessage(pl.err + "Something went wrong");
                    System.out.println(pl.err + "Encountered " + e.toString() + " during playerUpdate()");
                    pl.makeLog(e);
                }
            }
        } else {
            p.sendMessage(pl.err + "You don't have the required permissions to run this command.");
        }
    }
}
