package me.shakeforprotein.treebotickets.Bungee;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.configuration.ConfigurationSection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ShakeSocketSend implements Runnable {

    private TreeboTickets pl;
    private String ipAddress = "127.0.0.1";
    private int port = 9999;
    public ShakeSocketSend(TreeboTickets main) {
        this.pl = main;
        this.ipAddress = pl.getConfig().getString("socketIP") == null ? "127.0.0.1" : pl.getConfig().getString("socketIP");
        this.port = pl.getConfig().getInt("socketPort") == 0 ? 9999 : pl.getConfig().getInt("socketPort");

    }

    @Override
    public void run() {
        if (pl.getConfig().getConfigurationSection("pullbackPlayers") != null) {
            ConfigurationSection pBP = pl.getConfig().getConfigurationSection("pullbackPlayers");

            for (String playerName : pBP.getKeys(false)) {
                System.out.println("PULLING" + playerName);
                Socket client;
                try {
                    //Creating Clientside Socket
                    client = new Socket(ipAddress, port);

                    OutputStream out = client.getOutputStream();
                    PrintWriter writer = new PrintWriter(out);

                    //Writing a String to the Server via a PrintWriter
                        writer.write("SHAKESOCKET:CONNECT:" + playerName + ":" + pl.getConfig().getString("serverName"));
                    //Flushing the Writer to actually send the Data
                    writer.flush();


                    writer.close();
                    client.close();
                } catch (UnknownHostException e) {
                    System.out.println("[Error] Failed to connect to the remote Server.");
                } catch (IOException e) {
                    System.out.println("[Error] Failed to create Writers.");
                }
                pl.getConfig().set("pullbackPlayers." + playerName, null);

            }
            pl.getConfig().set("pullbackPlayers", null);
            pl.saveConfig();
        }
        else{
            System.out.println("Pull back players list was empty");
        }

    }
}
