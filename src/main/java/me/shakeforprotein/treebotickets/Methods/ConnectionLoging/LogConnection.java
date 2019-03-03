package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LogConnection {

    private TreeboTickets pl;

    public LogConnection(TreeboTickets main){
    this.pl = main;}

    public void logConnection(Player p) {
        ResultSet response;
        ResultSet responseIndividualServer;
        String query = "";
        try {
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.ontimetable + "` WHERE UUID = '"+ p.getUniqueId() +"'");
            responseIndividualServer = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `stats_" + pl.getServerName(p) + "` WHERE UUID = '"+ p.getUniqueId() +"'");


            while (responseIndividualServer.next()) {
                if (responseIndividualServer.getInt("TOTAL") == 0) {
                    UUID uUID = p.getUniqueId();
                    query = "INSERT INTO `stats_" + pl.getServerName(p) + "`(`UUID`, `IGNAME`) VALUES  ('" + uUID + "', '"+ p.getName() +"')";
                    pl.connection.createStatement().executeUpdate(query);
                }
            }
            while (response.next()) {
                if(response.getInt("TOTAL") == 0){
                    UUID uUID = p.getUniqueId();
                    String currentName = p.getName();
                    Long currentOn = System.currentTimeMillis();
                    int totalOn = 0;
                    InetAddress currentIP = p.getAddress().getAddress();
                    String otherNames = "";
                    String otherIP = "";
                    Long firstJoin = System.currentTimeMillis();
                    int lastLeft = 0;
                    query = "INSERT INTO `" + pl.ontimetable + "`(`UUID`, `CurrentName`, `CurrentOn`, `TotalOn`, `CurrentIP`, `OtherIP`, `FirstJoin`, `LastLeft`) VALUES  ('" + uUID + "','" + currentName + "','" + currentOn + "','" + totalOn + "','" + currentIP + "','" + otherIP + "','" + firstJoin + "','" + lastLeft + "')";
                    int response2 = pl.connection.createStatement().executeUpdate(query);
                }

            else{
                    UUID uUID = p.getUniqueId();
                    String currentName = p.getName();
                    Long currentOn = System.currentTimeMillis();
                    //int totalOn = 0;
                    InetAddress currentIP = p.getAddress().getAddress();
                    //String othernames = "";
                    //String otherIP = "";
                    Long firstJoin = System.currentTimeMillis();
                    int lastLeft = 0;
                    query =  "UPDATE  `"+ pl.ontimetable +"` SET  `CurrentName` = '" + currentName +"',`CurrentOn` = '" + currentOn + "', `CurrentIP` = '" + currentIP +"' WHERE  `UUID` = '" + p.getUniqueId() +"'";
                    int response2 = pl.connection.createStatement().executeUpdate(query);

                }
            }
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during logConnection()");
            pl.makeLog(e);
        }
    }


}

