package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.CreateTicket.StartTicket;
import me.shakeforprotein.treebotickets.Methods.Helps.TbTicketsHelp;
import me.shakeforprotein.treebotickets.Methods.Lists.PlayerListOwn;
import me.shakeforprotein.treebotickets.Methods.TicketCloses.PlayerClose;
import me.shakeforprotein.treebotickets.Methods.TicketUpdates.PlayerUpdate;
import me.shakeforprotein.treebotickets.Methods.ViewTickets.PlayerViewTicket;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.shakeforprotein.treebotickets.Commands.Commands.isNumeric;

public class TbTicket implements CommandExecutor {

    private TreeboTickets pl;
    private TbTicketsHelp tbTicketHelp;
    private StartTicket startTicketLogic;
    private PlayerListOwn playerListOwn;
    private PlayerClose playerClose;
    private PlayerViewTicket playerViewTicket;
    private PlayerUpdate playerUpdate;


    public TbTicket(TreeboTickets main) {
        pl = main;
        this.tbTicketHelp = new TbTicketsHelp(main);
        this.startTicketLogic = new StartTicket(main);
        this.playerListOwn = new PlayerListOwn(main);
        this.playerClose = new PlayerClose(main);
        this.playerViewTicket = new PlayerViewTicket(main);
        this.playerUpdate = new PlayerUpdate(main);
    }


    private void noPerms(Player p){
        p.sendMessage(ChatColor.RED + "You lack the required permissions to run this command");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;


            if (cmd.getName().equalsIgnoreCase("tbTicket")) {

                if (args.length == 0) {
                    tbTicketHelp.tbTicketHelp(p);
                    startTicketLogic.startTicketLogic(p);

                }

                else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("open") && p.hasPermission("tbtickets.create")) {
                        startTicketLogic.startTicketLogic(p);
                    }
                    else if (args[0].equalsIgnoreCase("open") && !p.hasPermission("tbtickets.create")) {
                        noPerms(p);
                    }

                    //TBTICKET COMMAND - LIST OWN Logic
                    else if (args[0].equalsIgnoreCase("list") && (p.hasPermission("tbtickets.view.own"))) {
                        String fullData = playerListOwn.listTickets(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "' ORDER BY id DESC");
                    } else if (args[0].equalsIgnoreCase("list") && !p.hasPermission("tbtickets.view.own")) {
                        p.sendMessage(ChatColor.RED + "You lack the required permission to list tickets");
                    }

                    //TBTICKET COMMAND - INCOMPLETE COMMANDS
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket view 274");
                    } else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket close 274");
                    }

                    //TBTICKET COMMAND - NO PERMISSIONS
                    else if (args[0].equalsIgnoreCase("view")  && !p.hasPermission("tbtickets.view.own")) {
                        noPerms(p);
                    } else if (args[0].equalsIgnoreCase("close") && !p.hasPermission("tbtickets.close.own")) {
                        noPerms(p);
                    }

                } else if (args.length == 2) {
                    // Make sure second argumentt is a valid integer.
                    Integer ticketNumber = -1;
                    if (isNumeric(args[1])) {
                        ticketNumber = Integer.parseInt(args[1]);
                    }

                    if (ticketNumber != -1) {
                        //TBTICKET COMMAND - VIEW SPECIFIC TICKET LOGIC
                        if (args[0].equalsIgnoreCase("view") && !p.hasPermission("tbtickets.view.own")) {
                            noPerms(p);
                        } else if (args[0].equalsIgnoreCase("close") && !p.hasPermission("tbtickets.close.own")) {
                            noPerms(p);
                        }

                        else if (args[0].equalsIgnoreCase("view")) {
                            playerViewTicket.getTicket(p, ticketNumber);
                        } else if (args[0].equalsIgnoreCase("close")) {
                            playerClose.closeTicket(p, ticketNumber);
                        }
                    }
                    else if (args.length >= 3 && args[0].equalsIgnoreCase("update")) {
                        StringBuilder playerText = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            playerText.append(args[i] + " ");
                        }

                        playerUpdate.playerUpdate(p, Integer.parseInt(args[1]), playerText.toString());
                    }
                } else {
                    tbTicketHelp.tbTicketHelp(p);
                }
            }
        }
        return true;
    }
}
