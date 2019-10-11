package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.TicketStatistics.AdminStats;
import me.shakeforprotein.treebotickets.Methods.TicketStatistics.BuilderStats;
import me.shakeforprotein.treebotickets.Methods.TicketStatistics.StaffStats;
import me.shakeforprotein.treebotickets.TreeboTickets;
import me.shakeforprotein.treebotickets.UpdateChecker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.HashMap;

public class NotifyStaff implements Listener {

    private TreeboTickets pl;
    private UpdateChecker uc;
    private AdminStats adminStats;
    private StaffStats staffStats;
    private BuilderStats builderStats;


    public NotifyStaff(TreeboTickets main) {
        this.pl = main;
        this.adminStats = new AdminStats(main);
        this.staffStats = new StaffStats(main);
        this.builderStats = new BuilderStats(main);
        this.uc = new UpdateChecker(main);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {


        try {
            pl.openConnection();
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println(pl.err + "Failed to reconnect to database. This is probably fine.");
        }

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
            public void run() {
                Player p = e.getPlayer();

                if ((pl.getConfig().getString(e.getPlayer().getName()) == null) || ((pl.getConfig().getString(e.getPlayer().getName()) != null) && (pl.getConfig().getString(e.getPlayer().getName()).equalsIgnoreCase("false")))) {
                    if (e.getPlayer().hasPermission("tbtickets.admin")) {
                        adminStats.adminStats(e.getPlayer());
                    }
                    if (e.getPlayer().hasPermission("tbtickets.mod.view")) {
                        staffStats.staffStats(e.getPlayer());
                    } else if (e.getPlayer().hasPermission("tbtickets.builder")) {
                        builderStats.builderStats(e.getPlayer());
                    }
                    if (e.getPlayer().hasPermission(uc.requiredPermission)) {
                            uc.checkUpdates(e.getPlayer());
                    }
                }
            }
        }, 200L);
    }
}