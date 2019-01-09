package me.shakeforprotein.treebotickets.Methods.TicketCloses;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerClose {

    private TreeboTickets pl;
    public PlayerClose(TreeboTickets main){this.pl = main;}

    public void closeTicket(Player p, Integer t) {
        int tId = -1;
        String tPlayer = "";

        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()) {
                tId = response.getInt("ID");
                tPlayer = response.getString("IGNAME");
                if (tPlayer.trim().equalsIgnoreCase(p.getName().trim())) {
                    pl.connection.createStatement().executeUpdate("UPDATE `" + pl.table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                    p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");
                } else {
                    p.sendMessage(ChatColor.RED + "This is not your ticket to close");
                }
            }
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during closeTicket()");
            pl.makeLog(e);
        }
    }
}
