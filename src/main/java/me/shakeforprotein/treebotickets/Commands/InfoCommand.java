package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.Helps.InfoHelp;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoCommand implements CommandExecutor {

    private TreeboTickets pl;
    private InfoHelp infoHelp;

    public InfoCommand(TreeboTickets main) {
        this.pl = main;
        this.infoHelp = new InfoHelp(main);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("tinfo")){
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "Please specify a topic to request information about.");
            }

            if(args.length == 1){
                infoHelp.infoHelp(args[0], sender);
            }
            else {sender.sendMessage(ChatColor.RED + "Too many Arguments");
            }
        }
        return true;
    }
}