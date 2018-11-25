package me.shakeforprotein.treebotickets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.util.Consumer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInput implements Listener {

    private TreeboTickets pl;

    public PlayerInput(TreeboTickets pl) {
        this.pl = pl;
    }


    public ArrayList usersToListenTo;
    public Integer ticketState = 0;
    public String type, description, usersteps = "";
    public String staffsteps = "";

    public void listenTo(Player p) {
        usersToListenTo.add(p.getUniqueId().toString());
    }

    public void stopListeningTo(Player p) {
        usersToListenTo.remove(p.getUniqueId().toString());
    }


    Map<UUID, Consumer<String>> map = new HashMap<>();

    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String m = e.getMessage();

        for (Object item : usersToListenTo) {
            if (item.toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                UUID puuid = p.getUniqueId();
                String pname = p.getName();
                String opened = LocalDateTime.now().toString();
                String status = "Open";
                String staff = "Unassigned";
                String pworld = p.getWorld().getName();
                Integer px = (int) p.getLocation().getX();
                Integer py = (int) p.getLocation().getY();
                Integer pz = (int) p.getLocation().getZ();
                if (m.equalsIgnoreCase("cancel")) {
                    stopListeningTo(p);
                    ticketState = 0;
                    break;
                }
                if (ticketState == 0) {
                    p.sendMessage("Please enter");
                    p.sendMessage(ChatColor.GOLD + "1 " + ChatColor.RESET + "-" + ChatColor.RED + " For Server related issues");
                    p.sendMessage(ChatColor.GOLD + "2 " + ChatColor.RESET + "-" + ChatColor.RED + " For Griefer related issues");
                    p.sendMessage(ChatColor.GOLD + "3 " + ChatColor.RESET + "-" + ChatColor.RED + " For Other issues");
                    p.sendMessage("or enter 'cancel' at any time to stop creating a ticket");

                    while (!m.equals("1") && !m.equals("2") && !m.equals("3")) {
                        p.sendMessage("Please enter");
                        p.sendMessage(ChatColor.GOLD + "1 " + ChatColor.RESET + "-" + ChatColor.RED + " For Server related issues");
                        p.sendMessage(ChatColor.GOLD + "2 " + ChatColor.RESET + "-" + ChatColor.RED + " For Griefer related issues");
                        p.sendMessage(ChatColor.GOLD + "3 " + ChatColor.RESET + "-" + ChatColor.RED + " For Other issues");
                    }
                    if (m.equals("1")) {
                        String type = "Server";
                    }
                    if (m.equals("2")) {
                        String type = "Griefer";
                    }
                    if (m.equals("3")) {
                        String type = "Other";
                    }
                    ticketState++;
                    e.setCancelled(true);
                }

                if (ticketState == 1) {
                    p.sendMessage("In your own words, please give a short explanation of the issue you are suffering");
                    description = m;
                    ticketState++;
                }

                if (ticketState == 2) {
                    p.sendMessage("Briefly describe what steps you've taken to attempt to fix this issue yourself");
                    usersteps = m;
                    ticketState++;
                }

                if (ticketState == 3) {
                    Integer severity = 0;
                    p.sendMessage("This ticket would result in the following query");
                    String ticketData = "" + puuid + "," + p.getName() + "," + opened + "," + status + "," + staff + "," + pworld + "," +  px + "," +  py + "," +  pz + "," +  type + "," + severity + "," +  description + "," +  usersteps + "," +  staffsteps;
                    p.sendMessage("INSERT INTO " + pl.table + "(" + pl.columns + ") VALUES (" + ticketData + ");");
                }
            }
        }

    }
}
