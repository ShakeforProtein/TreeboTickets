package me.shakeforprotein.treebotickets;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.UUID;

public class PlayerInput implements Listener {


    private TreeboTickets pl;
    private Commands cmds;

    public PlayerInput(TreeboTickets main) {
        pl = main;
        this.cmds = cmds;
    }


    private ArrayList<String> usersToListenTo = new ArrayList<>();


    public Integer ticketState = 0;
    public String type, description, usersteps = "";
    public String staffsteps = "";



   @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String m = e.getMessage();
        String u = p.getUniqueId().toString();

        ConfigurationSection playersList = pl.getConfig().getConfigurationSection("players");

        for(String item: playersList.getKeys(false)) {
                if ((p.getName().equalsIgnoreCase(item)) && (pl.getConfig().getInt("players." + p.getName() + ".ticketstate") > 0)) {
                    e.setCancelled(true);
                    ticketState = pl.getConfig().getInt("players." + p.getName() + ".ticketstate");
                    UUID puuid = p.getUniqueId();
                    String pname = p.getName();
                    String type = "";
                    String opened = LocalDateTime.now().toString();
                    String status = "Open";
                    String staff = "Unassigned";
                    String pworld = p.getWorld().getName();
                    Integer px = (int) p.getLocation().getX();
                    Integer py = (int) p.getLocation().getY();
                    Integer pz = (int) p.getLocation().getZ();
                    if (m.equalsIgnoreCase("cancel")) {
                        ticketState = 0;
                        pl.getConfig().set("players." + p.getName(), 0);
                        pl.saveConfig();
                        p.sendMessage("Ticket Canceled");
                    }

                    if (ticketState == 1) {
                        if(m.equals("1") || m.equals("2") || m.equals("3")){
                        if (m.equals("1")) {
                            type = "Server";
                        }
                        else if (m.equals("2")) {
                            type = "Griefer";
                        }
                        else if (m.equals("3")) {
                            type = "Other";
                        }



                        pl.getConfig().set("players." + p.getName() + ".type", type);
                        pl.getConfig().set("players." + p.getName() + ".ticketstate", 2);
                        pl.saveConfig();

                        p.sendMessage("");
                        p.sendMessage("Your response - " + type);
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("In your own words, please give a short explanation of the issue you are suffering");
                        }
                    }
                    if (ticketState == 2) {
                        description = m;
                        pl.getConfig().set("players." + p.getName() + ".description", m);
                        pl.getConfig().set("players." + p.getName() + ".ticketstate", 3);
                        pl.saveConfig();

                        p.sendMessage("");
                        p.sendMessage("Your response - " + description);
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage("Briefly describe what steps you've taken to attempt to fix this issue yourself");
                    }

                    if (ticketState == 3) {
                        usersteps = m;
                        pl.getConfig().set("players." + p.getName() + ".usersteps", m);
                        pl.getConfig().set("players." + p.getName() + ".ticketstate", 4);

                        p.sendMessage("");
                        p.sendMessage("Your response - " + usersteps);
                        p.sendMessage("");
                        p.sendMessage("");

                        type = pl.getConfig().getString("players." + p.getName() + ".type");
                        description = pl.getConfig().getString("players." + p.getName() + ".description");
                        usersteps = pl.getConfig().getString("players." + p.getName() + ".usersteps");
                        Integer severity = 0;
                        String ticketData = "\"" + puuid + "\",\"" + p.getName() + "\",\"" + opened + "\",\"" + status + "\",\"" + staff + "\",\"" + pworld + "\",\"" + px + "\",\"" + py + "\",\"" + pz + "\",\"" + type + "\",\"" + severity + "\",\"" + description + "\",\"" + usersteps + "\",\"" + staffsteps +"\"";
                        pl.getConfig().set("players." + p.getName() + "lastQuery", pl.baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
                        pl.getConfig().set("players." + p.getName() + ".ticketstate", 0);
                        pl.addTicketToDB(p, ticketData);
                        p.sendMessage(ChatColor.GREEN + "Your ticket has been successfully submitted");

                        pl.saveConfig();
                    }
                }
            }
    }
}




