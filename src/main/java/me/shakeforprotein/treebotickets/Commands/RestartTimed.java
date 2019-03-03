package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.Teleports.PushToLobby;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class RestartTimed implements CommandExecutor {

    private TreeboTickets pl;
    private PushToLobby pushToLobby;

    public RestartTimed(TreeboTickets main) {
        pl = main;
        this.pushToLobby = new PushToLobby(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("restarttimed") && args.length == 1 && (sender.hasPermission("tbtickets.restart")) || sender instanceof ConsoleCommandSender) {

            String command = "restart";
            Integer timer = Integer.parseInt(args[0]) + 60;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                public void run() {
                    pushToLobby.pushToLobby();
                    pl.saveConfig();
                }
            }, Integer.parseInt(args[0]));

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                public void run() {
                    sender.sendMessage("Restarting Now");
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, command);
                }
            }, timer);
        }
        return true;
    }
}
