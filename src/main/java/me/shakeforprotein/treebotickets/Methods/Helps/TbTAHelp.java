package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TbTAHelp {

    private TreeboTickets pl;

    public TbTAHelp(TreeboTickets main){this.pl = main;}

    public void tbtaHelp(Player p) {
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
        p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
        p.sendMessage(ChatColor.GOLD + "/tbta list <assigned|unnassigned|open|closed|idea>  -  Lists all tickets assigned to you");
        p.sendMessage(ChatColor.GOLD + "/tbta view <ticket_number>  -  Displays details on specific ticket");
        p.sendMessage(ChatColor.GOLD + "/tbta close <ticket_number>  -  Close ticket with id");
        p.sendMessage(ChatColor.GOLD + "/tbta <claim|unclaim> <ticket_number>  -  Assigns an unassigned ticket to yourself");
        p.sendMessage(ChatColor.GOLD + "/tbta tp <ticket_number>  -  Teleport to location of ticket number");
        p.sendMessage(ChatColor.GOLD + "/tbta update <ticket_number>  <your message> -  Updates a tickets staff steps data.  Remember this can be seen by the ticket submitter");
    }
}
