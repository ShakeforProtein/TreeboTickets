package me.shakeforprotein.treebotickets.Methods.CreateTicket;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CreateTicket {

    private TreeboTickets pl;


    public CreateTicket(TreeboTickets main){this.pl = main;}

    public void addTicketToDB(Player p, String ticketData, String type, String contents, String server) {
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
                    p.sendMessage("Sending Discord");
                    String discordHook = pl.getConfig().getString("discordHook");
                    //discordHook = discordHook + "?id=" + tID + "&user=" + p.getName() + "&type=" + type + "&contents=" +contents.replace(" ", "%20");

                    //String urlParameters  = "id=" + tID + "&user=" + p.getName() + "&server=" + server + "&type=" + type + "&contents=" + contents;
                    String p1 = "id=" + URLEncoder.encode(tID + "", "UTF-8");
                    String p2 = "&user=" + URLEncoder.encode(p.getName(), "UTF-8");
                    String p3 = "&server=" + URLEncoder.encode(server, "UTF-8");
                    String p4 = "&type=" + URLEncoder.encode(type, "UTF-8");
                    String p5 = "&contents=" + URLEncoder.encode(contents, "UTF-8");
                    String urlParameters = p1 + p2 + p3 + p4 + p5;
                    //URL url = new URL(null, discordHook, new sun.net.www.protocol.https.Handler());
                    //int    postDataLength = postData.length;


                    try {
                        URL url = new URL(null, discordHook, new sun.net.www.protocol.https.Handler());
                        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                        con.setDoOutput(true);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("User-Agent", "Java client");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        try (OutputStream wr = con.getOutputStream()) {

                            wr.write(urlParameters.getBytes(StandardCharsets.UTF_8));
                            wr.flush();
                            System.out.println(con.getResponseCode());
                            p.sendMessage(con.getOutputStream().toString());
                        }

                        StringBuilder content;

                        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(con.getInputStream()))) {

                            String line;
                            content = new StringBuilder();

                            while ((line = br.readLine()) != null) {
                                content.append(line);
                                content.append(System.lineSeparator());
                            }
                        }

                        System.out.println(content.toString());

                    } catch (IOException ex){

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
