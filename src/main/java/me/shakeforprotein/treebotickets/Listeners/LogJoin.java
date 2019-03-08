package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Commands.OnHere;
import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.LogConnection;
import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.RetrieveOntime;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogJoin implements Listener {

    private TreeboTickets pl;
    private LogConnection logConnection;
    private RetrieveOntime retrieveOntime;


    public LogJoin(TreeboTickets main) {
        this.pl = main;
        this.logConnection = new LogConnection(main);
        this.retrieveOntime = new RetrieveOntime(main);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        logConnection.logConnection(e.getPlayer());

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
            public void run() {


                if (pl.getConfig().getString("serverName").equalsIgnoreCase("games")) {
                    if (e.getPlayer().hasPermission("tbtickets.staffmanager") || e.getPlayer().getName().equalsIgnoreCase("ShakeforProtein")) {

                        File listFile = new File(pl.getDataFolder(), "staffList.yml");
                        FileConfiguration staffList = YamlConfiguration.loadConfiguration(listFile);

                        for (String item : staffList.getKeys(false)) {
                            String staff = staffList.getString(item);
                            retrieveOntime.retrieveOntime(staff, e.getPlayer(), "true");
                        }
                    }
                }
                int totalRows = 0;
                String ticketNumbers = "";
                String countQuery = ("SELECT Count(*) AS TOTALROWS FROM `" + pl.getConfig().getString("table") + "` WHERE UUID='" + e.getPlayer().getUniqueId().toString() + "' AND ATTN='Player'");
                ResultSet countResponse;
                try {
                    countResponse = pl.connection.createStatement().executeQuery(countQuery);
                    while (countResponse.next()) {
                        totalRows = countResponse.getInt("TOTALROWS");
                    }
                } catch (SQLException err) {
                    e.getPlayer().sendMessage(ChatColor.RED + "Something went wrong");
                    System.out.println("Encountered " + e.toString() + " during Retrieve Player Ticket Count - LogJoin");
                    pl.makeLog(err);
                }

                if (totalRows > 0) {
                    String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE UUID='" + e.getPlayer().getUniqueId().toString() + "' AND ATTN='Player' AND 'STATUS' = 'OPEN'");
                    ResultSet response;
                    try {
                        response = pl.connection.createStatement().executeQuery(query);
                        while (response.next()) {
                            int tId = response.getInt("ID");
                            ticketNumbers = ticketNumbers + " " +  tId + ",";
                            // String notifiedQuery = "UPDATE `tickets` SET ATTN = 'DONE' WHERE ID = '" + tId + "'";
                        }
                        ticketNumbers = ticketNumbers.substring(0, ticketNumbers.length() - 1);
                    } catch (SQLException err) {
                        e.getPlayer().sendMessage(ChatColor.RED + "Something went wrong");
                        System.out.println("Encountered " + e.toString() + " during NotifyPlayer - LogJoin");
                        pl.makeLog(err);
                    }
                    e.getPlayer().sendMessage(ChatColor.GOLD + "The following ticket numbers require your input: " + ChatColor.GREEN + ticketNumbers);
                }
            }
        }, 120L);
    }
}
