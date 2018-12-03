package me.shakeforprotein.treebotickets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   Coordinates -   Status");

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
                        p.sendMessage(ChatColor.WHITE + "" +  tId + "  -   " + tPlayer + "    -   " +  tX + " " + tY + " " + tZ + "   -   " + tStatus);
                    }
                    p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
                }
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




}