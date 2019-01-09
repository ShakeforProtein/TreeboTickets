package me.shakeforprotein.treebotickets.Methods.CreateTicket;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTicket {

    private TreeboTickets pl;

    public CreateTicket(TreeboTickets main){this.pl = main;}

    public void addTicketToDB(Player p, String ticketData) {
        try {
            String output = "" + pl.connection.createStatement().executeUpdate(pl.baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
            ResultSet response = pl.connection.createStatement().executeQuery("SELECT * FROM `tickets`WHERE `IGNAME`=\"" + p.getName() + "\" ORDER BY id DESC LIMIT 1");
            int tID = 0;
            while (response.next()) {
                tID = response.getInt("ID");
                p.sendMessage("Your Ticket number is " + tID + ". Use /ticket view " + tID + " to view any updates");
            }
            p.sendMessage(ChatColor.GREEN + "Your ticket has been successfully submitted");
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during addTicketToDB()");
        }
    }
}
