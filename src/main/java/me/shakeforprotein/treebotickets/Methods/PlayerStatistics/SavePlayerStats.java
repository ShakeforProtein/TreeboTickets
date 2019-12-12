package me.shakeforprotein.treebotickets.Methods.PlayerStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;
import java.util.Set;

public class SavePlayerStats {

    private TreeboTickets pl;


    public SavePlayerStats(TreeboTickets main) {
        this.pl = main;
    }

    public boolean savePlayerStats(PlayerEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {
                try {
                    Player p = e.getPlayer();
                    String server = pl.getServerName(e.getPlayer());
                    String statsAddQuery = "UPDATE `stats_" + server + "` SET ";


                    if (pl.getConfig().getConfigurationSection("playerStats." + pl.getServerName(p) + "." + p.getName() + ".kills") != null) {
                        Set<String> killsKeys = pl.getConfig().getConfigurationSection("playerStats." + pl.getServerName(p) + "." + p.getName() + ".kills").getKeys(false);
                        String[] killKeysArray = Arrays.copyOf(killsKeys.toArray(), killsKeys.size(), String[].class);
                        for (String column : killKeysArray) {
                            column = column.toUpperCase();
                            String columnName = column + "KILLS";
                            statsAddQuery += "`" + columnName + "` = " + columnName + " + " + pl.getConfig().getInt("playerStats." + pl.getServerName(p) + "." + p.getName() + ".kills." + column) + ",";
                            pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".kills." + column, null);
                        }
                    }

                    if (pl.getConfig().getConfigurationSection("playerStats." + pl.getServerName(p) + "." + p.getName() + ".deaths") != null) {
                        Set<String> deathsKeys = pl.getConfig().getConfigurationSection("playerStats." + pl.getServerName(p) + "." + p.getName() + ".deaths").getKeys(false);
                        String[] deathsKeysArray = Arrays.copyOf(deathsKeys.toArray(), deathsKeys.size(), String[].class);
                        for (String column : deathsKeysArray) {
                            column = column.toUpperCase();
                            String columnName = column + "DEATHS";
                            statsAddQuery += " `" + columnName + "` = " + columnName + " + " + pl.getConfig().getInt("playerStats." + pl.getServerName(p) + "." + p.getName() + ".deaths." + column) + ",";
                            pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".deaths." + column, null);
                        }
                    }
                    if (pl.getConfig().getConfigurationSection("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi") != null) {
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.EzRank") != null){
                            statsAddQuery += " `EZRANK` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.EzRank") + "\",";
                        }
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.EzProgress") != null){
                            statsAddQuery += " `EZPROGRESS` =  \""+ pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.EzProgress") + "\",";
                        }
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.vaultBal") != null){
                            statsAddQuery += " `BALANCE` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.vaultBal") + "\",";
                        }
                       /* if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.npcPoliceArrests") != null){
                            statsAddQuery += " `TOTALARRESTS` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.npcPoliceArrests") + "\",";
                        }*/
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.bounty") != null){
                            statsAddQuery += " `BOUNTY` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.bounty") + "\",";
                        }
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.totalBounty") != null){
                            statsAddQuery += " `ALLTIMEBOUNTY` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.totalBounty") + "\",";
                        }
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.murders") != null){
                            statsAddQuery += " `MURDERS` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.murders") + "\",";
                        }
                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.timeRemaining") != null){
                            statsAddQuery += " `SENTENCEREMAINING` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.timeRemaining") + "\",";
                        }

                        if(pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.status") != null){
                            statsAddQuery += " `STATUS` = \"" + pl.getConfig().getString("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi.status") + "\",";
                        }
                    }


                    statsAddQuery += " `IGNAME` = IGNAME WHERE `UUID` ='" + p.getUniqueId() + "'";
                    pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".deaths", null);
                    pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".kills", null);
                   // pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".papi", null);

                    pl.getConfig().set("LastStatEntry", statsAddQuery);
                    pl.saveConfig();
                    //System.out.println(statsAddQuery);
                    if (!e.getPlayer().getWorld().getName().toLowerCase().replace("_", "").contains("skyhub")) {
                        int response = pl.connection.createStatement().executeUpdate(statsAddQuery);
                    }
                } catch (Exception err) {
                    pl.makeLog(err);
                }
            }
        });
        return true;
    }

}
