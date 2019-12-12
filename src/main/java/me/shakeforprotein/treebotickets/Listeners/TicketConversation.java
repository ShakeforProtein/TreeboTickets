package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Methods.CreateTicket.CreateTicket;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketConversation implements Listener {

    private TreeboTickets pl;
    private CreateTicket createTicket;

    public TicketConversation(TreeboTickets main) {
        pl = main;
        this.createTicket = new CreateTicket(main);
    }

    public Integer ticketState = 0;
    public String type, description, usersteps = "";
    public String staffsteps = "";


    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String m = e.getMessage();
        String u = p.getUniqueId().toString();

        ConfigurationSection playersList = pl.getConfig().getConfigurationSection("players");

        for (String item : playersList.getKeys(false)) {
            if ((p.getName().equalsIgnoreCase(item)) && (pl.getConfig().getInt("players." + p.getName() + ".ticketstate") > 0)) {
                e.setCancelled(true);
                ticketState = pl.getConfig().getInt("players." + p.getName() + ".ticketstate");
                if (m.equalsIgnoreCase("cancel")) {
                    ticketState = 0;
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", 0);
                    pl.saveConfig();
                    p.sendMessage(pl.badge + "Ticket Canceled");
                }
                String tServer = pl.api.getServer().toString();
                UUID puuid = p.getUniqueId();
                String pname = p.getName();
                String type = "";
                String opened = LocalDateTime.now().toString();
                String status = "OPEN";
                String staff = "Unassigned";
                String pworld = p.getWorld().getName();
                String pServer = pl.getConfig().getString("serverName");
                Integer px = (int) p.getLocation().getX();
                Integer py = (int) p.getLocation().getY();
                Integer pz = (int) p.getLocation().getZ();

                if (ticketState == 1) {
                    if (m.equals("1") || m.equals("2") || m.equals("3")) {
                        if (m.equals("1")) {
                            type = "Server";
                        } else if (m.equals("2")) {
                            type = "Griefer";
                        } else if (m.equals("3")) {
                            type = "Other";
                        }
                        else {p.sendMessage(pl.err + "Invalid input.");}


                        pl.getConfig().set("players." + p.getName() + ".type", type);
                        pl.getConfig().set("players." + p.getName() + ".ticketstate", 2);
                        pl.saveConfig();

                        p.sendMessage("");
                        p.sendMessage("Your response - " + type);
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage(pl.badge + "In your own words, please give a short explanation of the issue you are suffering");
                    }
                }
                if (ticketState == 2) {
                    description = m.replace("'","APOSTR").replace("\\","BSlash").replace("/"," FSlash ");
                    pl.getConfig().set("players." + p.getName() + ".description", description);
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", 3);
                    pl.saveConfig();

                    p.sendMessage("");
                    p.sendMessage("Your response - " + description);
                    p.sendMessage("");
                    p.sendMessage("");
                    if (pl.getConfig().get("players." + p.getName() + ".type").toString().equalsIgnoreCase("idea")) {
                        p.sendMessage(pl.badge + "Please list any additional details you'd like to include.");
                    } else if (pl.getConfig().get("players." + p.getName() + ".type").toString().equalsIgnoreCase("review")) {
                        p.sendMessage(pl.badge + "Please list any additional details you'd like to include.");
                    } else {
                        p.sendMessage(pl.badge + "Briefly describe what steps you've taken to attempt to fix this issue yourself");
                    }
                }

                if (ticketState == 3) {
                    usersteps = m.replace("'","APOSTR").replace("\\","BSlash").replace("/"," FSlash ");
                    pl.getConfig().set("players." + p.getName() + ".usersteps", m);
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", 4);

                    p.sendMessage("");
                    p.sendMessage("Your response - " + usersteps);
                    p.sendMessage("");
                    p.sendMessage("");

                    if (pl.getConfig().get("players." + p.getName() + ".type").toString().equalsIgnoreCase("idea")) {
                        staff = "Robert_Beckley";
                    }
                    if (pl.getConfig().get("players." + p.getName() + ".type").toString().equalsIgnoreCase("review")) {
                        staff = "Builders";
                    }

                    if(pl.getConfig().getString("players." + p.getName() + ".type") != null){
                    type = pl.getConfig().getString("players." + p.getName() + ".type");}
                    else {type = "Other";}
                    description = pl.getConfig().getString("players." + p.getName() + ".description");
                    usersteps = pl.getConfig().getString("players." + p.getName() + ".usersteps");
                    String actualCommand = pl.getConfig().getString("players." + p.getName() + ".actualCommand").replace("'","APOSTR").replace("\\","BSlash").replace("/"," FSlash ");;
                    Integer severity = 0;
                    String ticketData = "\"" + puuid + "\",\"" + p.getName() + "\",\"" + opened + "\",\"" + status + "\",\"" + staff + "\",\"" + pServer + "\",\"" + pworld + "\",\"" + px + "\",\"" + py + "\",\"" + pz + "\",\"" + type + "\",\"" + severity + "\",\"" + description + "\",\"" + usersteps + "\",\"" + staffsteps + "\", \"Staff\", \"" + actualCommand + "\"";
                    pl.getConfig().set("players." + p.getName() + "lastQuery", pl.baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", 0);
                    pl.saveConfig();
                    String discordString = "&type=" + type +"&user=" + p.getName() + "&contents=" + (description + " " + usersteps).replace("&", "AND");

                    createTicket.addTicketToDB(p, ticketData, type, description + " " + usersteps);
                }
            }
        }
    }
}
