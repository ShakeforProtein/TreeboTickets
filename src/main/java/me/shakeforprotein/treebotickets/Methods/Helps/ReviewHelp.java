package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReviewHelp {

    private TreeboTickets pl;
    public ReviewHelp(TreeboTickets main){this.pl = main;}

    public void reviewHelp(Player p) {
        p.sendMessage(ChatColor.RED + "Incorrect usage. Please try one of the following.");
        p.sendMessage(ChatColor.GOLD + "/review -  Request a review");
        p.sendMessage(ChatColor.GOLD + "/review close <review_number> - Closes the review ticket with the review number if it is your own");
        p.sendMessage(ChatColor.GOLD + "/review view <review_number>  -  Shows the detail of one your ticket with the listed ticket_number");
        p.sendMessage(ChatColor.GOLD + "/review list  -  List all tickets waiting for review");
        p.sendMessage(ChatColor.GOLD + "/review update <review_number>  -  Allows user to update own ticket");
        p.sendMessage(ChatColor.GOLD + "/review tp - Teleport to a review site.");
        p.sendMessage(ChatColor.GOLD + "/review gui - Open the reviews gui");
    }
}
