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

                    statsAddQuery += " `IGNAME` = IGNAME WHERE `UUID` ='" + p.getUniqueId() + "'";
                    pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".deaths", null);
                    pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName() + ".kills", null);
                    pl.getConfig().set("playerStats." + pl.getServerName(p) + "." + p.getName(), null);

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
