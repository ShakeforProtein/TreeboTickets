package me.shakeforprotein.treebotickets.Listeners.StatTracking;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class OnPlayerDeath implements Listener {

    private TreeboTickets pl;
    private ArrayList<String> mobList = new ArrayList<>();
    public OnPlayerDeath(TreeboTickets main) {
        this.pl = main;
        mobList.add("SKELETON_HORSE");
        mobList.add("WITHER_SKELETON");
        mobList.add("CAVE_SPIDER");
        mobList.add("ELDER_GUARDIAN");
        mobList.add("PIG_ZOMBIE");
        mobList.add("ZOMBIE_VILLAGER");
        mobList.add("IRON_GOLEM");
        mobList.add("SNOW_GOLEM");
        mobList.add("ENDER_DRAGON");
        mobList.add("WITHER_BOSS");
        mobList.add("TRADER_LLAMA");
        mobList.add("WANDERING_TRADER");
        mobList.add("SKELETON HORSE");
        mobList.add("WITHER SKELETON");
        mobList.add("CAVE SPIDER");
        mobList.add("ELDER GUARDIAN");
        mobList.add("PIG ZOMBIE");
        mobList.add("ZOMBIE VILLAGER");
        mobList.add("IRON GOLEM");
        mobList.add("SNOW GOLEM");
        mobList.add("ENDER DRAGON");
        mobList.add("WITHER BOSS");
        mobList.add("TRADER LLAMA");
        mobList.add("WANDERING TRADER");
        mobList.add("GIANT");
        mobList.add("CREEPER");
        mobList.add("BAT");
        mobList.add("CHICKEN");
        mobList.add("COD");
        mobList.add("COW");
        mobList.add("DONKEY");
        mobList.add("HORSE");
        mobList.add("MOOSHROOM");
        mobList.add("MULE");
        mobList.add("OCELOT");
        mobList.add("PARROT");
        mobList.add("PIG");
        mobList.add("RABBIT");
        mobList.add("SHEEP");
        mobList.add("SALMON");
        mobList.add("SQUID");
        mobList.add("TURTLE");
        mobList.add("TROPICALFISH");
        mobList.add("VILLAGER");
        mobList.add("PUFFERFISH");
        mobList.add("DOLPHIN");
        mobList.add("LLAMA");
        mobList.add("POLARBEAR");
        mobList.add("WOLF");
        mobList.add("ENDERMAN");
        mobList.add("SPIDER");
        mobList.add("GUARDIAN");
        mobList.add("PHANTOM");
        mobList.add("SILVERFISH");
        mobList.add("SLIME");
        mobList.add("DROWNED");
        mobList.add("HUSK");
        mobList.add("ZOMBIE");
        mobList.add("SKELETON");
        mobList.add("STRAY");
        mobList.add("BLAZE");
        mobList.add("GHAST");
        mobList.add("MAGMACUBE");
        mobList.add("ENDERMITE");
        mobList.add("SHULKER");
        mobList.add("EVOKER");
        mobList.add("VINDICATOR");
        mobList.add("VEX");
        mobList.add("WITCH");
        mobList.add("CAT");
        mobList.add("PANDA");
        mobList.add("PILLAGER");
        mobList.add("RAVAGER");
        mobList.add("ILLUSIONER");
        mobList.add("KILLER BUNNY");
    }

    @EventHandler
    private void onPlayerDeathEvent(PlayerDeathEvent e) {

        int attributed = 0;
        String server = getServerName(e.getEntity());
        Player p = e.getEntity();
        String cause = e.getDeathMessage();
        cause = cause.replaceAll(p.getName(), "XXXVICTIMXXX");
        for(Player player: Bukkit.getOnlinePlayers()){
            if (cause.toLowerCase().contains(player.getName().toLowerCase())){
                if (pl.getConfig().get("playerStats." + p.getName() + ".deaths.PLAYER") == null) {
                    pl.getConfig().set("playerStats." + p.getName() + ".deaths.PLAYER", 1);
                } else {
                    pl.getConfig().set("playerStats." + p.getName() + ".deaths.PLAYER", (pl.getConfig().getInt("playerStats." + p.getName() + ".deaths.PLAYER") + 1));
                }
                attributed = 1;
                break;
            }
        }
        for (String mob : mobList) {
            if (cause.toLowerCase().contains(mob.toLowerCase())) {
                String deathCause = mob.toUpperCase();
                deathCause = deathCause.replace(" ", "");
                if (pl.getConfig().get("playerStats." + p.getName() + ".deaths." + deathCause) == null) {
                    pl.getConfig().set("playerStats." + p.getName() + ".deaths." + deathCause, 1);
                } else {
                    pl.getConfig().set("playerStats." + p.getName() + ".deaths." + deathCause, (pl.getConfig().getInt("playerStats." + p.getName() + ".deaths." + deathCause) + 1));
                }
                attributed = 1;
                break;
            }
        }
        if(cause.toLowerCase().contains("fell from a high place")){
            if (pl.getConfig().get("playerStats." + p.getName() + ".deaths.GRAVITY") == null) {
                pl.getConfig().set("playerStats." + p.getName() + ".deaths.GRAVITY", 1);
            } else {
                pl.getConfig().set("playerStats." + p.getName() + ".deaths.GRAVITY", (pl.getConfig().getInt("playerStats." + p.getName() + ".deaths.GRAVITY") + 1));
            }
            attributed = 1;
        }

        if(cause.toLowerCase().contains("kinetic energy")){
            if (pl.getConfig().get("playerStats." + p.getName() + ".deaths.ELYTRA") == null) {
                pl.getConfig().set("playerStats." + p.getName() + ".deaths.ELYTRA", 1);
            } else {
                pl.getConfig().set("playerStats." + p.getName() + ".deaths.ELYTRA", (pl.getConfig().getInt("playerStats." + p.getName() + ".deaths.ELYTRA") + 1));
            }
            attributed = 1;
        }


        if (attributed == 0){
            if (pl.getConfig().get("playerStats." + p.getName() + ".deaths.ENVIRONMENT") == null) {
                pl.getConfig().set("playerStats." + p.getName() + ".deaths.ENVIRONMENT", 1);
            } else {
                pl.getConfig().set("playerStats." + p.getName() + ".deaths.ENVIRONMENT", (pl.getConfig().getInt("playerStats." + p.getName() + ".deaths.ENVIRONMENT") + 1));
            }
            attributed = 1;
        }

        if (pl.getConfig().get("playerStats." + p.getName() + ".deaths.TOTAL") == null) {
            pl.getConfig().set("playerStats." + p.getName() + ".deaths.TOTAL", 1);
        } else {
            pl.getConfig().set("playerStats." + p.getName() + ".deaths.TOTAL", (pl.getConfig().getInt("playerStats." + p.getName() + ".deaths.TOTAL") + 1));
        }


    }


    @EventHandler
    private void onPlayerKillEvent(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if(e.getEntity() instanceof Player){
                if (pl.getConfig().get("playerStats." + killer.getName() + ".kills.TOTALPLAYER") == null) {
                    pl.getConfig().set("playerStats." + killer.getName() + ".kills.TOTALPLAYER", 1);
                } else {
                    pl.getConfig().set("playerStats." + killer.getName() + ".kills.TOTALPLAYER", (pl.getConfig().getInt("playerStats." + killer.getName() + ".kills.TOTALPLAYER") + 1));
                }
            }
            else{
            for (String mob : mobList) {
                if (entity.getType().toString().toLowerCase().contains(mob.toLowerCase())) {
                    String killedMob = mob.toUpperCase().replace(" ","").replace("_", "");
                    if (killedMob.toLowerCase().equalsIgnoreCase("PIGZOMBIE")){killedMob = "ZOMBIEPIGMAN";}
                    killer.sendMessage(entity.getType().toString());
                    killer.sendMessage(mob);
                    killer.sendMessage(killedMob);
                    if (pl.getConfig().get("playerStats." + killer.getName() + ".kills." + killedMob) == null) {
                        pl.getConfig().set("playerStats." + killer.getName() + ".kills." + killedMob, 1);
                    } else {
                        pl.getConfig().set("playerStats." + killer.getName() + ".kills." + killedMob, (pl.getConfig().getInt("playerStats." + killer.getName() + ".kills." + killedMob) + 1));
                    }
                    if (pl.getConfig().get("playerStats." + killer.getName() + ".kills.TOTALMOB") == null) {
                        pl.getConfig().set("playerStats." + killer.getName() + ".kills.TOTALMOB", 1);
                    } else {
                        pl.getConfig().set("playerStats." + killer.getName() + ".kills.TOTALMOB", (pl.getConfig().getInt("playerStats." + killer.getName() + ".kills.TOTALMOB") + 1));
                    }
                    break;
                }
            }
            }
            if (pl.getConfig().get("playerStats." + killer.getName() + ".kills.TOTAL") == null) {
                pl.getConfig().set("playerStats." + killer.getName() + ".kills.TOTAL", 1);
            } else {
                pl.getConfig().set("playerStats." + killer.getName() + ".kills.TOTAL", (pl.getConfig().getInt("playerStats." + killer.getName() + ".kills.TOTAL") + 1));
            }
        }
    }


    @EventHandler
    private void onPlayerDisconnectEvent(PlayerQuitEvent e) {
        savePlayerStats(e);
    }

    @EventHandler
    public void onPlayerChangeWorlds(PlayerChangedWorldEvent e){
        savePlayerStats(e);
    }



    private boolean savePlayerStats(PlayerEvent e){
        try {
            Player p = e.getPlayer();
            String server = getServerName(e.getPlayer());
            String statsAddQuery = "UPDATE `stats_" + server + "` SET ";
            /*
            if (pl.getConfig().getConfigurationSection("playerStats") == null || pl.getConfig().get(("playerStats." + p.getName())) == null) {
                pl.getConfig().createSection("playerStats." + p.getName() + ".kills");
            }
            if (pl.getConfig().getConfigurationSection("playerStats") == null || pl.getConfig().get("playerStats." + p.getName()) == null) {
                pl.getConfig().createSection("playerStats." + p.getName() + ".deaths");
            }*/


            if (pl.getConfig().getConfigurationSection("playerStats." + p.getName() + ".kills") != null) {
                Set<String> killsKeys = pl.getConfig().getConfigurationSection("playerStats." + p.getName() + ".kills").getKeys(false);
                String[] killKeysArray = Arrays.copyOf(killsKeys.toArray(), killsKeys.size(), String[].class);
                for (String column : killKeysArray) {
                    column = column.toUpperCase();
                    String columnName = column + "KILLS";
                    statsAddQuery += "`" + columnName + "` = " + columnName + " + " + pl.getConfig().getInt("playerStats." + p.getName() + ".kills." + column) + ",";
                    pl.getConfig().set("playerStats." + p.getName() + ".kills." + column, null);
                }
            }

            if (pl.getConfig().getConfigurationSection("playerStats." + p.getName() + ".deaths") != null) {
                Set<String> deathsKeys = pl.getConfig().getConfigurationSection("playerStats." + p.getName() + ".deaths").getKeys(false);
                String[] deathsKeysArray = Arrays.copyOf(deathsKeys.toArray(), deathsKeys.size(), String[].class);
                for (String column : deathsKeysArray) {
                    column = column.toUpperCase();
                    String columnName = column + "DEATHS";
                    statsAddQuery += " `" + columnName + "` = " + columnName + " + " + pl.getConfig().getInt("playerStats." + p.getName() + ".deaths." + column) + ",";
                    pl.getConfig().set("playerStats." + p.getName() + ".deaths." + column, null);
                }
            }

            statsAddQuery += " `IGNAME` = IGNAME WHERE `UUID` ='" + p.getUniqueId() + "'";
            pl.getConfig().set("playerStats." + p.getName() + ".deaths", null);
            pl.getConfig().set("playerStats." + p.getName() + ".kills", null);
            pl.getConfig().set("playerStats." + p.getName(), null);

            pl.getConfig().set("LastStatEntry", statsAddQuery);
            pl.saveConfig();
            System.out.println(statsAddQuery);
            int response = pl.connection.createStatement().executeUpdate(statsAddQuery);
        } catch (Exception err) {
            pl.makeLog(err);
        }
        return true;
    }

    private String getServerName(Entity e){
        String server = pl.getConfig().getString("serverName");
            if(server.toLowerCase().contains("Sky")){
                server = e.getWorld().getName().split("_]")[0].split("-")[0];
            }
        return server;
    }
}