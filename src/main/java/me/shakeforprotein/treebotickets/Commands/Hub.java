package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.Guis.OpenHubMenu;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Hub implements CommandExecutor {

    private TreeboTickets pl;
    private OpenHubMenu openHubMenu;

    public Hub(TreeboTickets main){
        this.pl = main;
        this.openHubMenu = new OpenHubMenu(pl);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();
            if (cmd.getName().equalsIgnoreCase("hub1")) {
                if (args.length == 0){
                    openHubMenu.openHubMenu((Player) sender);
                }
                else{
                    sender.sendMessage("The HUB command does not support additional arguments");
                }
            }
        }
        return true;
    }
}
