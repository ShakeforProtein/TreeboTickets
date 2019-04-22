package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Discord implements CommandExecutor {

    private TreeboTickets plugin;

    public Discord(TreeboTickets main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discord")) {
            String commandString = "discordsrv:discord";
            if (args.length == 0) {
                commandString += " link";
            }
            else if (args.length == 1 && args[0].equalsIgnoreCase("unlink")){
                commandString += " unlink";
            }
            else if (args.length == 1 && args[0] == "default"){commandString += "";}
            else {
                for (String argString : args) {
                    commandString += argString + " ";
                }
            }

            Bukkit.dispatchCommand(sender, commandString);
        }

        return true;
    }
}
