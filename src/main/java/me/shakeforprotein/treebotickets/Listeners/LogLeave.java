package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.LogDisconnection;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class LogLeave implements Listener {
    private TreeboTickets pl;
    private LogDisconnection logDisconnection;

    public LogLeave(TreeboTickets main) {
        this.pl = main;
        this.logDisconnection = new LogDisconnection(main);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        logDisconnection.logDisconnection(e.getPlayer());
        pl.getConfig().set("players." + e.getPlayer().getName() + ".ticketstate", 0);
    }
}
