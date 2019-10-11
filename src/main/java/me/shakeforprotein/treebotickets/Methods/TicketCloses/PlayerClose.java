package me.shakeforprotein.treebotickets.Methods.TicketCloses;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerClose {

    private TreeboTickets pl;

    public PlayerClose(TreeboTickets main) {
        this.pl = main;
    }

    public void closeTicket(Player p, Integer t) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {

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
                            p.sendMessage(pl.badge + "Ticket " + t + " Closed.");
                        } else {
                            p.sendMessage(pl.err + "This is not your ticket to close");
                        }
                    }
                } catch (SQLException e) {
                    p.sendMessage(pl.err + "Something went wrong");
                    System.out.println(pl.err + "Encountered " + e.toString() + " during closeTicket()");
                    pl.makeLog(e);
                }
            }
        });
    }
}
