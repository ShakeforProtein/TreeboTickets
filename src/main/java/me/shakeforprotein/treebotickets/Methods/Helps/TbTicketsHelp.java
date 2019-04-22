package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TbTicketsHelp {

    private TreeboTickets pl;
    public TbTicketsHelp(TreeboTickets main){this.pl = main;}

    public void tbTicketHelp(Player p) {
        p.sendMessage(pl.err + "Incorrect usage. Please try one of the following.");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbticket open  -  Creates a new ticket");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbticket close <ticket_number> - Closes the ticket with the ticket number if it is your own");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbticket view <ticket_number>  -  Shows the detail of one your ticket with the listed ticket_number");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbticket list  -  Lists all tickets you've created");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbticket update <ticket_number>  -  Allows user to update own ticket");
    }
}
