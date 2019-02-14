package me.shakeforprotein.treebotickets.Listeners.StatTracking;

import me.shakeforprotein.treebotickets.Methods.PlayerStatistics.AddStatistic;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;



public class OnPlayerDeath implements Listener {

    private TreeboTickets pl;
    private AddStatistic addStatistic;

    public OnPlayerDeath(TreeboTickets main) {
        this.pl = main;
        this.addStatistic = new AddStatistic(pl);

    }

    @EventHandler
    private void onPlayerDeathEvent(PlayerDeathEvent e) {
        try{
        int attributed = 0;
        String server = pl.getServerName(e.getEntity());
        Player p = e.getEntity();
        String cause = e.getDeathMessage();
        cause = cause.replaceAll(p.getName(), "XXXVICTIMXXX");
        String deathCause = "";
        p.sendMessage(cause);
        for(Player player: Bukkit.getOnlinePlayers()){
            if (cause.toLowerCase().contains(player.getName().toLowerCase())){
                addStatistic.addStatistic(p, "deaths", "PLAYER");
                attributed = 1;
                deathCause = "PLAYER";
                break;
            }
        }

        for (String mob : pl.mobList) {
            if (cause.toLowerCase().contains(mob.toLowerCase())) {
                deathCause = mob.toUpperCase();
                deathCause = deathCause.replace(" ", "");
                if(deathCause.equalsIgnoreCase("Wither")){deathCause = "WITHERBOSS";}
                addStatistic.addStatistic(p,"deaths",deathCause);
                addStatistic.addStatistic(p,"deaths","TOTALMOB");
                attributed = 1;
                break;
            }
        }

        if(cause.toLowerCase().contains("fell from a high place")){
            addStatistic.addStatistic(p,"deaths", "GRAVITY");
            attributed = 1;
            deathCause = "GRAVITY";
        }

        if(cause.toLowerCase().contains("kinetic energy")){
            addStatistic.addStatistic(p,"deaths","ELYTRA");
            attributed = 1;
            deathCause = "ELYTRA";
        }

        if (attributed == 0){
            addStatistic.addStatistic(p,"deaths","ENVIRONMENT");
            attributed = 1;
            deathCause = "ENVIRONMENT";
        }
        p.sendMessage(deathCause);
        addStatistic.addStatistic(p,"deaths","TOTAL");
    }
    catch(Exception err){
            pl.makeLog(err);
    }
}
}