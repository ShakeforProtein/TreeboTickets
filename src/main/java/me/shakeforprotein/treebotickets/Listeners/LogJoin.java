package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Commands.OnHere;
import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.LogConnection;
import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.RetrieveOntime;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

public class LogJoin implements Listener {

    private TreeboTickets pl;
    private LogConnection logConnection;
    private RetrieveOntime retrieveOntime;


    public LogJoin(TreeboTickets main) {
        this.pl = main;
        this.logConnection = new LogConnection(main);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        logConnection.logConnection(e.getPlayer());
        if(pl.getConfig().getString("serverName").equalsIgnoreCase("hub")){
            if(e.getPlayer().getName().equalsIgnoreCase("Huntwren") || e.getPlayer().getName().equalsIgnoreCase("ShakeforProtein")){
                retrieveOntime.retrieveOntime("Huntwren",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("SquishyMikes",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("xZarina",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("NecRick",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("ShadowPlusPlus",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("HunkyChinchilla",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("DenverAU",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("ShakeforProtein",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("Chicken_Tree",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("C4_Demon",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("AnimatingPanda",e.getPlayer(),true);
                retrieveOntime.retrieveOntime("SonOfOdin001",e.getPlayer(),true);
            }
        }
    }
}
