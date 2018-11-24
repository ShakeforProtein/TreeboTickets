package me.shakeforprotein.treebotickets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;


public final class TreeboTickets extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("TreeboTickets Started");

        host = getConfig().getString("host");
        port = getConfig().getInt("port");
        database = getConfig().getString("database");
        username = getConfig().getString("username");
        password = getConfig().getString("password");


        try {
            openConnection();
            Statement statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDisable() {
        getPluginLoader().disablePlugin(this);
        System.out.println("TreeboTickets Stopped");
    }


    private Connection connection;
    private String host, database, username, password;
    private int port;
    private String table = getConfig().getString("table");
    private String unusedColumns = "ID, MODIFIED";
    private String columns = "UUID, IGNAME, OPENED, MODIFIED, STATUS, STAFF, WORLD, X, Y, Z, TYPE, SEVERITY, DESCRIPTION, USERSTEPS, STAFFSTEPS";
    private String UUID, IGNAME, STATUS, STAFF, WORLD, TYPE, DESCRIPTION, USERSTEPS, STAFFSTEPS = "";
    private Integer ID, X, Y, Z, SEVERITY = 0;
    private String OPENED = LocalDateTime.now().toString();



    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }

    private Integer addTicketToDB(Player player, String ticketData){
        try {
            Integer output = connection.createStatement().executeUpdate("INSERT INTO " + table + "(" + columns + ") VALUES (" + ticketData + ");");
            return output;
        }
        catch (SQLException e){return 0;}
    }

    private void updateTicketInDB(Player player, String ticketData){
        try {
            connection.createStatement().executeQuery("INSERT INTO " + table + "(" + columns + ") VALUES (" + ticketData + ");");
        }
        catch (SQLException e){}
    }

    private void listenToUser(Player p){
        p.sendMessage(("XXXNETWORKNAMEXXX" + ChatColor.RED+  "Ticket System§").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage("");
    }
}