package me.shakeforprotein.treebotickets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.time.LocalDateTime;


public final class TreeboTickets extends JavaPlugin{



    private PlayerInput pl = new PlayerInput();
    private Commands cmds = new Commands(this, pl);

    public TreeboTickets(){TreeboTickets TT = this;}

    @Override
    public void onEnable() {
        this.getCommand("tbticket").setExecutor(cmds);
        System.out.println("TreeboTickets Started");
        Bukkit.getPluginManager().registerEvents(new PlayerInput(), this);
        host = getConfig().getString("host");
        port = getConfig().getInt("port");
        database = getConfig().getString("database");
        username = getConfig().getString("username");
        password = getConfig().getString("password");
        table = getConfig().getString("table");


 /*       try {
            openConnection();
            Statement statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }


    @Override
    public void onDisable() {
        getPluginLoader().disablePlugin(this);
        System.out.println("TreeboTickets Stopped");
    }


    private Connection connection;
    private String host, database, username, password;
    private int port;
    public String table = getConfig().getString("table");
    public String unusedColumns = "ID, MODIFIED";
    public String columns = "UUID, IGNAME, OPENED, STATUS, STAFF, WORLD, X, Y, Z, TYPE, SEVERITY, DESCRIPTION, USERSTEPS, STAFFSTEPS";
    public String UUID, IGNAME, STATUS, STAFF, WORLD, TYPE, DESCRIPTION, USERSTEPS, STAFFSTEPS = "";
    public Integer ID, X, Y, Z, SEVERITY = 0;
    public String OPENED = LocalDateTime.now().toString();



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

    private String addTicketToDB(Player p, String ticketData){
        try {
            String output = "" + connection.createStatement().executeUpdate("INSERT INTO " + table + "(" + columns + ") VALUES (" + ticketData + ");");
            return "Success";
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during addTicketToDB()");
            return "Fail";
        }
    }

    private void updateTicketInDB(Player p, String ticketData){
        try {
            connection.createStatement().executeQuery("INSERT INTO " + table + "(" + columns + ") VALUES (" + ticketData + ");");
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during updateTicketInDB()");
        }
    }

    public String placeholderParser(String input, Player p){

        String output = input.
                replace("XXXPLAYERXXX", p.getName()).
                replace("XXXNETWORKNAMEXXX", getConfig().getString("networkName"));

        return output;
    }

    public String assembleTicketData(String input){
        String output = input + ",";
        return output;
    }



}