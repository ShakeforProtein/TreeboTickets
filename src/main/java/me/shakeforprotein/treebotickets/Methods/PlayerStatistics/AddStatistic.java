package me.shakeforprotein.treebotickets.Methods.PlayerStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AddStatistic {

    private TreeboTickets pl;

    public AddStatistic(TreeboTickets main) {
        this.pl = main;
    }

    public void addStatistic(Player p, String type, String entity) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {
                try {
                    pl.getConfig().get("playerStats." + pl.getServerName(p) + "." + p.getName() + "." + type + "." + entity);
                } catch (NullPointerException err) {
                    pl.getConfig().createSection("playerStats." + pl.getServerName(p) + "." + p.getName() + "." + entity);
                }
                pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + "." + type + "." + entity, (pl.getConfig().getInt("playerStats." + pl.getServerName(p) + "." + p.getName() + "." + type + "." + entity) + 1));
            }
        });
    }
}
