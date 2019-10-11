package me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;

public class DbKeepAlive {

    private TreeboTickets pl;
    public DbKeepAlive(TreeboTickets main){this.pl = main;}


    public void dbKeepAlive() {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(pl, new Runnable() {
            public void run() {
                try {

                    pl.genericQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID!='0'");
                    dbKeepAlive();
                } catch (NullPointerException e) {
                    pl.makeLog(e);
                }
            }
        }, 36000L);
    }
}
