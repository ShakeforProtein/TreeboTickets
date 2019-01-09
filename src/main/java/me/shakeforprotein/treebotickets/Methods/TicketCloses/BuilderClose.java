package me.shakeforprotein.treebotickets.Methods.TicketCloses;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuilderClose {

    private TreeboTickets pl;
    public BuilderClose(TreeboTickets main){this.pl = main;}

    public void builderClose(Player p, String t) {
        if (p.hasPermission("tbtickets.builder")) {
            int tId = -1;
            String tPlayer = "";

            ResultSet response;
            try {
                response = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    String tstaff = response.getString("STAFF");
                    if (tstaff.equalsIgnoreCase("Builders")) {
                        tId = response.getInt("ID");
                        pl.connection.createStatement().executeUpdate("UPDATE `" + pl.table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                        p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffCloseTicket()");
                pl.makeLog(e);
            }
        }
    }
}
