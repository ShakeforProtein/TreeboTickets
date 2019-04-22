package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TbTAHelp {

    private TreeboTickets pl;

    public TbTAHelp(TreeboTickets main){this.pl = main;}

    public void tbtaHelp(Player p) {
        p.sendMessage(pl.err + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbta list <assigned|unnassigned|open|closed|idea>  -  Lists all tickets assigned to you");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbta view <ticket_number>  -  Displays details on specific ticket");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbta close <ticket_number>  -  Close ticket with id");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbta <claim|unclaim> <ticket_number>  -  Assigns an unassigned ticket to yourself");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbta tp <ticket_number>  -  Teleport to location of ticket number");
        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "/tbta update <ticket_number>  <your message> -  Updates a tickets staff steps data.  Remember this can be seen by the ticket submitter");
    }
}
