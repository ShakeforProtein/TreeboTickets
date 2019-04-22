package me.shakeforprotein.treebotickets.Methods.TicketCloses;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminClose {

    private TreeboTickets pl;
    public AdminClose(TreeboTickets main){this.pl = main;}

    public void adminCloseTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.admin")) {
            int tId = -1;
            String tPlayer = "";

            ResultSet response;
            try {
                response = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    tId = response.getInt("ID");
                    pl.connection.createStatement().executeUpdate("UPDATE `" + pl.table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                    p.sendMessage(pl.badge + "Ticket " + t + " Closed.");

                }
            } catch (SQLException e) {
                p.sendMessage(pl.err + "Something went wrong");
                System.out.println(pl.err + "Encountered " + e.toString() + " during adminCloseTicket()");
                pl.makeLog(e);
            }
        }
    }
}
