package me.shakeforprotein.treebotickets;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Commands implements CommandExecutor {

    private TreeboTickets pl;
    private PlayerInput pi;

    public Commands(TreeboTickets main) {
        pl = main;
        this.pi = pi;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            //TBTICKET Logic
            if (cmd.getName().equalsIgnoreCase("tbTicket")) {
                // TBTICKET COMMAND - With no arguments
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Incorrect usage. Please try one of the following.");
                    p.sendMessage(ChatColor.GOLD + "/tbticket open  -  Creates a new ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbticket close <ticket_number> - Closes the ticket with the ticket number if it is your own");
                    p.sendMessage(ChatColor.GOLD + "/tbticket view <ticket_number>  -  Shows the detail of one your ticket with the listed ticket_number");
                    p.sendMessage(ChatColor.GOLD + "/tbticket list  -  Lists all tickets you've created");
                } else if (args.length == 1) {
                    // TBTICKET COMMAND - OPEN NEW TICKET LOGIC
                    if (args[0].equalsIgnoreCase("open") && p.hasPermission("tbtickets.create")) {
                        pl.startTicketLogic(p);
                    }
                    //TBTICKEWT COMMAND - LIST OWN Logic
                    else if (args[0].equalsIgnoreCase("list") && (p.hasPermission("tbtickets.view.own"))) {
                        String fullData = pl.listTickets(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "'");
                    }

                    //TBTICKET COMMAND - INCOMPLETE COMMANDS
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket view 274");
                    } else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket close 274");
                    }
                } else if (args.length == 2) {
                    // Make sure second argumentt is a valid integer.
                    Integer ticketNumber = -1;
                    if (isNumeric(args[1])) {
                        ticketNumber = Integer.parseInt(args[1]);
                    }

                    if (ticketNumber != -1) {
                        //TBTICKET COMMAND - VIEW SPECIFIC TICKET LOGIC
                        if (args[0].equalsIgnoreCase("view")) {
                            pl.getTicket(p, ticketNumber);
                        }
                        if (args[0].equalsIgnoreCase("close")) {
                            pl.closeTicket(p, ticketNumber);
                        }
                    }
                }
            }

            if (cmd.getName().equalsIgnoreCase("tbta")) {
                if (args.length < 2) {
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
                    p.sendMessage(ChatColor.GOLD + "/tbta list <assigned|unnassigned|open|closed>  -  Lists all tickets assigned to you");
                    p.sendMessage(ChatColor.GOLD + "/tbta view <ticket_number>  -  Displays details on specific ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbta close <ticket_number>  -  Close ticket with id");
                    p.sendMessage(ChatColor.GOLD + "/tbta  <ticket_number>  -  Displays details on ticket assigned to ");

                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list")){
                        if(args[1].equalsIgnoreCase("assigned")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='" + p.getName() + "'");
                        }
                        else if (args[1].equalsIgnoreCase("unassigned")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF=''");
                        }
                        else if (args[1].equalsIgnoreCase("open")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN'");
                        }
                        else if (args[1].equalsIgnoreCase("closed")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='CLOSED'");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.any")){
                        pl.staffCloseTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.any")){
                        pl.staffViewTicket(p, Integer.parseInt(args[1]));
                    }
                }
            }

        }
        return true;
    }


        public static boolean isNumeric (String str)
        {
            return str.matches("\\d+");
        }

    }