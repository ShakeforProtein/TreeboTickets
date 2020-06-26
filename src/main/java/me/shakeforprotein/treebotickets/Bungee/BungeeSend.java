package me.shakeforprotein.treebotickets.Bungee;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeSend {

    private TreeboTickets pl;

    public BungeeSend(TreeboTickets main) {
        this.pl = main;
    }

    public void sendPluginMessage(String type, String server, String cmd) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");  //Type of message
        out.writeUTF(server);       // Server to send to
        out.writeUTF(pl.getName() + "Channel-" + type); //Receiver Channel

        // If you don't care about the player
        // Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        // Else, specify them
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            String message = "";
            if (type.equalsIgnoreCase("CrossServerTeleport") || type.equalsIgnoreCase("CrossServerTPAHere") || type.equalsIgnoreCase("CrossServerTPA") || type.equalsIgnoreCase("Jsaw")) {
                message = cmd;
            }
            msgout.writeUTF(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());


        player.sendPluginMessage(pl, "BungeeCord", out.toByteArray());
    }

    public void sendConnectOther(String server, String playerName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");  //Type of message
        out.writeUTF(playerName);         //player
        out.writeUTF(server);             //server

        // If you don't care about the player
        // Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        // Else, specify them
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            player.sendPluginMessage(pl, "BungeeCord", out.toByteArray());
        }
    }

    public void getPlayerList(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerList");  //Type of message
        out.writeUTF(server);             //server

        // If you don't care about the player
        // Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        // Else, specify them
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            player.sendPluginMessage(pl, "BungeeCord", out.toByteArray());
        }
    }

    public void getServers() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");  //Type of message

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(pl, "BungeeCord", out.toByteArray());
    }

    public void sendConnectOtherSpecial(Player player, String server, String playerName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");  //Type of message
        out.writeUTF(playerName);         //player
        out.writeUTF(server);             //server

        // If you don't care about the player
        // Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        // Else, specify them
        player.sendPluginMessage(pl, "BungeeCord", out.toByteArray());

    }

}
