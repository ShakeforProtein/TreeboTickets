package me.shakeforprotein.treebotickets;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;


public final class TreeboTickets extends JavaPlugin{



    private PlayerInput pi;
    private Commands cmds;
    public TreeboTickets(){}

    @Override
    public void onEnable() {
        this.cmds = new Commands(this);
        this.getCommand("tbticket").setExecutor(cmds);
        this.getCommand("tbta").setExecutor(cmds);
        this.getCommand("tbticketadmin").setExecutor(cmds);
        System.out.println("TreeboTickets Started");
        getServer().getPluginManager().registerEvents(new PlayerInput(this), this);
        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        saveConfig();
        host = getConfig().getString("host");
        port = getConfig().getInt("port");
        database = getConfig().getString("database");
        username = getConfig().getString("username");
        password = getConfig().getString("password");
        table = getConfig().getString("table");


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
    public String table = getConfig().getString("table");
    public String unusedColumns = "ID, MODIFIED";
    public String columns = "`UUID`, `IGNAME`, `OPENED`, `STATUS`, `STAFF`, `WORLD`, `X`, `Y`, `Z`, `TYPE`, `SEVERITY`, `DESCRIPTION`, `USERSTEPS`, `STAFFSTEPS`";
    public String UUID, IGNAME, STATUS, STAFF, WORLD, TYPE, DESCRIPTION, USERSTEPS, STAFFSTEPS = "";
    public Integer ID, X, Y, Z, SEVERITY = 0;
    public String OPENED = LocalDateTime.now().toString();
    public String baseInsert = "INSERT INTO `" + table + "`(" + columns + ") VALUES (XXXVALUESPLACEHOLDERXXX);";



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

    public String addTicketToDB(Player p, String ticketData){
        try {
            String output = "" + connection.createStatement().executeUpdate(baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
            return "Success";
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during addTicketToDB()");
            return "Fail";
        }
    }

    public String genericQuery(Player p, String query){
        ResultSet response;
        String output = "";
        try {
            response = connection.createStatement().executeQuery(query);
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
        }
        return output;
    }

    public String listTickets(Player p, String query){
        ResultSet response;
        String output = "";
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Status");

        try {
            response = connection.createStatement().executeQuery(query);
            while(response.next()){
                String tPlayer = response.getString("IGNAME");
                    if(tPlayer.equalsIgnoreCase(p.getName())){
                        int tId = response.getInt("ID");
                        int tX = response.getInt("X");
                        int tY = response.getInt("Y");
                        int tZ = response.getInt("Z");
                        String tStatus = response.getString("STATUS");
                        String tWorld = response.getString("WORLD");
                        p.sendMessage(ChatColor.WHITE + "" +  tId + "  -   " + tPlayer + "    -   " +tWorld + "     -   " +  tX + " " + tY + " " + tZ + "   -   " + tStatus);
                    }
                }
            p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
        }
        return output;
    }

    public String placeholderParser(String input, Player p){

        String output = input.
                replace("XXXPLAYERXXX", p.getName()).
                replace("XXXNETWORKNAMEXXX", getConfig().getString("networkName"));

        return output;
    }


    public void startTicketLogic(Player p){
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage("Please enter");
        p.sendMessage(ChatColor.GOLD + "1 " + ChatColor.RESET + "-" + ChatColor.RED + " For Server related issues");
        p.sendMessage(ChatColor.GOLD + "2 " + ChatColor.RESET + "-" + ChatColor.RED + " For Griefer related issues");
        p.sendMessage(ChatColor.GOLD + "3 " + ChatColor.RESET + "-" + ChatColor.RED + " For Other issues");
        p.sendMessage("or enter 'cancel' at any time to stop creating a ticket");
        getConfig().set("players." + p.getName() + ".ticketstate", 1);
        saveConfig();
    }


    public void getTicket(Player p, Integer t){
        int tId = -1;
        String tPlayer = "";
        String tCoords = "";
        String tDesc = "";
        String tStaffS = "";
        String tUserS = "";
        String tStatus = "";
        String tOpened = "";
        String tModified = "";
        String tWorld = "";

        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()){
                tId = response.getInt("ID");
                tPlayer = response.getString("IGNAME");
                if(tPlayer.equalsIgnoreCase(p.getName())){
                    tWorld = response.getString("WORLD");
                    tCoords = (response.getString("X") + " " + response.getString("Y") + " " + response.getString("Z"));
                    tDesc = response.getString("DESCRIPTION");
                    tStaffS = response.getString("STAFFSTEPS");
                    tUserS = response.getString("USERSTEPS");
                    tStatus = response.getString("STATUS");
                    tOpened = response.getDate("OPENED").toString();
                    try {
                        tModified = response.getDate("MODIFIED").toString();
                    }
                    catch(NullPointerException e){tModified = "BLANK VALUE";

                    }
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                    p.sendMessage(ChatColor.GREEN + "Opened by Player: " + ChatColor.WHITE + tPlayer);
                    p.sendMessage(ChatColor.GREEN + "Opened at: " + ChatColor.WHITE + tOpened);
                    p.sendMessage(ChatColor.GREEN + "Last Updated: " + ChatColor.WHITE + tModified);
                    p.sendMessage(ChatColor.GREEN + "On World: " + ChatColor.WHITE + tWorld);
                    p.sendMessage(ChatColor.GREEN + "At Coordinates: " + ChatColor.GOLD + tCoords);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "User Description: " + ChatColor.WHITE + tDesc);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.BLUE + "Steps taken by user: " + ChatColor.WHITE + tUserS);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Staff comments / actions: " + ChatColor.WHITE + tStaffS);
                }
                else{
                    p.sendMessage(ChatColor.RED + "Sorry but that ticket does not belong to you.");
                }
            }
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during getTicket()");
        }
    }

    public void closeTicket(Player p, Integer t){
        int tId = -1;
        String tPlayer = "";

        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()){
                tId = response.getInt("ID");
                tPlayer = response.getString("IGNAME");
                if(tPlayer.trim().equalsIgnoreCase(p.getName().trim())){
                    connection.createStatement().executeUpdate("UPDATE `tickets` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                    p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");
                }
                else{
                    p.sendMessage(ChatColor.RED + "This is not your ticket to close");
                }
            }
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during getTicket()");
        }
    }

    public String staffList(Player p, String query){
        ResultSet response;
        String output = "";
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Status");

        try {
            response = connection.createStatement().executeQuery(query);
            while(response.next()){
                String tPlayer = response.getString("IGNAME");
                if(p.hasPermission("treebotickets.view.any")){
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");
                    p.sendMessage(ChatColor.WHITE + "" +  tId + "  -   " + tPlayer + "    -   " + tWorld + "    -   " +  tX + " " + tY + " " + tZ + "   -   " + tStatus);
                }
            }
            p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
        }
        return output;
    }

    public void staffCloseTicket(Player p, int t){
        //TODO CLOSETICKET STAFF
        int tId = -1;
        String tPlayer = "";

        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()){
                tId = response.getInt("ID");
                connection.createStatement().executeUpdate("UPDATE `tickets` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");

            }
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during getTicket()");
        }
    }


    public void staffViewTicket(Player p, int t) {
        //TODO VIEW TICKET STAFF
        if(p.hasPermission("tbtickets.view.any")){
        int tId = -1;
        String tPlayer = "";
        String tCoords = "";
        String tDesc = "";
        String tStaffS = "";
        String tUserS = "";
        String tStatus = "";
        String tOpened = "";
        String tModified = "";
        String tWorld = "";
        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()){
                tId = response.getInt("ID");
                tPlayer = response.getString("IGNAME");

                    tCoords = (response.getString("X") + " " + response.getString("Y") + " " + response.getString("Z"));
                    tDesc = response.getString("DESCRIPTION");
                    tStaffS = response.getString("STAFFSTEPS");
                    tUserS = response.getString("USERSTEPS");
                    tStatus = response.getString("STATUS");
                    tOpened = response.getDate("OPENED").toString();
                    tWorld = response.getString("WORLD");
                    try {
                        tModified = response.getDate("MODIFIED").toString();
                    }
                    catch(NullPointerException e){tModified = "BLANK VALUE";

                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                    p.sendMessage(ChatColor.GREEN + "Opened by Player: " + ChatColor.WHITE + tPlayer);
                    p.sendMessage(ChatColor.GREEN + "Opened at: " + ChatColor.WHITE + tOpened);
                    p.sendMessage(ChatColor.GREEN + "Last Updated: " + ChatColor.WHITE + tModified);
                    p.sendMessage(ChatColor.GREEN + "On World: " + ChatColor.WHITE + tWorld);
                    p.sendMessage(ChatColor.GREEN + "At Coordinates: " + ChatColor.GOLD + tCoords);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "User Description: " + ChatColor.WHITE + tDesc);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.BLUE + "Steps taken by user: " + ChatColor.WHITE + tUserS);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Staff comments / actions: " + ChatColor.WHITE + tStaffS);
                }
            }
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during getTicket()");
        }
    }else {p.sendMessage(ChatColor.RED + "You lack the sufficient permissions.");}}



    public void staffTP(Player p, String query, int t){
        ResultSet response;
        p.sendMessage("Attempting to send you to location of ticket " + t + ". This will fail if the world is on another server.");
        try {
            response = connection.createStatement().executeQuery(query);
            while(response.next()){
                String tPlayer = response.getString("IGNAME");
                if(p.hasPermission("tbtickets.view.any")){
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tCoords = tX + " " + tY + " " + tZ;
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");

                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String command = "execute in minecraft:" + tWorld +" run tp " + p.getName() + " " + tCoords;
                    Bukkit.dispatchCommand(console, command);
                }
            }
        }
        catch (SQLException e){
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during staffTP()");
        }
    }

}