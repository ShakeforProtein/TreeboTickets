package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;

import java.sql.SQLException;
import java.util.UUID;

public class LogConnection {

    private TreeboTickets pl;

    public LogConnection(TreeboTickets main){
    this.pl = main;}

    public void logConnection(UUID pUUID, String pIGN, String pOnOff, String pCreated) {
        int response;
        String output = "";
        try {
            String query = "INSERT INTO `ontime`(`UUID`, `IGNAME`, `ONOFF`, `CREATED`, `SERVER`) VALUES (\"" + pUUID + "\",\"" + pIGN + "\",\"" + pOnOff + "\",\"" + pCreated + "\",\"" + pl.getConfig().getString("serverName") + "\")";
            System.out.println(query);
            response = pl.connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during logConnection()");
            pl.makeLog(e);
        }
    }
}
