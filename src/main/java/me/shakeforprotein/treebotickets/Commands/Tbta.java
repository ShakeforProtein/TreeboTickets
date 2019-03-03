package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.Guis.OpenTicketGui;
import me.shakeforprotein.treebotickets.Methods.Helps.TbTAHelp;
import me.shakeforprotein.treebotickets.Methods.Lists.StaffList;
import me.shakeforprotein.treebotickets.Methods.Teleports.StaffTp;
import me.shakeforprotein.treebotickets.Methods.TicketAssignments.StaffClaim;
import me.shakeforprotein.treebotickets.Methods.TicketAssignments.StaffUnclaim;
import me.shakeforprotein.treebotickets.Methods.TicketCloses.StaffClose;
import me.shakeforprotein.treebotickets.Methods.TicketUpdates.StaffUpdate;
import me.shakeforprotein.treebotickets.Methods.ViewTickets.StaffViewTicket;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import static me.shakeforprotein.treebotickets.Commands.Commands.isNumeric;


public class Tbta implements CommandExecutor {

    private TreeboTickets pl;
    private OpenTicketGui openTicketGui;
    private StaffList staffList;
    private StaffClose staffClose;
    private StaffUnclaim staffUnclaim;
    private StaffClaim staffClaim;
    private StaffTp staffTp;
    private StaffUpdate staffUpdate;
    private StaffViewTicket staffViewTicket;
    private TbTAHelp tbTAHelp;

    public Tbta(TreeboTickets main) {
        pl = main;
        this.openTicketGui = new OpenTicketGui(main);
        this.staffList = new StaffList(main);
        this.staffClose = new StaffClose(main);
        this.staffUnclaim = new StaffUnclaim(main);
        this.staffClaim = new StaffClaim(main);
        this.staffTp = new StaffTp(main);
        this.staffUpdate = new StaffUpdate(main);
        this.staffViewTicket = new StaffViewTicket(main);
        this.tbTAHelp = new TbTAHelp(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();


            if (cmd.getName().equalsIgnoreCase("tbta")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
                    if (p.hasPermission("tbtickets.view.any")) {
                        openTicketGui.openTicketGui(p);
                    } else {
                        p.sendMessage("You lack the permissions required to use this command");
                    }
                } else if (args.length < 2) {
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
                    p.sendMessage(ChatColor.GOLD + "/tbta list <assigned|unnassigned|open|closed|idea>  -  Lists all tickets assigned to you");
                    p.sendMessage(ChatColor.GOLD + "/tbta view <ticket_number>  -  Displays details on specific ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbta <close|reopen> <ticket_number>  -  Close / Reopen ticket with id");
                    p.sendMessage(ChatColor.GOLD + "/tbta <claim|unclaim> <ticket_number>  -  Assigns an unassigned ticket to yourself");
                    p.sendMessage(ChatColor.GOLD + "/tbta tp <ticket_number>  -  Teleport to location of ticket number");
                    p.sendMessage(ChatColor.GOLD + "/tbta update <ticket_number>  <your message> -  Updates a tickets staff steps data.  Remember this can be seen by the ticket submitter");
                    p.sendMessage(ChatColor.GOLD + "/tbta gui");


                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list")) {
                        if (p.hasPermission("tbtickets.view.any")) {
                            if (args[1].equalsIgnoreCase("assigned")) {
                                staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='" + p.getName() + "' AND STATUS='OPEN' ORDER BY id DESC");
                            }
                         else if (args[1].equalsIgnoreCase("unassigned")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED' AND STATUS='OPEN' ORDER BY id DESC");
                        } else if (args[1].equalsIgnoreCase("idea")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE TYPE='Idea' AND STATUS='OPEN' ORDER BY id DESC");
                        } else if (args[1].equalsIgnoreCase("open")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN' ORDER BY id DESC");
                        } else if (args[1].equalsIgnoreCase("closed")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='CLOSED' ORDER BY id DESC");
                        }
                        }
                    } else if (args[0].equalsIgnoreCase("reopen") && p.hasPermission("tbtickets.view.any")) {
                        if (pl.isNumeric(args[1])) {
                            pl.staffReOpenTicket(p, Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please include a valid ticket number as your second argument");
                        }
                    } else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.any")) {
                        if (pl.isNumeric(args[1])) {
                            staffClose.staffCloseTicket(p, Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please include a valid ticket number as your second argument");
                        }
                    } else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.any")) {
                        if (pl.isNumeric(args[1])) {
                            staffViewTicket.staffViewTicket(p, Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please include a valid ticket number as your second argument");
                        }
                    } else if (args[0].equalsIgnoreCase("claim")) {
                        if (pl.isNumeric(args[1])) {
                            staffClaim.staffClaim(p, Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please include a valid ticket number as your second argument");
                        }
                    } else if (args[0].equalsIgnoreCase("unclaim")) {
                        if (pl.isNumeric(args[1])) {
                            staffUnclaim.staffUnclaim(p, Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please include a valid ticket number as your second argument");
                        }
                    } else if (args[0].equalsIgnoreCase("tp")) {
                        if (isNumeric(args[1])) {
                            staffTp.staffTP(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + args[1] + "' AND `STAFF`='" + p.getName() + "'", Integer.parseInt(args[1]));
                        }
                    }
                } else if (args.length >= 3 && args[0].equalsIgnoreCase("update")) {
                    StringBuilder staffText = new StringBuilder();

                    for (int i = 2; i < args.length; i++) {
                        staffText.append(args[i] + " ");
                    }

                    staffUpdate.staffUpdate(p, Integer.parseInt(args[1]), staffText.toString());
                } else {
                    tbTAHelp.tbtaHelp(p);
                }
            }
        }
        return true;
    }
}
