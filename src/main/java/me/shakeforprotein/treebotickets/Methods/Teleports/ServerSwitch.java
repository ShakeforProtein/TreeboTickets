package me.shakeforprotein.treebotickets.Methods.Teleports;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Player;

public class ServerSwitch {

    private TreeboTickets pl;
    public ServerSwitch(TreeboTickets main){this.pl = main;}

    public void serverSwitch(Player p, String server) {
        if (p.hasPermission("tbtickets.server." + server)){
            if (!server.equalsIgnoreCase(pl.getConfig().getString("serverName"))) {
                p.sendMessage("Attempting to send you to " + server);
                pl.api.connectOther(p.getName(), server);
            } else {
                p.sendMessage("You are already on the " + server + " server.");
                p.sendMessage("please use /hub to navigate from here");
            }
        }
    }
}
