package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.Methods.Lists.StaffList;
import me.shakeforprotein.treebotickets.Methods.Stats.BuilderStats;
import me.shakeforprotein.treebotickets.Methods.Teleports.BuilderTp;
import me.shakeforprotein.treebotickets.Methods.TicketCloses.BuilderClose;
import me.shakeforprotein.treebotickets.Methods.TicketUpdates.BuilderUpdate;
import me.shakeforprotein.treebotickets.Methods.ViewTickets.BuilderViewTicket;
import me.shakeforprotein.treebotickets.TreeboTickets;
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

    public Review(TreeboTickets main) {
        pl = main;
        this.staffList =new StaffList(main);
        this.builderViewTicket =  new BuilderViewTicket(main);
        this.builderStats = new BuilderStats(main);
        this.builderClose = new BuilderClose(main);
        this.builderTp = new BuilderTp(main);
        this.builderUpdate = new BuilderUpdate(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();

            if (cmd.getName().equalsIgnoreCase("review")) {
                pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                pl.getConfig().set("players." + p.getName() + ".type", "Review");
                p.sendMessage("Please give a brief description of your build. You can cancel at any time by entering the word \"cancel\" into chat");
            }

            else if (cmd.getName().equalsIgnoreCase("reviewlist") && p.hasPermission("tbtickets.builder")){
                staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='Builders' ORDER BY id DESC");
            }
            else if (cmd.getName().equalsIgnoreCase("reviewview") && p.hasPermission("tbtickets.builder")){
                if(args.length > 0 && pl.isNumeric(args[0])){
                    builderViewTicket.builderViewTicket(p, Integer.parseInt(args[0]));}
                else {p.sendMessage("Please enter a valid ticket number after the command");}
            }
            else if (cmd.getName().equalsIgnoreCase("reviewstats") && p.hasPermission("tbtickets.builder")){
                builderStats.builderStats(p);
            }
            else if (cmd.getName().equalsIgnoreCase("reviewclose") && p.hasPermission("tbtickets.builder")){
                if(args.length > 0 && pl.isNumeric(args[0])){
                    builderClose.builderClose(p, args[0]);}
                else {p.sendMessage("Please enter a valid ticket number to close.");}
            }
            else if (cmd.getName().equalsIgnoreCase("reviewtp") && p.hasPermission("tbtickets.builder")){
                if(args.length > 0 && pl.isNumeric(args[0])){
                    builderTp.builderTP(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + args[0] + "'", Integer.parseInt(args[0]));
                }
                else {p.sendMessage("Please enter a valid ticket number for Teleport");}
            }
            else if (cmd.getName().equalsIgnoreCase("reviewupdate") && p.hasPermission("tbtickets.builder")){
                if(args.length > 0 && pl.isNumeric(args[0])){

                    StringBuilder staffText = new StringBuilder();
                    for(int i = 1; i < args.length; i++){
                        staffText.append(args[i] + " ");
                    }

                    builderUpdate.builderUpdate(p, Integer.parseInt(args[0]), staffText.toString());
                }
                else{p.sendMessage("Please include a valid ticket number to update as your first argument");}
            }
        }
        return true;
    }
}
