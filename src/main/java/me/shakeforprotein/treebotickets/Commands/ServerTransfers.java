package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import me.shakeforprotein.treebotickets.Methods.Teleports.ToWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ServerTransfers implements CommandExecutor {


    private TreeboTickets pl;
    private ToWorld toWorld;

    public ServerTransfers(TreeboTickets main) {
        pl = main;
        this.toWorld = new ToWorld(pl);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p;
        p = (Player) sender;

       /* if (args.length == 0) {
            if (sender instanceof Player) {
                p = (Player) sender;
            }
            else {
                sender.sendMessage("You are not being recognized as a player");
            }
        }
        else if (args.length == 1){
            //TODO: Make these commands executable on other players by an admin when their name is used as the first argument.
            sender.sendMessage("Place holder function");

        }*/
        if (cmd.getName().equalsIgnoreCase("survival")) {
            toWorld.toWorld("survival", "Survival", p);
        } else if (cmd.getName().equalsIgnoreCase("lobby")) {
            toWorld.toWorld("hub", "hub", p);
        } else if (cmd.getName().equalsIgnoreCase("creative")) {
            toWorld.toWorld("creative", "Creative", p);
        } else if (cmd.getName().equalsIgnoreCase("plots")) {
            toWorld.toWorld("creative", "Plots", p);
        } else if (cmd.getName().equalsIgnoreCase("comp")) {
            toWorld.toWorld("creative", "Comp", p);
        } else if (cmd.getName().equalsIgnoreCase("skyhub")) {
            toWorld.toWorld("sky", "Skyhub", p);
        } else if (cmd.getName().equalsIgnoreCase("skyblock")) {
            toWorld.toWorld("sky", "BSkyBlock_world", p);
        } else if (cmd.getName().equalsIgnoreCase("skygrid")) {
            toWorld.toWorld("sky2", "Skygrid-world", p);
        } else if (cmd.getName().equalsIgnoreCase("acidislands")) {
            toWorld.toWorld("sky", "AcidIsland_world", p);
        } else if (cmd.getName().equalsIgnoreCase("acidislands")) {
            toWorld.toWorld("sky", "AcidIsland_world", p);
        } else if (cmd.getName().equalsIgnoreCase("caveblock")) {
            toWorld.toWorld("sky", "CaveBlock_world", p);
        } else if (cmd.getName().equalsIgnoreCase("hardcore")) {
            toWorld.toWorld("hardcore", "Hardcore", p);
        } else if (cmd.getName().equalsIgnoreCase("games")) {
            toWorld.toWorld("games", "Games", p);
        } else if (cmd.getName().equalsIgnoreCase("prison")) {
            toWorld.toWorld("prison", "Prison", p);
        }
        return true;
    }
}
