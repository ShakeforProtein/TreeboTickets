package me.shakeforprotein.treebotickets.Listeners.StatTracking;

import me.shakeforprotein.treebotickets.Methods.PlayerStatistics.SavePlayerStats;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerDisconnect implements Listener {

    private TreeboTickets pl;
    private SavePlayerStats savePlayerStats;

    public OnPlayerDisconnect(TreeboTickets main) {
                this.pl = main;
                this.savePlayerStats = new SavePlayerStats(pl);
    }

    @EventHandler
    private void playerDisconnect(PlayerQuitEvent e){
        savePlayerStats.savePlayerStats(e);
    }
}
