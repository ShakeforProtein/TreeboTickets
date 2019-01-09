package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class RemoteExecute implements CommandExecutor {

    private TreeboTickets pl;

    public RemoteExecute(TreeboTickets main) {
        pl = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("remoteexecute")) {
            if (sender instanceof ConsoleCommandSender) {
                String onServer = args[0];
                if (onServer.equalsIgnoreCase(pl.getConfig().getString("serverName"))) {
                    StringBuilder commandString = new StringBuilder();
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    for (int i = 1; i < args.length; i++) {
                        commandString = commandString.append(args[i] + " ");
                    }
                    sender.sendMessage("Executing command \"" + commandString.toString() + "\" on server - " + args[0]);

                    Bukkit.dispatchCommand(console, commandString.toString());
                } else {
                    sender.sendMessage("Command does not apply to this server - " + pl.getConfig().getString("serverName") + ". Skipping execute");
                }
            } else {
                sender.sendMessage("This command only runs from console");
            }

        }
        return true;
    }
}
