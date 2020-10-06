package me.shakeforprotein.treebotickets.Listeners.StatTracking;

import me.shakeforprotein.treebotickets.Methods.PlayerStatistics.AddStatistic;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnPlayerKill implements Listener {

    private TreeboTickets pl;
    private AddStatistic addStatistic;

    public OnPlayerKill(TreeboTickets main){
        this.pl = main;
        this.addStatistic = new AddStatistic(pl);
    }

    @EventHandler
    private void onPlayerKillEvent(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if(e.getEntity() instanceof Player){
                addStatistic.addStatistic(killer,"kills","TOTALPLAYER");
            }
            else{
                for (String mob : pl.mobList) {
                    if (entity.getType().toString().toLowerCase().contains(mob.toLowerCase()) || entity.getType().toString().replace(" ","").replace("_","").toLowerCase().contains(mob.toLowerCase())) {
                        String killedMob = mob.toUpperCase().replace(" ","").replace("_", "");
                        if (killedMob.toLowerCase().equalsIgnoreCase("PIGZOMBIE")){killedMob = "ZOMBIEPIGMAN";}
                        if (killedMob.toLowerCase().equalsIgnoreCase("PIGLIN")){killedMob = "ZOMBIEPIGMAN";}
                        if (killedMob.toLowerCase().equalsIgnoreCase("PIGLIN_BRUTE")){killedMob = "ZOMBIEPIGMAN";}
                        if (killedMob.toLowerCase().equalsIgnoreCase("ZOMBIFIED_PIGLIN")){killedMob = "ZOMBIEPIGMAN";}
                        if (killedMob.toLowerCase().equalsIgnoreCase("WITHER")){killedMob = "WITHERBOSS";}
                        addStatistic.addStatistic(killer,"kills",killedMob);
                        addStatistic.addStatistic(killer,"kills","TOTALMOB");
                        break;
                    }
                }
            }
            addStatistic.addStatistic(killer,"kills", "TOTAL");
        }
    }
}
