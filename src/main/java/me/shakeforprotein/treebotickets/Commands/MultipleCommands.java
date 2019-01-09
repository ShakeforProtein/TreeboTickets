package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MultipleCommands implements CommandExecutor {

    private TreeboTickets pl;

    public MultipleCommands(TreeboTickets main) {
        pl = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("multipleCommands")) {
            if (args.length > 0) {
                StringBuilder argsText = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    argsText.append(args[i] + " ");
                }
                String[] commandsArray = new String[argsText.toString().split("/").length];

                int positionCount = 0;
                for (String command : argsText.toString().split("/")){
                    commandsArray[positionCount] = command;
                    Bukkit.dispatchCommand(sender, command);
                }
            }
            else {sender.sendMessage("No command arguments detected");
            }
        }
        return true;
    }

}

