package me.shakeforprotein.treebotickets;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import me.shakeforprotein.treebotickets.Commands.Commands;
import me.shakeforprotein.treebotickets.Commands.TabComplete.*;
import me.shakeforprotein.treebotickets.Listeners.InventoryEvents.HubMenuInventoryListener;
import me.shakeforprotein.treebotickets.Listeners.InventoryEvents.TbTAGuiIndividualTicketLinks;
import me.shakeforprotein.treebotickets.Listeners.InventoryEvents.TbTAGuiListLinks;
import me.shakeforprotein.treebotickets.Listeners.InventoryEvents.TbTAGuiMainMenuLinks;
import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.Listeners.StatTracking.*;
import me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance.CleanupDatabase;
import me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance.CreateTables;
import me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance.DbKeepAlive;
import me.shakeforprotein.treebotickets.Methods.Teleports.PushToLobby;
import me.shakeforprotein.treebotickets.UpdateChecker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private OnPlayerDeath onPlayerDeath = new OnPlayerDeath(this);
    private OnPlayerKill onPlayerKill = new OnPlayerKill(this);
    private OnPlayerChangeWorld onPlayerChangeWorld = new OnPlayerChangeWorld(this);
    private OnPlayerDisconnect onPlayerDisconnect = new OnPlayerDisconnect(this);
    private HubMenuInventoryListener hubMenuInventoryListener = new HubMenuInventoryListener(this);
    //private Bstats bstats = new Bstats(this);


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
    private Discord discord = new Discord(this);
    private GetStat getStat = new GetStat(this);
    private Hub hub = new Hub(this);
    private InfoCommand infoCommand = new InfoCommand(this);


    //Tab Completers
    private TbTicketTabComplete tbTicketTabComplete = new TbTicketTabComplete(this);
    private TbtaTabComplete tbtaTabComplete = new TbtaTabComplete(this);
    private TbTicketAdminTabComplete tbTicketAdminTabComplete = new TbTicketAdminTabComplete(this);
    private ReviewTabComplete reviewTabComplete = new ReviewTabComplete(this);
    private InfoTabComplete infoTabComplete = new InfoTabComplete(this);


    //Method Classes
    private DbKeepAlive dbKeepAlive = new DbKeepAlive(this);
    private CleanupDatabase cleanupDatabase = new CleanupDatabase(this);
    private PushToLobby pushToLobby = new PushToLobby(this);
    private CreateTables createTables = new CreateTables(this);

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
        this.discord = new Discord(this);
        this.getStat = new GetStat(this);
        this.hub = new Hub(this);
        this.infoCommand = new InfoCommand(this);
        //this.bstats = new Bstats(this);
        //Register Commands to Executors
        //this.getCommand("hub1").setExecutor(hub);
        this.getCommand("tbticket").setExecutor(tbTicket);
        this.getCommand("tbticket").setTabCompleter(tbTicketTabComplete);
        this.getCommand("ticket").setTabCompleter(tbTicketTabComplete);
        this.getCommand("tbta").setExecutor(tbta);
        this.getCommand("tbta").setTabCompleter(tbtaTabComplete);
        this.getCommand("tbticketadmin").setExecutor(tbTicketAdmin);
        this.getCommand("tbticketadmin").setTabCompleter(tbTicketAdminTabComplete);
        this.getCommand("review").setExecutor(review);
        this.getCommand("review").setTabCompleter(reviewTabComplete);
        this.getCommand("idea").setExecutor(idea);
        this.getCommand("lobby").setExecutor(serverTransfers);
        this.getCommand("survival").setExecutor(serverTransfers);
        this.getCommand("creative").setExecutor(serverTransfers);
        this.getCommand("comp").setExecutor(serverTransfers);
        this.getCommand("plots").setExecutor(serverTransfers);
        this.getCommand("hardcore").setExecutor(serverTransfers);
        this.getCommand("prison").setExecutor(serverTransfers);
        this.getCommand("games").setExecutor(serverTransfers);
        this.getCommand("skyhub").setExecutor(serverTransfers);
        this.getCommand("skyblock").setExecutor(serverTransfers);
        this.getCommand("skygrid").setExecutor(serverTransfers);
        this.getCommand("acidislands").setExecutor(serverTransfers);
        this.getCommand("caveblock").setExecutor(serverTransfers);
        this.getCommand("restarttimed").setExecutor(restartTimed);
        this.getCommand("remoteexecute").setExecutor(remoteExecute);
        this.getCommand("multipleCommands").setExecutor(multipleCommands);
        this.getCommand("onHere").setExecutor(onHere);
        this.getCommand("seen").setExecutor(onHere);
        this.getCommand("discord").setExecutor(discord);
        this.getCommand("getstat").setExecutor(getStat);

        File listFile = new File(getDataFolder(), File.separator + "infoList.yml");
        FileConfiguration infoList = YamlConfiguration.loadConfiguration(listFile);

        for(String item: infoList.getKeys(false)){
            BukkitCommand item2 = new BukkitCommand(item) {
                @Override
                public boolean execute(CommandSender commandSender, String s, String[] strings) {
                        Bukkit.dispatchCommand(commandSender, "tinfo " + item);
                    return false;
                }

            };
            registerNewCommand("info", item2);
        }
        this.getCommand("tinfo").setExecutor(infoCommand);
        this.getCommand("tinfo").setTabCompleter(infoTabComplete);


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
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerKill(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerChangeWorld(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(this), this);
        getServer().getPluginManager().registerEvents(new HubMenuInventoryListener (this), this);




        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        for(String player : getConfig().getConfigurationSection("players").getKeys(false)){
            getConfig().set("players." + player + ".ticketstate", 0);
        }
        saveConfig();
        defineMobList();
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
            int doesNothing = 1;
        }

        dbKeepAlive.dbKeepAlive();
        if(getConfig().getString("serverName").equalsIgnoreCase("Sky")){
            createTables.createServerStatsTable("BSkyBlock");
            createTables.createServerStatsTable("AcidIsland");
            createTables.createServerStatsTable("SkyGrid");
            createTables.createServerStatsTable("CaveBlock");
        }
        else{createTables.createServerStatsTable(getConfig().getString("serverName"));}
        if(getServer().getName().equalsIgnoreCase(getConfig().getString("lobbyServerName"))){
            cleanupDatabase.cleanupDatabase(getConfig().getInt("maxClosedTickets"));
        }


        if(getConfig().getString("serverName").equalsIgnoreCase("hub")){
                File staffFile = new File(getDataFolder(), "staffList.yml");
                FileConfiguration staffList = YamlConfiguration.loadConfiguration(staffFile);

                int delTable;
                int populateStaff;
                try {
                    delTable = connection.createStatement().executeUpdate("DELETE FROM `tickets_stafflist`");
                } catch (SQLException e) {
                    System.out.println("Encountered " + e.toString() + " during genericQuery()");
                    makeLog(e);
                }

                for (String item : staffList.getKeys(false)) {
                    String staff = staffList.getString(item);
                    try{
                    populateStaff = connection.createStatement().executeUpdate("INSERT INTO `tickets_stafflist` ('IGNAME') VALUES ('" + staff + "')");
                }
                    catch (SQLException err){}
                }
        }
        System.out.println("TreeboTickets Started");
    }
    public String ontimetable = getConfig().getString("ontimetable");


    @Override
    public void onDisable() {
        saveConfig();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if ((getConfig().getString("serverName") != null) && (!getConfig().getString("serverName)").equalsIgnoreCase("hub"))) {
                player.sendMessage("Server is going down for restart, moving you to Hub");
            } else {
                player.sendMessage("Server is going down for restart, moving you to Survival");
            }
        }
        pushToLobby.pushToLobby();
        try{connection.close();}
        catch(SQLException e){makeLog(e);}
        System.out.println("TreeboTickets Stopped");
        getPluginLoader().disablePlugin(this);
    }


    public Connection connection;
    public ArrayList<String> mobList = new ArrayList<>();
    private String host, database, username, password;
    private int port;
    public String table = getConfig().getString("table");
    public String unusedColumns = "ID, MODIFIED";
    public String columns = "`UUID`, `IGNAME`, `OPENED`, `STATUS`, `STAFF`, `SERVER`,`WORLD`, `X`, `Y`, `Z`, `TYPE`, `SEVERITY`, `DESCRIPTION`, `USERSTEPS`, `STAFFSTEPS`, `ATTN`, 'ACTUALCOMMAND'";
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
        if (p.hasPermission("tbtickets.mod.view")) {
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
        System.out.println("Creating new log folder - " + new File (this.getDataFolder() + File.separator + "logs").mkdir());
        String dateTimeString = LocalDateTime.now().toString().replace(":", "_").replace("T","__");
        File file = new File(this.getDataFolder() + File.separator + "logs" + File.separator + dateTimeString + "-" + tr.getCause() + ".log");
        try {
            PrintStream ps = new PrintStream(file);
            tr.printStackTrace(ps);
            System.out.println(this.getDescription().getName() + " - " + this.getDescription().getVersion() + "Encountered Error of type: " + tr.getCause());
            System.out.println("A log file has been generated at " + this.getDataFolder() + File.separator + "logs" + File.separator + dateTimeString + "-" + tr.getCause() + ".log");
            ps.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error creating new log file for " + getDescription().getName() + " - " + getDescription().getVersion());
            System.out.println("Error was as follows");
            System.out.println(e.getMessage());
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

    public String getServerName(Entity e){
        String server = getConfig().getString("serverName");
        if(server.toLowerCase().contains("sky")){
            server = e.getWorld().getName().split("_")[0].split("-")[0];
        }
        return server;
    }



    private void defineMobList(){
        mobList.add("SKELETON_HORSE");
        mobList.add("WITHER_SKELETON");
        mobList.add("CAVE_SPIDER");
        mobList.add("ELDER_GUARDIAN");
        mobList.add("PIG_ZOMBIE");
        mobList.add("ZOMBIE PIGMAN");
        mobList.add("ZOMBIE_VILLAGER");
        mobList.add("IRON_GOLEM");
        mobList.add("SNOW_GOLEM");
        mobList.add("ENDER_DRAGON");
        mobList.add("TRADER_LLAMA");
        mobList.add("WANDERING_TRADER");
        mobList.add("SKELETON HORSE");
        mobList.add("WITHER SKELETON");
        mobList.add("CAVE SPIDER");
        mobList.add("ELDER GUARDIAN");
        mobList.add("PIG ZOMBIE");
        mobList.add("WITHER");
        mobList.add("ZOMBIE VILLAGER");
        mobList.add("IRON GOLEM");
        mobList.add("SNOW GOLEM");
        mobList.add("ENDER DRAGON");
        mobList.add("WITHER BOSS");
        mobList.add("TRADER LLAMA");
        mobList.add("WANDERING TRADER");
        mobList.add("MAGMA_CUBE");
        mobList.add("MAGMA CUBE");
        mobList.add("MAGMACUBE");
        mobList.add("MOOSHROOM");
        mobList.add("MUSHROOM_COW");
        mobList.add("MUSHROOM COW");
        mobList.add("COW");
        mobList.add("GIANT");
        mobList.add("CREEPER");
        mobList.add("BAT");
        mobList.add("CHICKEN");
        mobList.add("COD");
        mobList.add("DONKEY");
        mobList.add("HORSE");
        mobList.add("MULE");
        mobList.add("OCELOT");
        mobList.add("PARROT");
        mobList.add("PIG");
        mobList.add("RABBIT");
        mobList.add("SHEEP");
        mobList.add("SALMON");
        mobList.add("SQUID");
        mobList.add("TURTLE");
        mobList.add("TROPICAL_FISH");
        mobList.add("VILLAGER");
        mobList.add("PUFFERFISH");
        mobList.add("DOLPHIN");
        mobList.add("LLAMA");
        mobList.add("POLARBEAR");
        mobList.add("POLAR BEAR");
        mobList.add("POLAR_BEAR");
        mobList.add("WOLF");
        mobList.add("ENDERMAN");
        mobList.add("SPIDER");
        mobList.add("GUARDIAN");
        mobList.add("PHANTOM");
        mobList.add("SILVERFISH");
        mobList.add("SLIME");
        mobList.add("DROWNED");
        mobList.add("HUSK");
        mobList.add("ZOMBIE");
        mobList.add("SKELETON");
        mobList.add("STRAY");
        mobList.add("BLAZE");
        mobList.add("GHAST");
        mobList.add("MAGMACUBE");
        mobList.add("ENDERMITE");
        mobList.add("SHULKER");
        mobList.add("EVOKER");
        mobList.add("VINDICATOR");
        mobList.add("VEX");
        mobList.add("WITCH");
        mobList.add("CAT");
        mobList.add("PANDA");
        mobList.add("PILLAGER");
        mobList.add("RAVAGER");
        mobList.add("ILLUSIONER");
        mobList.add("KILLER BUNNY");
    }


    private void registerNewCommand(String fallback, BukkitCommand command) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(fallback, command);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }
}