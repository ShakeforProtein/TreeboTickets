package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TbTicketAdminHelp {

    private TreeboTickets pl;

    public TbTicketAdminHelp(TreeboTickets main){this.pl = main;}

    public void tbTicketAdminHelp(Player p) {
    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
    p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin reload");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin list <assigned|unnassigned|open|closed> - Lists tickets of the selected type");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin staffList <staff_name> - Lists tickets assigned to particular staff member");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin assign <ticket_number> <staff_name> -  Displays details on specific ticket");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin close <ticket_number>  -  Close ticket with id");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin delete <ticket_number>  -  Assigns an unassigned ticket to yourself");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin update <ticket_number> <your update>  -  Updates the staff steps data for this ticket (This can be seen by anyone who can view the ticket)");
    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin stats");
}

}
