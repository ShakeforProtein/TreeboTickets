package me.shakeforprotein.treebotickets.Methods.Teleports;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ServerSwitch {

    private TreeboTickets pl;
    public ServerSwitch(TreeboTickets main){this.pl = main;}

    public void serverSwitch(Player p, String server) {
        if (p.hasPermission("tbtickets.server." + server)){
            if (!server.equalsIgnoreCase(pl.getConfig().getString("serverName"))) {
                p.sendMessage(pl.badge + "Attempting to send you to " + server);
                pl.api.connectOther(p.getName(), server);
            } else {
                p.sendMessage(pl.err + "You are already on the " + server + " server.");
                p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "please use /hub to navigate from here");
            }
        }
    }
}
