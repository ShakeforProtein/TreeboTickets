package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.LogConnection;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;

public class LogLeave implements Listener {
    private TreeboTickets pl;
    private LogConnection logConnection;

    public LogLeave(TreeboTickets main) {
        this.pl = main;
        this.logConnection = new LogConnection(main);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        logConnection.logConnection(e.getPlayer().getUniqueId(), e.getPlayer().getName(), "OFF", LocalDateTime.now().toString());
    }
}
