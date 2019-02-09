package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Discord implements CommandExecutor {

    private TreeboTickets plugin;

    public Discord(TreeboTickets main){
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("discord")){
            String command = "discordsrv:discord link";
            Bukkit.dispatchCommand(sender,command);
        }
        return true;
    }

}
