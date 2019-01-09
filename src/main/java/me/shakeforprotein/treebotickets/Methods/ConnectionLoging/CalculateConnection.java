package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CalculateConnection {

    private TreeboTickets pl;
    public CalculateConnection(TreeboTickets main){this.pl = main;}

    public void calculateConnection(Player p, UUID pUUID) {
        String query = "SELECT * FROM `ontime` WHERE `UUID`=\"" + pUUID + "\" AND `SERVER`=\"" + Bukkit.getServer().getName() + "\" AND `ONOFF`=\"ON\" ORDER BY ID DESC LIMIT 1";
        String query2 = "SELECT * FROM `ontime` WHERE `UUID`=\"" + pUUID + "\" AND `SERVER`=\"" + Bukkit.getServer().getName() + "\" AND `ONOFF`=\"OFF\" ORDER BY ID DESC LIMIT 1";
        ResultSet response, response2;
        try {
            response = pl.connection.createStatement().executeQuery(query);
            response2 = pl.connection.createStatement().executeQuery(query2);
            while (response.next()) {
                String DT = response.getString("CREATED");
                p.sendMessage(DT);
            }
            while (response2.next()) {
                String DT = response2.getString("CREATED");
                p.sendMessage(DT);
            }


        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during calculateConnection()");
            pl.makeLog(e);
        }
    }
}
