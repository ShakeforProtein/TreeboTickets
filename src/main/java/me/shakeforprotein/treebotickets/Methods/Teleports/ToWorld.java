package me.shakeforprotein.treebotickets.Methods.Teleports;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ToWorld {

    private TreeboTickets pl;
    public ToWorld(TreeboTickets main){this.pl = main;}


    public void toWorld(String server, String toWorld, Player p){
        if (p.hasPermission("tbtickets.server." + server)){

            if (!server.equalsIgnoreCase(pl.getConfig().getString("serverName"))) {
                p.sendMessage("As you were on the wrong server, you will need to repeat the command.");
                p.sendMessage("Changing your server for you now.");
                pl.api.connectOther(p.getName(), server);
            } else {
                if(p.getWorld().getName().equalsIgnoreCase("test_the_end")){toWorld = "test";}
                p.teleport(Bukkit.getWorld(toWorld).getSpawnLocation());
            }
        }
        else{p.sendMessage(ChatColor.RED + "You do not have the required permission node");}
    }
}
