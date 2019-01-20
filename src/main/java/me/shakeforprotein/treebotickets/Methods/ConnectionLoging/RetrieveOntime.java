package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RetrieveOntime {

    private TreeboTickets pl;
    public RetrieveOntime(TreeboTickets main){this.pl = main;}

    public void retrieveOntime(String  p, Player s) {
        String query = "SELECT * FROM `"+ pl.ontimetable +"` WHERE `CurrentName`=\"" + p + "\" ORDER BY ID DESC LIMIT 1";

        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery(query);
            while (response.next()) {
                String uUID = (response.getString("UUID"));
                String currentName = response.getString("CurrentName");
                String currentOn = response.getString("CurrentOn");
                String totalOn = response.getString("TotalOn");
                String currentIP = response.getString("CurrentIP");
                String otherNames = response.getString("OtherNames");
                String firstJoin = response.getString("FirstJoin");
                String lastOff = response.getString("LastLeft");


                s.sendMessage("UUID  -  " + uUID);
                s.sendMessage("CurrentName  -  " + currentName);
                s.sendMessage("OtherNames  -  " + otherNames);
                s.sendMessage("CurrentIP  -  " + currentIP);
                s.sendMessage("FirstJoin  -  " + firstJoin);
                s.sendMessage("LatestOntime  -  " + currentOn);
                s.sendMessage("LastLeft  -  " + lastOff);
                s.sendMessage("TotalOntime  -  " + totalOn);
            }
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during retrieveOntime()");
            pl.makeLog(e);
        }
    }
}
