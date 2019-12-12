package me.shakeforprotein.treebotickets.Methods.CreateTicket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTicket {

    private TreeboTickets pl;


    public CreateTicket(TreeboTickets main){this.pl = main;}

    public void addTicketToDB(Player p, String ticketData, String type, String contents) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {

        try {
            String output = "" + pl.connection.createStatement().executeUpdate(pl.baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
            ResultSet response = pl.connection.createStatement().executeQuery("SELECT * FROM `tickets`WHERE `IGNAME`=\"" + p.getName() + "\" ORDER BY id DESC LIMIT 1");
            ResultSet staffResponse = pl.connection.createStatement().executeQuery("SELECT * FROM `tickets_stafflist` WHERE 1 = 1");
            int tID = 0;
            while (response.next()) {
                tID = response.getInt("ID");
                p.sendMessage(pl.badge + "Your Ticket number is " + tID + ". Use /ticket view " + tID + " to view any updates");
                try {
                    String discordHook = pl.getConfig().getString("discordHook");
                    discordHook = discordHook + "?id=" + tID + "&user=" + p.getName() + "&type=" + type + "&contents=" +contents.replace(" ", "%20");


                    URL url = new URL(null, discordHook, new sun.net.www.protocol.https.Handler());

                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setRequestProperty("User-Agent", "ShakeBrowser/5.0");
                    httpsURLConnection.setRequestProperty("Referer", "no-referer");
                    httpsURLConnection.setConnectTimeout(5000);
                    httpsURLConnection.connect();
                    System.out.println(httpsURLConnection.getResponseMessage());
                    if(httpsURLConnection.getErrorStream() != null){
                        System.out.println(discordHook);
                    }
                }
                catch(IOException e){
                    pl.makeLog(e);
                }

                while (staffResponse.next()){
                    String staff = staffResponse.getString("IGNAME");
                    pl.api.sendMessage(staff, "Player " + p.getName() + "Has just submitted ticket number " + tID);
                }
            }
            p.sendMessage(pl.badge + "Your ticket has been successfully submitted");
        } catch (SQLException e) {
            p.sendMessage(pl.err + "Something went wrong while sending your ticket to the database");
            pl.makeLog(e);
            System.out.println(pl.err + "Encountered " + e.toString() + " during addTicketToDB()");
        }

            }
        });

    }

}
