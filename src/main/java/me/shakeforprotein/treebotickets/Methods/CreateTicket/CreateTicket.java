package me.shakeforprotein.treebotickets.Methods.CreateTicket;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateTicket {

    private TreeboTickets pl;

    public CreateTicket(TreeboTickets main){this.pl = main;}

    public void addTicketToDB(Player p, String ticketData) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {

        try {
            String output = "" + pl.connection.createStatement().executeUpdate(pl.baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
            ResultSet response = pl.connection.createStatement().executeQuery("SELECT * FROM `tickets`WHERE `IGNAME`=\"" + p.getName() + "\" ORDER BY id DESC LIMIT 1");
            ResultSet staffResponse = pl.connection.createStatement().executeQuery("SELECT * FROM `tickets_stafflist` WHERE 1 = 1");
            int tID = 0;
            while (response.next()) {
                tID = response.getInt("ID");
                p.sendMessage(pl.badge + "Your Ticket number is " + tID + ". Use /ticket view " + tID + " to view any updates");
                while (staffResponse.next()){
                    String staff = staffResponse.getString("IGNAME");
                    pl.api.sendMessage(staff, "Player " + p.getName() + "Has just submitted ticket number " + tID);
                }
            }
            p.sendMessage(pl.badge + "Your ticket has been successfully submitted");
        } catch (SQLException e) {
            p.sendMessage(pl.err + "Something went wrong while sending your ticket to the database");
            pl.makeLog(e);
            System.out.println(pl.err + "Encountered " + e.toString() + " during addTicketToDB()");
        }

            }
        });
    }
}
