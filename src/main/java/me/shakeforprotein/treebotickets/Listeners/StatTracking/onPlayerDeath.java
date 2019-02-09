package me.shakeforprotein.treebotickets.Listeners.StatTracking;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerDeath implements Listener {

    @EventHandler
    private void onPlayerDeathEvent(PlayerDeathEvent e){

        Player p = e.getEntity();
        World world = p.getWorld();
        String cause = "";
        if(p.getKiller() != null){cause = p.getKiller().getType().name();}
        
    }
}
