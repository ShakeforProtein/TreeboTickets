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


    private void noPerms(Player p) {
        p.sendMessage(pl.err + "You lack the required permissions to run this command");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            pl.getConfig().set("players." + p.getName() + ".type", "other");
/*
            //if no arguments treate like "/tbticket open"
            if (args.length == 0) {
                args[0] = "open";
            }

            //capture command
            StringBuilder argsText = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                argsText.append(args[i] + " ");
            }
            pl.getConfig().set("players." + p.getName() + ".actualCommand", cmd.getName() + " " + argsText);



            //Open a new ticket
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("open") && p.hasPermission("tbtickets.player.create")) {
                    startTicketLogic.startTicketLogic(p);
                } else if (args[0].equalsIgnoreCase("list")) {
                    String fullData = playerListOwn.listTickets(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "' ORDER BY id DESC");
                } else {
                    tbTicketHelp.tbTicketHelp(p);
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
                        playerViewTicket.getTicket(p, ticketNumber);
                    } else if (args[0].equalsIgnoreCase("close")) {
                        playerClose.closeTicket(p, ticketNumber);
                    }
                }

            } else if (args.length >= 3 && args[0].equalsIgnoreCase("update")) {
                StringBuilder playerText = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    playerText.append(args[i] + " ");
                }

                playerUpdate.playerUpdate(p, Integer.parseInt(args[1]), playerText.toString());
            } else {
                tbTicketHelp.tbTicketHelp(p);
            }
        }
    */
            //if no arguments treate like "/tbticket open"
            if (args.length == 0) {
                args = new String[1];
                args[0] = "open";
            }


            //capture command
            StringBuilder argsText = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                argsText.append(args[i] + " ");
            }
            pl.getConfig().set("players." + p.getName() + ".actualCommand", cmd.getName() + " " + argsText);


            if (args[0].equalsIgnoreCase("open")){
                startTicketLogic.startTicketLogic(p);
            }
            else if (args[0].equalsIgnoreCase("close")){
                if(args.length == 2 && isNumeric(args[1])){
                    playerClose.closeTicket(p, Integer.parseInt(args[1]));
                }
                else {
                    p.sendMessage(pl.err + "Please specify a ticket number as your second argument. Ie. /ticket " + args[0] + " 123");
                }
            }
            else if (args[0].equalsIgnoreCase("list")){
                    if(args.length == 1){
                        String fullData = playerListOwn.listTickets(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "' ORDER BY id DESC");
                    }
                    else{
                        tbTicketHelp.tbTicketHelp(p);
                    }
            }
            else if (args[0].equalsIgnoreCase("view")){
                if(args.length == 2 && isNumeric(args[1])){
                    playerViewTicket.getTicket(p, Integer.parseInt(args[1]));
                }
                else {
                    tbTicketHelp.tbTicketHelp(p);
                    p.sendMessage(pl.err + "Please specify a ticket number as your second argument. Ie. /ticket " + args[0] + " 123");
                }
            }
            else if (args[0].equalsIgnoreCase("update")){
                if(args.length >= 3 && isNumeric(args[1])){
                }
                else {
                    tbTicketHelp.tbTicketHelp(p);
                    p.sendMessage(pl.err + "Please specify a ticket number as your second argument. Ie. /ticket " + args[0] + " 123 Yes it works now");
                }
            }
            else{
                tbTicketHelp.tbTicketHelp(p);
                p.sendMessage(pl.badge + "Starting new ticket. Enter cancel into chat to cancel anytime.");
                startTicketLogic.startTicketLogic(p);
            }
        }
        else{
            sender.sendMessage(pl.err + "These commands can only be run as a player");
        }
        return true;

    }
}

