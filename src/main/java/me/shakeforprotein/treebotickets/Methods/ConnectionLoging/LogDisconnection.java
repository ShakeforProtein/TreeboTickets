package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;
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
        String query;
        try {
            long totalOn = 0;
            long currentOn = 0;
            response = pl.connection.createStatement().executeQuery("SELECT *FROM `" + pl.ontimetable + "` WHERE UUID = '"+ p.getUniqueId() +"'");
            while (response.next()) {
                totalOn = response.getLong("TotalOn");
                currentOn = response.getLong("CurrentOn");
            }
            UUID uUID = p.getUniqueId();
            long lastLeft = System.currentTimeMillis();
            totalOn = totalOn + (lastLeft - currentOn);
            query = "UPDATE `" + pl.ontimetable + "` SET  `LastLeft` = '" + lastLeft + "', `TotalOn` = '" + totalOn + "' WHERE  `UUID` = '" + p.getUniqueId() + "'";
            int response2 = pl.connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during logDisconnection()");
            pl.makeLog(e);
        }
    }
}
