package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.Guis.OpenTicketGui;
import me.shakeforprotein.treebotickets.Methods.Helps.ReviewHelp;
import me.shakeforprotein.treebotickets.Methods.Lists.StaffList;
import me.shakeforprotein.treebotickets.Methods.TicketStatistics.BuilderStats;
import me.shakeforprotein.treebotickets.Methods.Teleports.BuilderTp;
import me.shakeforprotein.treebotickets.Methods.TicketCloses.BuilderClose;
import me.shakeforprotein.treebotickets.Methods.TicketUpdates.BuilderUpdate;
import me.shakeforprotein.treebotickets.Methods.ViewTickets.BuilderViewTicket;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Review implements CommandExecutor {

    private TreeboTickets pl;
    private StaffList staffList;
    private BuilderViewTicket builderViewTicket;
    private BuilderStats builderStats;
    private BuilderClose builderClose;
    private BuilderTp builderTp;
    private BuilderUpdate builderUpdate;
    private OpenTicketGui openTicketGui;
    private ReviewHelp reviewHelp;

    public Review(TreeboTickets main) {
        pl = main;
        this.staffList =new StaffList(main);
        this.builderViewTicket =  new BuilderViewTicket(main);
        this.builderStats = new BuilderStats(main);
        this.builderClose = new BuilderClose(main);
        this.builderTp = new BuilderTp(main);
        this.builderUpdate = new BuilderUpdate(main);
        this.openTicketGui = new OpenTicketGui(main);
        this.reviewHelp = new ReviewHelp(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();

            if (cmd.getName().equalsIgnoreCase("review")) {

                if(args.length > 0){
                    StringBuilder argsText = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        argsText.append(args[i] + " ");
                    }
                    pl.getConfig().set("players." + p.getName() + ".actualCommand", cmd.getName() + " " + argsText);
                }
                else{
                    pl.getConfig().set("players." + p.getName() + ".actualCommand", cmd.getName());
                }

                if (args.length == 0) {
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                    pl.getConfig().set("players." + p.getName() + ".type", "Review");
                    p.sendMessage("Please give a brief description of your build. You can cancel at any time by entering the word \"cancel\" into chat");
                }
                else if (args.length == 1 && p.hasPermission("tbtickets.builder")) {
                    if (args[0].equalsIgnoreCase("gui")) {
                        openTicketGui.openTicketGui(p);
                    } else if (args[0].equalsIgnoreCase("list")) {
                        staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='Builders' ORDER BY id DESC");
                    } else if (args[0].equalsIgnoreCase("stats")) {
                        builderStats.builderStats(p);
                    }
                }
                else if (args.length == 2 && p.hasPermission("tbtickets.builder")) {
                    if (args[0].equalsIgnoreCase("close")) {
                        if (pl.isNumeric(args[1])) {
                            builderClose.builderClose(p, args[1]);
                        } else {
                            p.sendMessage("Please enter a valid ticket number to close.");
                        }
                    } else if (args[0].equalsIgnoreCase("tp")) {
                        if (pl.isNumeric(args[1])) {
                            builderTp.builderTP(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF = 'Builders' AND ID='" + args[1] + "'", Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please enter a valid ticket number for Teleport");
                        }
                    } else if (args[0].equalsIgnoreCase("update")) {
                        if (pl.isNumeric(args[1])) {
                            StringBuilder staffText = new StringBuilder();
                            for (int i = 2; i < args.length; i++) {
                                staffText.append(args[i] + " ");
                            }
                            builderUpdate.builderUpdate(p, Integer.parseInt(args[1]), staffText.toString());
                        }
                    } else if (args[0].equalsIgnoreCase("view")) {
                        if (pl.isNumeric(args[1])) {
                            builderViewTicket.builderViewTicket(p, Integer.parseInt(args[1]));
                        } else {
                            p.sendMessage("Please enter a valid ticket number after the command");
                        }
                    }
                }
                else if(p.hasPermission("tbtickets.builder")){reviewHelp.reviewHelp(p);}
                else{
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                    pl.getConfig().set("players." + p.getName() + ".type", "Review");
                    p.sendMessage("Invalid input. Opening review request. Enter 'cancel' into chat at any time to cancel.");
                    p.sendMessage("Please give a brief description of your build. You can cancel at any time by entering the word \"cancel\" into chat");
                }
            }
        }
        return true;
    }
}
