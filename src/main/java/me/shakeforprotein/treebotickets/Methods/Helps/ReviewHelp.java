package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReviewHelp {

    private TreeboTickets pl;
    public ReviewHelp(TreeboTickets main){this.pl = main;}

    public void reviewHelp(Player p) {
        p.sendMessage(pl.err + "Incorrect usage. Please try one of the following.");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review -  Request a review");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review close <review_number> - Closes the review ticket with the review number if it is your own");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review view <review_number>  -  Shows the detail of one your ticket with the listed ticket_number");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review list  -  List all tickets waiting for review");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review update <review_number>  -  Allows user to update own ticket");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review tp - Teleport to a review site.");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/review gui - Open the reviews gui");
    }
}
