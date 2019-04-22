package me.shakeforprotein.treebotickets.Methods.Teleports;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class PushToLobby {

    private TreeboTickets pl;
    public PushToLobby(TreeboTickets main){this.pl = main;}

    public void pushToLobby() {
        if (pl.getConfig().getString("isLobbyServer").equalsIgnoreCase("false")) {

            for (Player p : getServer().getOnlinePlayers()) {
                pl.getConfig().set("shutdownPlayerList." + p.getName(), p.getName());
                p.sendMessage(pl.badge + "This server is restarting.");
                p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "Moving you temporarily to the Lobby");
                //p.sendMessage("You will be automatically moved back when the restart is complete");
                //serverSwitch(p, "hub");
            }
            ConsoleCommandSender console = getServer().getConsoleSender();
            String command = "sudo ** lobby";
            Bukkit.dispatchCommand(console, command);
        }
    }
}
