package me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance;

import me.shakeforprotein.treebotickets.Methods.NotifyOnline;
import me.shakeforprotein.treebotickets.TreeboTickets;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CleanupDatabase {

    private TreeboTickets pl;
    private NotifyOnline notifyOnline;

    public CleanupDatabase(TreeboTickets main){
        this.pl = main;
        this.notifyOnline = new NotifyOnline(pl);
    }

    public void cleanupDatabase(int size){
        ResultSet response;
        ResultSet response2;
        int response3;
        int arrayPosition = 0;
        int trimTotal = 0;
        int total = 0;
        String tIdString = "";
        String[] tIdArray;
        try {
            response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS = 'CLOSED' AND STAFF != 'DELETED'");
            while (response.next()) {
                total = response.getInt("TOTAL");
            }
            if(total > size){
                System.out.println("Total (" + total + ") > size (" + size + ")");
                response2 = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS = 'CLOSED' AND STAFF != 'DELETED' ORDER BY ID ASC");
                while(response2.next()) {
                    tIdString = tIdString + response2.getInt("ID") + ",";
                }
                tIdArray = tIdString.split(",");
                System.out.println("Just split: " + tIdArray.length);

                System.out.println("tIdA =  " + tIdArray.length);
                int[] tIdIntArray = new int[tIdArray.length - 1];
                System.out.println("tIdIA =  " + tIdIntArray.length);
                int counter = 0;
                for(String sID : tIdArray){
                    try {tIdIntArray[counter] = Integer.parseInt(sID);
                        counter++;}
                    catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("Error: sID = " + sID);
                        System.out.println("Error: tID = " + Integer.parseInt(sID));}
                }
                System.out.println("tIdIA =  " + tIdIntArray.length);

                arrayPosition = 0;
                trimTotal = tIdIntArray.length - size;
                System.out.println("TrimTotal = " + trimTotal);
                for (counter = 0; counter < trimTotal; counter++){
                    System.out.println("rawArray Position: " + arrayPosition);
                    if(arrayPosition < trimTotal){
                        System.out.println("Trim Loop Array Position: " + arrayPosition);
                        response3 = pl.connection.createStatement().executeUpdate("UPDATE `" + pl.getConfig().getString("table") + "` SET STAFF = 'DELETED', STATUS = 'CLOSED' WHERE ID='" + tIdIntArray[counter] + "'");
                        notifyOnline.notifyOnline("admin", tIdIntArray[counter] + "", arrayPosition, trimTotal);
                        System.out.println("passed notifyOnline");
                        arrayPosition++;
                    }
                }
            }
            System.out.println("Completed loop");
        }
        catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during cleanupDatabase()");
            pl.makeLog(e);
        }
    }
}
