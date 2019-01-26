package me.shakeforprotein.treebotickets;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import me.shakeforprotein.treebotickets.Commands.Commands;
import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance.CleanupDatabase;
import me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance.DbKeepAlive;
import me.shakeforprotein.treebotickets.Methods.Teleports.PushToLobby;
import me.shakeforprotein.treebotickets.UpdateChecker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDateTime;

import me.shakeforprotein.treebotickets.Commands.*;
import me.shakeforprotein.treebotickets.Listeners.*;

public final class TreeboTickets extends JavaPlugin {


    //Listener Classes
    private PlayerInput pi = new PlayerInput(this);
    private GuiDragStealBlock guiDragStealBlock = new GuiDragStealBlock(this);
    private LogJoin logJoin = new LogJoin(this);
    private LogLeave logLeave = new LogLeave(this);
    private NotifyHub notifyHub = new NotifyHub(this);
    private NotifyStaff notifyStaff = new NotifyStaff(this);
    private TbTAGuiIndividualTicketLinks tbTAGuiIndividualTicketLinks = new TbTAGuiIndividualTicketLinks(this);
    private TbTAGuiListLinks tbTAGuiListLinks = new TbTAGuiListLinks(this);
    private TbTAGuiMainMenuLinks tbTAGuiMainMenuLinks = new TbTAGuiMainMenuLinks(this);
    private TicketConversation ticketConversation = new TicketConversation(this);

    //Command Classes
    private Commands cmds; //not used.
    private Idea idea = new Idea(this);
    private MultipleCommands multipleCommands = new MultipleCommands(this);
    private OnHere onHere = new OnHere(this);
    private RemoteExecute remoteExecute = new RemoteExecute(this);
    private RestartTimed restartTimed = new RestartTimed(this);
    private Review review = new Review(this);
    private ServerTransfers serverTransfers = new ServerTransfers(this);
    private Tbta tbta = new Tbta(this);
    private TbTicket tbTicket = new TbTicket(this);
    private TbTicketAdmin tbTicketAdmin = new TbTicketAdmin(this);


    //Method Classes
    private DbKeepAlive dbKeepAlive = new DbKeepAlive(this);
    private CleanupDatabase cleanupDatabase = new CleanupDatabase(this);
    private PushToLobby pushToLobby = new PushToLobby(this);

    public TreeboTickets() {
    }

    private UpdateChecker uc;
    public BungeeChannelApi api = BungeeChannelApi.of(this);

    @Override
    public void onEnable() {
        //Register Command Executors
        this.cmds = new Commands(this);
        this.tbTicket = new TbTicket(this);
        this.idea = new Idea(this);
        this.multipleCommands = new MultipleCommands(this);
        this.onHere = new OnHere(this);
        this.remoteExecute = new RemoteExecute(this);
        this.restartTimed = new RestartTimed(this);
        this.review = new Review(this);
        this.serverTransfers = new ServerTransfers(this);
        this.tbta = new Tbta(this);
        this.tbTicketAdmin = new TbTicketAdmin(this);

        //Register Commands to Executors
        this.getCommand("tbticket").setExecutor(tbTicket);
        this.getCommand("tbta").setExecutor(tbta);
        this.getCommand("tbticketadmin").setExecutor(tbTicketAdmin);
        this.getCommand("idea").setExecutor(idea);
        this.getCommand("review").setExecutor(review);
        this.getCommand("lobby").setExecutor(serverTransfers);
        this.getCommand("survival").setExecutor(serverTransfers);
        this.getCommand("creative").setExecutor(serverTransfers);
        this.getCommand("comp").setExecutor(serverTransfers);
        this.getCommand("plots").setExecutor(serverTransfers);
        this.getCommand("hardcore").setExecutor(serverTransfers);
        this.getCommand("prison").setExecutor(serverTransfers);
        this.getCommand("games").setExecutor(serverTransfers);
        this.getCommand("skyblock").setExecutor(serverTransfers);
        this.getCommand("skygrid").setExecutor(serverTransfers);
        this.getCommand("acidislands").setExecutor(serverTransfers);
        this.getCommand("test").setExecutor(serverTransfers);
        this.getCommand("restarttimed").setExecutor(restartTimed);
        this.getCommand("remoteexecute").setExecutor(remoteExecute);
        this.getCommand("multipleCommands").setExecutor(multipleCommands);
        this.getCommand("onHere").setExecutor(onHere);

        //Register Listeners
        getServer().getPluginManager().registerEvents(new PlayerInput(this), this);
        getServer().getPluginManager().registerEvents(new LogJoin(this), this);
        getServer().getPluginManager().registerEvents(new LogLeave(this), this);
        getServer().getPluginManager().registerEvents(new GuiDragStealBlock(this), this);
        getServer().getPluginManager().registerEvents(new TbTAGuiIndividualTicketLinks(this), this);
        getServer().getPluginManager().registerEvents(new TbTAGuiListLinks(this), this);
        getServer().getPluginManager().registerEvents(new TbTAGuiMainMenuLinks(this), this);
        getServer().getPluginManager().registerEvents(new NotifyHub(this), this);
        getServer().getPluginManager().registerEvents(new NotifyStaff(this), this);
        getServer().getPluginManager().registerEvents(new TicketConversation(this), this);
        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        for(String player : getConfig().getConfigurationSection("players").getKeys(false)){
            getConfig().set("players." + player + ".ticketstate", 0);
        }
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

        dbKeepAlive.dbKeepAlive();
        if(getServer().getName().equalsIgnoreCase(getConfig().getString("lobbyServerName"))){
            cleanupDatabase.cleanupDatabase(getConfig().getInt("maxClosedTickets"));
        }
        System.out.println("TreeboTickets Started");
    }
    public String ontimetable = getConfig().getString("ontimetable");


    @Override
    public void onDisable() {
        saveConfig();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!getConfig().getString("serverName)").equalsIgnoreCase("hub")) {
                player.sendMessage("Server is going down for restart, moving you to Hub");
            } else {
                player.sendMessage("Server is going down for restart, moving you to Survival");
            }
        }
        pushToLobby.pushToLobby();
        try{connection.close();}
        catch(SQLException e){makeLog(e);}
        getPluginLoader().disablePlugin(this);
        System.out.println("TreeboTickets Stopped");
    }


    public Connection connection;
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


    public String placeholderParser(String input, Player p) {

        return input.replace("XXXPLAYERXXX", p.getName()).
                replace("XXXNETWORKNAMEXXX", getConfig().getString("networkName"));
    }


    public void staffReOpenTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.view.any")) {
            int tId = -1;
            String tPlayer = "";

            ResultSet response;
            try {
                response = connection.createStatement().executeQuery("SELECT * FROM `" + getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    tId = response.getInt("ID");
                    connection.createStatement().executeUpdate("UPDATE `" + table + "` SET STATUS = 'OPEN' WHERE ID =" + tId);
                    p.sendMessage(ChatColor.BLUE + "Ticket " + t + " Re-Opened.");

                }
            } catch (SQLException e) {
                p.sendMessage(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during staffReOpen()");
                makeLog(e);
            }
        }
    }


    public void makeLog(Exception tr) {
        File file = new File(LocalDateTime.now() + ".log");
        try {
            PrintStream ps = new PrintStream(file);
            tr.printStackTrace(ps);
            ps.close();
        } catch (FileNotFoundException e) {makeLog(e);
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


    public static boolean isNumeric (String str)
    {
        return str.matches("\\d+");
    }


}