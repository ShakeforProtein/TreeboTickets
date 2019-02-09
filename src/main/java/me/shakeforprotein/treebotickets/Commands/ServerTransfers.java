package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.Methods.Teleports.ToWorld;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ServerTransfers implements CommandExecutor {


    private TreeboTickets pl;
    private ToWorld toWorld;

    public ServerTransfers(TreeboTickets main) {
        pl = main;
        this.toWorld = new ToWorld(main);
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
            toWorld.toWorld("survival", "survival", p);
        } else if (cmd.getName().equalsIgnoreCase("lobby")) {
            toWorld.toWorld("hub", "hub", p);
        } else if (cmd.getName().equalsIgnoreCase("creative")) {
            toWorld.toWorld("creative", "creative", p);
        } else if (cmd.getName().equalsIgnoreCase("plots")) {
            toWorld.toWorld("creative", "plots", p);
        } else if (cmd.getName().equalsIgnoreCase("comp")) {
            toWorld.toWorld("creative", "comp", p);
        } else if (cmd.getName().equalsIgnoreCase("skyblock")) {
            toWorld.toWorld("sky", "BSkyblock_world", p);
        } else if (cmd.getName().equalsIgnoreCase("skygrid")) {
            toWorld.toWorld("sky", "Skygrid_world", p);
        } else if (cmd.getName().equalsIgnoreCase("acidislands")) {
            toWorld.toWorld("sky", "AcidIsland_world", p);
        } else if (cmd.getName().equalsIgnoreCase("caveblock")) {
            toWorld.toWorld("sky", "CaveBlock_world", p);
        } else if (cmd.getName().equalsIgnoreCase("hardcore")) {
            toWorld.toWorld("hardcore", "hardcore", p);
        } else if (cmd.getName().equalsIgnoreCase("games")) {
            toWorld.toWorld("games", "games", p);
        } else if (cmd.getName().equalsIgnoreCase("prison")) {
            toWorld.toWorld("prison", "prison", p);
        } else if (cmd.getName().equalsIgnoreCase("test")) {
            toWorld.toWorld("test", "test_the_end", p);
        }
        return true;
    }
}
