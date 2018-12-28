package me.shakeforprotein.treebotickets;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public final class TreeboTickets extends JavaPlugin {


    private PlayerInput pi;
    private Commands cmds;

    public TreeboTickets() {
    }

    private UpdateChecker uc;
    public BungeeChannelApi api = BungeeChannelApi.of(this);

    @Override
    public void onEnable() {
        this.cmds = new Commands(this);
        this.getCommand("tbticket").setExecutor(cmds);
        this.getCommand("tbta").setExecutor(cmds);
        this.getCommand("tbticketadmin").setExecutor(cmds);
        this.getCommand("idea").setExecutor(cmds);
        this.getCommand("review").setExecutor(cmds);
        this.getCommand("reviewview").setExecutor(cmds);
        this.getCommand("reviewlist").setExecutor(cmds);
        this.getCommand("reviewclose").setExecutor(cmds);
        this.getCommand("reviewstats").setExecutor(cmds);
        this.getCommand("reviewupdate").setExecutor(cmds);
        this.getCommand("reviewtp").setExecutor(cmds);
        this.getCommand("lobby").setExecutor(cmds);
        this.getCommand("survival").setExecutor(cmds);
        this.getCommand("creative").setExecutor(cmds);
        this.getCommand("plots").setExecutor(cmds);
        this.getCommand("hardcore").setExecutor(cmds);
        this.getCommand("prison").setExecutor(cmds);
        this.getCommand("games").setExecutor(cmds);
        this.getCommand("skyblock").setExecutor(cmds);
        this.getCommand("acidislands").setExecutor(cmds);
        this.getCommand("test").setExecutor(cmds);
        this.getCommand("restarttimed").setExecutor(cmds);
        this.getCommand("remotereceive").setExecutor(cmds);
        this.getCommand("onHere").setExecutor(cmds);

        getServer().getPluginManager().registerEvents(new PlayerInput(this), this);
        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        saveConfig();
        this.uc = new UpdateChecker(this);
        uc.getCheckDownloadURL();
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

        dbKeepAlive();
        System.out.println("TreeboTickets Started");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                pullBack();
            }
        }, 200L);

    }


    @Override
    public void onDisable() {
        saveConfig();
        for(Player player : Bukkit.getServer().getOnlinePlayers())
        {
            logConnection(player.getUniqueId(), player.getName(), "OFF", LocalDateTime.now().toString());
            if(!getConfig().getString("serverName)").equalsIgnoreCase("hub")){
                player.sendMessage("Server is going down for restart, moving you to Hub");
                api.connectOther(player.getName(), "hub");
            }
            else {
                player.sendMessage("Server is going down for restart, moving you to Survival");
                api.connectOther(player.getName(),"survival");
            }
        }
        getPluginLoader().disablePlugin(this);
        System.out.println("TreeboTickets Stopped");
    }


    private Connection connection;
    private String host, database, username, password;
    private int port;
    public String table = getConfig().getString("table");
    public String unusedColumns = "ID, MODIFIED";
    public String columns = "`UUID`, `IGNAME`, `OPENED`, `STATUS`, `STAFF`, `SERVER`,`WORLD`, `X`, `Y`, `Z`, `TYPE`, `SEVERITY`, `DESCRIPTION`, `USERSTEPS`, `STAFFSTEPS`";
    public String UUID, IGNAME, STATUS, STAFF, WORLD, TYPE, DESCRIPTION, USERSTEPS, STAFFSTEPS = "";
    public Integer ID, X, Y, Z, SEVERITY = 0;
    public String OPENED = LocalDateTime.now().toString();
    public String baseInsert = "INSERT INTO `" + table + "`(" + columns + ") VALUES (XXXVALUESPLACEHOLDERXXX);";


    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }

    public void addTicketToDB(Player p, String ticketData) {
        try {
            String output = "" + connection.createStatement().executeUpdate(baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
            ResultSet response = connection.createStatement().executeQuery("SELECT * FROM `tickets`WHERE `IGNAME`=\"" + p.getName() +"\" ORDER BY id DESC LIMIT 1");
            int tID = 0;
            while (response.next()){
                tID = response.getInt("ID");
                p.sendMessage("Your Ticket number is " +tID+ ". Use /ticket view " +tID+ " to view any updates");
            }
            p.sendMessage(ChatColor.GREEN + "Your ticket has been successfully submitted");
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during addTicketToDB()");
        }
    }

    public void genericQuery(Player p, String query) {
        ResultSet response;
        String output = "";
        try {
            response = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
            makeLog(e);
        }
    }

    public void genericQuery(String query) {
        ResultSet response;
        String output = "";
        try {
            response = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
            makeLog(e);
        }
    }

    public String listTickets(Player p, String query) {
        ResultSet response;
        String output = "";
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Status");

        try {
            response = connection.createStatement().executeQuery(query);
            while (response.next()) {
                String tPlayer = response.getString("IGNAME");
                if (tPlayer.equalsIgnoreCase(p.getName())) {
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tStatus = response.getString("STATUS");
                    if (tStatus.equalsIgnoreCase("OPEN")) {
                        tStatus = ChatColor.GREEN + tStatus + ChatColor.WHITE;
                    }
                    if (tStatus.equalsIgnoreCase("closed")) {
                        tStatus = ChatColor.RED + tStatus + ChatColor.WHITE;
                    }
                    String tWorld = response.getString("WORLD");
                    p.sendMessage(ChatColor.WHITE + "" + tId + "  -   " + tPlayer + "    -   " + tWorld + "     -   " + tX + " " + tY + " " + tZ + "   -   " + tStatus);
                }
            }
            p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
            makeLog(e);
        }
        return output;
    }

    public String placeholderParser(String input, Player p) {

        String output = input.
                replace("XXXPLAYERXXX", p.getName()).
                replace("XXXNETWORKNAMEXXX", getConfig().getString("networkName"));

        return output;
    }


    public void startTicketLogic(Player p) {
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage("Please enter");
        p.sendMessage(ChatColor.GOLD + "1 " + ChatColor.RESET + "-" + ChatColor.RED + " For Server related issues");
        p.sendMessage(ChatColor.GOLD + "2 " + ChatColor.RESET + "-" + ChatColor.RED + " For Griefer related issues");
        p.sendMessage(ChatColor.GOLD + "3 " + ChatColor.RESET + "-" + ChatColor.RED + " For Other issues");
        p.sendMessage("or enter 'cancel' at any time to stop creating a ticket");
        getConfig().set("players." + p.getName() + ".ticketstate", 1);
        saveConfig();
    }


    public void getTicket(Player p, Integer t) {
        int tId = -1;
        String tPlayer = "";
        String tCoords = "";
        String tDesc = "";
        String tStaffS = "";
        String tUserS = "";
        String tStatus = "";
        String tOpened = "";
        String tModified = "";
        String tServer = "";
        String tWorld = "";

        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()) {
                tId = response.getInt("ID");
                tPlayer = response.getString("IGNAME");
                if (tPlayer.equalsIgnoreCase(p.getName())) {
                    tWorld = response.getString("WORLD");
                    tCoords = (response.getString("X") + " " + response.getString("Y") + " " + response.getString("Z"));
                    tDesc = response.getString("DESCRIPTION");
                    tStaffS = response.getString("STAFFSTEPS");
                    tUserS = response.getString("USERSTEPS");
                    tStatus = response.getString("STATUS");
                    tOpened = response.getDate("OPENED").toString();
                    try {
                        tModified = response.getDate("MODIFIED").toString();
                    } catch (NullPointerException e) {
                        tModified = "BLANK VALUE";

                    }
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                    p.sendMessage(ChatColor.GREEN + "Opened by Player: " + ChatColor.WHITE + tPlayer);
                    p.sendMessage(ChatColor.GREEN + "Status: " + ChatColor.WHITE + tStatus);
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
                } else {
                    p.sendMessage(ChatColor.RED + "Sorry but that ticket does not belong to you.");
                }
            }
        } catch (SQLException e) {
            // p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during getTicket()");
            getConfig().set("lastStackTrace", e.getStackTrace());
            makeLog(e);
        }
    }

    public void closeTicket(Player p, Integer t) {
        int tId = -1;
        String tPlayer = "";

        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            while (response.next()) {
                tId = response.getInt("ID");
                tPlayer = response.getString("IGNAME");
                if (tPlayer.trim().equalsIgnoreCase(p.getName().trim())) {
                    connection.createStatement().executeUpdate("UPDATE `" + table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                    p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");
                } else {
                    p.sendMessage(ChatColor.RED + "This is not your ticket to close");
                }
            }
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during closeTicket()");
            makeLog(e);
        }
    }

    public String staffList(Player p, String query) {
        ResultSet response;
        String output = "";
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Assigned    -   Status");

        try {
            response = connection.createStatement().executeQuery(query);
            while (response.next()) {
                String tStaff = response.getString("STAFF");
                String tPlayer = response.getString("IGNAME");

                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");
                    p.sendMessage(ChatColor.WHITE + "" + tId + "  -   " + tPlayer + "    -   " + tWorld + "    -   " + tX + " " + tY + " " + tZ + "   -   " + tStaff + "   -   " + tStatus);

            }
            p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong getting statistics");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
            makeLog(e);
        }
        return output;
    }

    public void staffCloseTicket(Player p, int t) {
        //TODO CLOSETICKET STAFF
        if (p.hasPermission("tbtickets.close.any")) {
            int tId = -1;
            String tPlayer = "";

            ResultSet response;
            try {
                response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    tId = response.getInt("ID");
                    connection.createStatement().executeUpdate("UPDATE `" + table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                    p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");

                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffCloseTicket()");
                makeLog(e);
            }
        }
    }


    public void staffViewTicket(Player p, int t) {
        //TODO VIEW TICKET STAFF
        if (p.hasPermission("tbtickets.view.any")) {
            int tId = -1;
            String tPlayer = "";
            String tCoords = "";
            String tDesc = "";
            String tStaffS = "";
            String tStaff = "";
            String tUserS = "";
            String tStatus = "";
            String tOpened = "";
            String tModified = "";
            String tWorld = "";
            ResultSet response;
            try {
                response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    tStaff = response.getString("STAFF");
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
                    } catch (NullPointerException e) {
                        tModified = "BLANK VALUE";
                    }

                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                    if (tStatus.equalsIgnoreCase("OPEN")) {
                        p.sendMessage(ChatColor.GREEN + "Ticket Status: " + ChatColor.GOLD + tStatus);
                    } else if (tStatus.equalsIgnoreCase("closed")) {
                        p.sendMessage(ChatColor.GREEN + "Ticket Status: " + ChatColor.BLUE + tStatus);
                    } else {
                        p.sendMessage(ChatColor.GREEN + "Ticket Status: " + ChatColor.WHITE + tStatus);
                    }
                    p.sendMessage(ChatColor.GREEN + "Assigned To: " + ChatColor.WHITE + tStaff);
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
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffViewTicket()");
                makeLog(e);
            }
        } else {
            p.sendMessage(ChatColor.RED + "You lack the sufficient permissions.");
        }
    }


    public void staffTP(Player p, String query, int t) {
        ResultSet response;
        try {
            response = connection.createStatement().executeQuery(query);
            while (response.next()) {
                String tPlayer = response.getString("IGNAME");
                if (p.hasPermission("tbtickets.view.any")) {
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tServer = response.getString("SERVER");
                    String tCoords = tX + " " + tY + " " + tZ;
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");
                    if (!tServer.equalsIgnoreCase(getConfig().getString("serverName"))) {
                        p.sendMessage("As you were on the wrong server, you will need to repeat the command.");
                        p.sendMessage("Changing your server for you now.");
                        api.connectOther(p.getName(), tServer);
                    } else {
                        p.teleport(new Location(Bukkit.getWorld(tWorld), tX, tY, tZ));
                    }
                }
            }
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during staffTP()");
            makeLog(e);

        }
    }


    public void staffClaim(Player p, int t) {
        if (p.hasPermission("tbtickets.view.any")) {
            String query = ("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            int response2 = 0;
            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    if (tStaff.equalsIgnoreCase("unassigned")) {
                        String query2 = ("UPDATE  `" + table + "` SET  `STAFF` =  '" + p.getName() + "' WHERE  `ID` =" + tId);
                        try {
                            connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " Claimed.");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during staffClaim()");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That ticket is already assigned to " + tStaff);
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffClaim()");
                makeLog(e);

            }
        }
    }


    public void staffUnclaim(Player p, int t) {
        if (p.hasPermission("tbtickets.view.any")) {
            String query = ("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    if (tStaff.equalsIgnoreCase(p.getName())) {
                        String query2 = ("UPDATE  `" + table + "` SET  `STAFF` =  'UNASSIGNED' WHERE  `ID` =" + tId);
                        try {
                            connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " unclaimed.");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during staffUnclaim()");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That ticket is assigned to " + tStaff);
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffUnclaim()");
                makeLog(e);
            }
        }
    }


    public void staffUpdate(Player p, int t, String staffText) {
        if (p.hasPermission("tbtickets.view.any")) {
            String query = ("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            int response2 = 0;
            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    String tStaffSteps = response.getString("STAFFSTEPS");
                    String newStaffSteps = (tStaffSteps + "\n" + LocalDateTime.now() + " - " + p.getName() + " - " + staffText);
                    if (tStaff.equalsIgnoreCase(p.getName())) {
                        String query2 = ("UPDATE  `" + table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + "' WHERE  `ID` =" + tId);
                        try {
                            connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " updated.");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during staffClaim()");
                            makeLog(e);
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That ticket is assigned to " + tStaff + " so you are not able to update it.");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffUpdate()");
                makeLog(e);

            }
        }
    }


    public String adminListStaff(Player p, String query, String staff) {
        ResultSet response;
        String output = "";
        if (p.hasPermission("tbtickets.admin")) {
            p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
            p.sendMessage(ChatColor.AQUA + "Assigned to " + staff);
            p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Status");

            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    String tPlayer = response.getString("IGNAME");
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");
                    p.sendMessage(ChatColor.WHITE + "" + tId + "  -   " + tPlayer + "    -   " + tWorld + "    -   " + tX + " " + tY + " " + tZ + "   -   " + tStatus);
                }

                p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during genericQuery()");
                makeLog(e);

            }
        }
        return output;
    }

    public void adminCloseTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.admin")) {
            int tId = -1;
            String tPlayer = "";

            ResultSet response;
            try {
                response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    tId = response.getInt("ID");
                    connection.createStatement().executeUpdate("UPDATE `" + table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                    p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");

                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during adminCloseTicket()");
                makeLog(e);
            }
        }
    }


    public void adminDeleteTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.admin")) {
            int tId = -1;
            String tPlayer = "";

            int response;
            try {
                response = connection.createStatement().executeUpdate("DELETE FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
                p.sendMessage("Ticket " + t + " deleted. This action cannot be undone.");
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during adminDeleteTicket()");
                makeLog(e);
            }
        } else {
            p.sendMessage("You are not a ticket administrator");
        }
    }


    public void adminAssign(Player p, int t, String staffName) {
        if (p.hasPermission("tbtickets.admin")) {
            String query = ("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            int response2 = 0;
            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    if (1 == 1) {
                        String query2 = ("UPDATE  `" + table + "` SET  `STAFF` =  '" + staffName + "' WHERE  `ID` =" + tId);
                        try {
                            connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " assigned to " + staffName + ".");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during adminAssign()");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That ticket is already assigned to " + tStaff);
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffClaim()");
                makeLog(e);
            }
        }
    }


    public void adminUpdate(Player p, int t, String staffText) {
        if (p.hasPermission("tbtickets.admin")) {
            String query = ("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            int response2 = 0;
            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    String tStaffSteps = response.getString("STAFFSTEPS");
                    String newStaffSteps = (tStaffSteps + "\n" + LocalDateTime.now() + " - " + p.getName() + " - " + staffText);
                    if (1 == 1) {
                        String query2 = ("UPDATE  `" + table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + "' WHERE  `ID` =" + tId);
                        try {
                            connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " updated.");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during staffClaim()");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That ticket is assigned to " + tStaff + " so you are not able to update it.");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during adminUpdate()");
                makeLog(e);
            }
        }
    }

    public void staffStats(Player p) {

        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= '" + p.getName() + "'");
            while (response.next()) {
                if(!p.hasPermission("tbtickets.admin")){p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));}
                /*p.sendMessage(ChatColor.UNDERLINE + "Your" + ChatColor.RESET + " TOTAL assigned tickets: " + response.getInt("TOTAL"));
                */
            }
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= '" + p.getName() + "' AND STATUS='OPEN'");
            while (response.next()) {
                p.sendMessage(ChatColor.UNDERLINE + "Your" + ChatColor.RESET + ChatColor.RED + " OPEN" + ChatColor.RESET + " Tickets: " + response.getInt("TOTAL"));
            }
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= '" + p.getName() + "' AND STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage(ChatColor.UNDERLINE + "Your" + ChatColor.RESET + ChatColor.GREEN + " CLOSED" + ChatColor.RESET + " Tickets: " + response.getInt("TOTAL"));
            }*/
        } catch (SQLException e) {
            // p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during staffStats()");
            makeLog(e);
        }
    }

    public void adminStats(Player p) {

        ResultSet response;
        try {
            p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE ID!='0'");
            while (response.next()) {
                p.sendMessage("Total Tickets:" + response.getInt("TOTAL"));
            }*/
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF!='UNASSIGNED'");
            while (response.next()) {
                p.sendMessage("Assigned Tickets:" + response.getInt("TOTAL"));
            }*/
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED'");
            while (response.next()) {
                p.sendMessage("UnAssigned Tickets:" + response.getInt("TOTAL"));
            }
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STATUS='OPEN'");
            while (response.next()) {
                p.sendMessage("Open Tickets:" + response.getInt("TOTAL"));
            }
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage("Closed Tickets:" + response.getInt("TOTAL"));
            }*/
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during AdminStats()");
            makeLog(e);
        }
    }

    public void serverSwitch(Player p, String server) {
        if (p.hasPermission("tbtickets.server." + getConfig().getString("serverName"))) {
            if (!server.equalsIgnoreCase(getConfig().getString("serverName"))) {
                p.sendMessage("Attempting to send you to " + server);
                api.connectOther(p.getName(), server);
            } else {
                p.sendMessage("You are already on the " + server + " server.");
                p.sendMessage("please use /hub to navigate from here");
            }
        }
    }

    private void dbKeepAlive() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
                try {

                    genericQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID!='0'");
                    dbKeepAlive();
                } catch (NullPointerException e) {
                    makeLog(e);
                }
            }
        }, 36000L);
    }

    private void makeLog(Exception tr) {
        File file = new File(LocalDateTime.now() + ".log");
        try {
            PrintStream ps = new PrintStream(file);
            tr.printStackTrace(ps);
            ps.close();
        } catch (FileNotFoundException e) {
        }
    }


    public void builderViewTicket(Player p, int t) {
        //TODO VIEW TICKET STAFF
        if (p.hasPermission("tbtickets.builder")) {
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
                while (response.next()) {
                    String assigned = response.getString("STAFF");
                    if (assigned.equalsIgnoreCase("Builders")) {
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
                        } catch (NullPointerException e) {
                            tModified = "BLANK VALUE";
                        }

                        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
                        p.sendMessage(ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                        if (tStatus.equalsIgnoreCase("OPEN")) {
                            p.sendMessage(ChatColor.GREEN + "Ticket Status: " + ChatColor.GOLD + tStatus);
                        } else if (tStatus.equalsIgnoreCase("closed")) {
                            p.sendMessage(ChatColor.GREEN + "Ticket Status: " + ChatColor.BLUE + tStatus);
                        } else {
                            p.sendMessage(ChatColor.GREEN + "Ticket Status: " + ChatColor.WHITE + tStatus);
                        }
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
                    } else {
                        p.sendMessage("You can't use this command");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during builderViewTicket()");
                makeLog(e);
            }
        } else {
            p.sendMessage(ChatColor.RED + "You lack the sufficient permissions.");
        }
    }

    public void builderClose(Player p, String t) {
        if (p.hasPermission("tbtickets.builder")) {
            int tId = -1;
            String tPlayer = "";

            ResultSet response;
            try {
                response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    String tstaff = response.getString("STAFF");
                    if (tstaff.equalsIgnoreCase("Builders")) {
                        tId = response.getInt("ID");
                        connection.createStatement().executeUpdate("UPDATE `" + table + "` SET STATUS = 'CLOSED' WHERE ID =" + tId);
                        p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Closed.");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffCloseTicket()");
                makeLog(e);
            }
        }
    }


    public void builderStats(Player p) {
        ResultSet response;
        try {
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= 'Builders'");
            while (response.next()) {
                p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
                p.sendMessage("TOTAL Review tickets: " + response.getInt("TOTAL"));
            }
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= 'Builders' AND STATUS='OPEN'");
            while (response.next()) {
                p.sendMessage("OPEN ReviewTickets: " + response.getInt("TOTAL"));
            }
            response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF= 'Builders' AND STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage("CLOSED review Tickets: " + response.getInt("TOTAL"));
            }
        } catch (SQLException e) {
            // p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during BuilderStats()");
            makeLog(e);
        }
    }

    public void builderUpdate(Player p, int t, String staffText) {
        if (p.hasPermission("tbtickets.builder")) {
            String query = ("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
            ResultSet response;
            try {
                response = connection.createStatement().executeQuery(query);
                while (response.next()) {
                    int tId = response.getInt("ID");
                    String tStaff = response.getString("STAFF");
                    if(tStaff.equalsIgnoreCase("Builders")){
                    String tStaffSteps = response.getString("STAFFSTEPS");
                    String newStaffSteps = (tStaffSteps + "\n" + LocalDateTime.now() + " - " + p.getName() + " - " + staffText);
                    if (tStaff.equalsIgnoreCase("Builders")) {
                        String query2 = ("UPDATE  `" + table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + "' WHERE  `ID` =" + tId);
                        try {
                            connection.createStatement().executeUpdate(query2);
                            p.sendMessage("Ticket " + t + " updated.");
                        } catch (SQLException e) {
                            p.sendMessage(ChatColor.RED + "Something went wrong");
                            System.out.println("Encountered " + e.toString() + " during builderUpdate()");
                            makeLog(e);
                            }
                        }
                    }
                    else {
                        p.sendMessage(ChatColor.RED + "That ticket is assigned to " + tStaff + " so you are not able to update it.");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffUpdate()");
                makeLog(e);

            }
        }
    }
    public void builderTP(Player p, String query, int t) {
        ResultSet response;
        try {
            response = connection.createStatement().executeQuery(query);
            while (response.next()) {
                String tStaff = response.getString("STAFF");
                if (p.hasPermission("tbtickets.builder") && tStaff.equalsIgnoreCase("Builders")) {
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tServer = response.getString("SERVER");
                    String tWorld = response.getString("WORLD");
                    if (!tServer.equalsIgnoreCase(getConfig().getString("serverName"))) {
                        p.sendMessage("As you were on the wrong server, you will need to repeat the command.");
                        p.sendMessage("Changing your server for you now.");
                        api.connectOther(p.getName(), tServer);
                    } else {
                        p.teleport(new Location(Bukkit.getWorld(tWorld), tX, tY, tZ));
                    }
                }
                else {
                    p.sendMessage("Insufficient permissions");
                }
            }
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during staffTP()");
            makeLog(e);

        }
    }



    public void pullBack() {
        if (getConfig().getString("isLobbyServer").equalsIgnoreCase("false")) {
            for (String player : getConfig().getConfigurationSection("shutdownPlayerList").getKeys(false)) {
                //api.sendMessage(player, "Now attempting to return you to the " + getConfig().getString("serverName") + " server");
                // api.connectOther(player, getConfig().getString("serverName"));
                getConfig().set("shutdownPlayerList." + player, null);
                saveConfig();
            }
        }
    }

    public void pushToLobby() {
        if (getConfig().getString("isLobbyServer").equalsIgnoreCase("false")) {
            for (Player p : getServer().getOnlinePlayers()) {
                getConfig().set("shutdownPlayerList." + p.getName(), p.getName());
                p.sendMessage("This server is restarting.");
                p.sendMessage("Moving you temporarily to the Lobby");
                //p.sendMessage("You will be automatically moved back when the restart is complete");
                serverSwitch(p, "hub");
            }
        }
    }

    public void logConnection(UUID pUUID, String pIGN, String pOnOff, String pCreated) {
        int response;
        String output = "";
        try {
           String query = "INSERT INTO `ontime`(`UUID`, `IGNAME`, `ONOFF`, `CREATED`, `SERVER`) VALUES (\""+pUUID+"\",\""+pIGN+"\",\""+pOnOff+"\",\""+pCreated+"\",\"" + Bukkit.getServer().getName() + "\")";
           System.out.println(query);
            response = connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during logConnection()");
            makeLog(e);
        }
    }

    public void calculateConnection(Player p, UUID pUUID){
        String query = "SELECT * FROM `ontime` WHERE `UUID`=\""+pUUID+"\" AND `SERVER`=\"" + Bukkit.getServer().getName() + "\" AND `ONOFF`=\"ON\" ORDER BY ID DESC LIMIT 1";
        String query2 = "SELECT * FROM `ontime` WHERE `UUID`=\""+pUUID+"\" AND `SERVER`=\"" + Bukkit.getServer().getName() + "\" AND `ONOFF`=\"OFF\" ORDER BY ID DESC LIMIT 1";
        ResultSet response, response2;
        try {
            response = connection.createStatement().executeQuery(query);
            response2 = connection.createStatement().executeQuery(query2);
            while (response.next()) {
                String DT = response.getString("CREATED");
                p.sendMessage(DT);
            }
            while (response2.next()) {
                String DT = response2.getString("CREATED");
                p.sendMessage(DT);
            }



        }
        catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during calculateConnection()");
            makeLog(e);
        }
    }


    public void tbTicketHelp(Player p){
        p.sendMessage(ChatColor.RED + "Incorrect usage. Please try one of the following.");
        p.sendMessage(ChatColor.GOLD + "/tbticket open  -  Creates a new ticket");
        p.sendMessage(ChatColor.GOLD + "/tbticket close <ticket_number> - Closes the ticket with the ticket number if it is your own");
        p.sendMessage(ChatColor.GOLD + "/tbticket view <ticket_number>  -  Shows the detail of one your ticket with the listed ticket_number");
        p.sendMessage(ChatColor.GOLD + "/tbticket list  -  Lists all tickets you've created");
    }

    public void tbtaHelp(Player p){
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
        p.sendMessage(ChatColor.GOLD + "/tbta list <assigned|unnassigned|open|closed|idea>  -  Lists all tickets assigned to you");
        p.sendMessage(ChatColor.GOLD + "/tbta view <ticket_number>  -  Displays details on specific ticket");
        p.sendMessage(ChatColor.GOLD + "/tbta close <ticket_number>  -  Close ticket with id");
        p.sendMessage(ChatColor.GOLD + "/tbta <claim|unclaim> <ticket_number>  -  Assigns an unassigned ticket to yourself");
        p.sendMessage(ChatColor.GOLD + "/tbta tp <ticket_number>  -  Teleport to location of ticket number");
        p.sendMessage(ChatColor.GOLD + "/tbta update <ticket_number>  <your message> -  Updates a tickets staff steps data.  Remember this can be seen by the ticket submitter");
    }

    public void tbTicketAdminHelp(Player p){
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + getConfig().getString("networkName")));
        p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin reload");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin list <assigned|unnassigned|open|closed> - Lists tickets of the selected type");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin staffList <staff_name> - Lists tickets assigned to particular staff member");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin assign <ticket_number> <staff_name> -  Displays details on specific ticket");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin close <ticket_number>  -  Close ticket with id");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin delete <ticket_number>  -  Assigns an unassigned ticket to yourself");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin update <ticket_number> <your update>  -  Updates the staff steps data for this ticket (This can be seen by anyone who can view the ticket)");
        p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin stats");
    }
}