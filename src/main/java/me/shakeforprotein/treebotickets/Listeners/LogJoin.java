package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.LogConnection;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

public class LogJoin implements Listener {

    private TreeboTickets pl;
    private LogConnection logConnection;

    public LogJoin(TreeboTickets main) {
        this.pl = main;
        this.logConnection = new LogConnection(main);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        logConnection.logConnection(e.getPlayer());
    }
}
