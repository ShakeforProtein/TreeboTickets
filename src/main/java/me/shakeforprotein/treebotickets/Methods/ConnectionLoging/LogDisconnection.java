package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LogDisconnection {


    private TreeboTickets pl;

    public LogDisconnection(TreeboTickets main) {
        this.pl = main;
    }


    public void logDisconnection(Player p) {

        ResultSet response;
        ResultSet response2;
        String query;
        String statsQuery;
        try {
            long totalOn = 0;
            long currentOn = 0;
            long playTime = 0;
            UUID uUID = p.getUniqueId();
            response = pl.connection.createStatement().executeQuery("SELECT *FROM `" + pl.ontimetable + "` WHERE UUID = '"+ uUID +"'");
            response2 = pl.connection.createStatement().executeQuery("SELECT *FROM `stats_" + pl.getServerName(p) + "` WHERE UUID = '"+ uUID +"'");
            while (response.next()) {
                totalOn = response.getLong("TotalOn");
                currentOn = response.getLong("CurrentOn");
            }
            while (response2.next()) {
                playTime = response2.getLong("PLAYTIME");
            }
            long lastLeft = System.currentTimeMillis();
            totalOn = totalOn + (lastLeft - currentOn);
            playTime = playTime + (lastLeft - currentOn);
            query = "UPDATE `" + pl.ontimetable + "` SET  `LastLeft` = '" + lastLeft + "', `TotalOn` = '" + totalOn + "' WHERE  `UUID` = '" + uUID + "'";
            statsQuery = "UPDATE `stats_"+ pl.getServerName(p) +"` SET `PLAYTIME` = '" + playTime + "' WHERE  `UUID` = '" + uUID + "'";

            int response3 = pl.connection.createStatement().executeUpdate(query);
            int response4 = pl.connection.createStatement().executeUpdate(statsQuery);
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during logDisconnection()");
            pl.makeLog(e);
        }
    }
}
