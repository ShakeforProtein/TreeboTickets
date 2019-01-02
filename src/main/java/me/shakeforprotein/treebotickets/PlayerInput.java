package me.shakeforprotein.treebotickets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.UUID;

public class PlayerInput implements Listener {


    private TreeboTickets pl;
    private UpdateChecker uc;
    private Commands cmds;

    public PlayerInput(TreeboTickets main) {
        pl = main;
        this.cmds = cmds;
        this.uc = new UpdateChecker(pl);
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

        for (String item : playersList.getKeys(false)) {
            if ((p.getName().equalsIgnoreCase(item)) && (pl.getConfig().getInt("players." + p.getName() + ".ticketstate") > 0)) {
                e.setCancelled(true);
                ticketState = pl.getConfig().getInt("players." + p.getName() + ".ticketstate");
                if (m.equalsIgnoreCase("cancel")) {
                    ticketState = 0;
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", 0);
                    pl.saveConfig();
                    p.sendMessage("Ticket Canceled");
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
                    if (pl.getConfig().get("players." + p.getName() + ".type").toString().equalsIgnoreCase("idea")) {
                        p.sendMessage("Please list any additional details you'd like to include.");
                    } else if (pl.getConfig().get("players." + p.getName() + ".type").toString().equalsIgnoreCase("review")) {
                        p.sendMessage("Please list any additional details you'd like to include.");
                    } else {
                        p.sendMessage("Briefly describe what steps you've taken to attempt to fix this issue yourself");
                    }
                }

                if (ticketState == 3) {
                    usersteps = m;
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

                    type = pl.getConfig().getString("players." + p.getName() + ".type");
                    description = pl.getConfig().getString("players." + p.getName() + ".description");
                    usersteps = pl.getConfig().getString("players." + p.getName() + ".usersteps");
                    Integer severity = 0;
                    String ticketData = "\"" + puuid + "\",\"" + p.getName() + "\",\"" + opened + "\",\"" + status + "\",\"" + staff + "\",\"" + pServer + "\",\"" + pworld + "\",\"" + px + "\",\"" + py + "\",\"" + pz + "\",\"" + type + "\",\"" + severity + "\",\"" + description + "\",\"" + usersteps + "\",\"" + staffsteps + "\"";
                    pl.getConfig().set("players." + p.getName() + "lastQuery", pl.baseInsert.replace("XXXVALUESPLACEHOLDERXXX", ticketData));
                    pl.getConfig().set("players." + p.getName() + ".ticketstate", 0);
                    pl.saveConfig();
                    pl.addTicketToDB(p, ticketData);
                }
            }
        }
    }


    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        try {
            pl.openConnection();
        } catch (SQLException | ClassNotFoundException err) {
            System.out.println("Failed to reconnect to database. This is probably fine.");
        }

        pl.logConnection(e.getPlayer().getUniqueId(), e.getPlayer().getName(), "ON", LocalDateTime.now().toString());
        if ((pl.getConfig().getString("isLobbyServer").equalsIgnoreCase("true")) && (!e.getPlayer().hasPlayedBefore())) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                public void run() {
                    String command;
                    Player p = e.getPlayer();
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    command = "title " + p.getName() + " times 20 100 20";
                    Bukkit.dispatchCommand(console, command);
                    command = "title " + p.getName() + " subtitle [\"\",{\"text\":\"Use\",\"color\":\"green\"},{\"text\":\" \"},{\"text\":\"/hub\",\"color\":\"red\"},{\"text\":\" \"},{\"text\":\"to navigate the server\",\"color\":\"green\"}]";
                    Bukkit.dispatchCommand(console, command);
                    command = "title " + p.getName() + " title {\"text\":\"Welcome to TreeboMC\",\"color\":\"green\"}\n";
                    Bukkit.dispatchCommand(console, command);
                }
            }, 200L);
        }


        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
            public void run() {
                Player p = e.getPlayer();

                if ((pl.getConfig().getString(e.getPlayer().getName()) == null) || ((pl.getConfig().getString(e.getPlayer().getName()) != null) && (pl.getConfig().getString(e.getPlayer().getName()).equalsIgnoreCase("false")))) {
                    if (e.getPlayer().hasPermission("tbtickets.admin")) {
                        pl.adminStats(e.getPlayer());
                    }
                    if (e.getPlayer().hasPermission("tbtickets.view.any")) {
                        pl.staffStats(e.getPlayer());
                    } else if (e.getPlayer().hasPermission("tbtickets.builder")) {
                        pl.builderStats(e.getPlayer());
                    }
                    if (e.getPlayer().hasPermission(uc.requiredPermission)) {
                        uc.getCheckDownloadURL(e.getPlayer());
                        pl.getConfig().set(e.getPlayer().getName(), "true");
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                            public void run() {
                                pl.getConfig().set(e.getPlayer().getName(), "false");
                            }
                        }, 75L);
                    }
                } else {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                        public void run() {
                            try {
                                pl.getConfig().set(e.getPlayer().getName(), null);
                            } catch (NullPointerException e) {
                            }
                        }
                    }, 120L);
                }

            }
        }, 200L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        pl.logConnection(e.getPlayer().getUniqueId(), e.getPlayer().getName(), "OFF", LocalDateTime.now().toString());
    }

    @EventHandler
    public void invDragEvent(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        String name = inv.getName();
        if (name.equalsIgnoreCase("Your assigned tickets") || name.equalsIgnoreCase("Unassigned tickets") || name.equalsIgnoreCase("All open tickets") || name.equalsIgnoreCase("All closed tickets")) {
            e.setCancelled(true);
            return;
        }

    }

    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        String name = inv.getName();
        int slot = e.getSlot();
        if (slot < 0) {
            p.closeInventory();
            return;
        }

        if (name.equalsIgnoreCase("Tickets Main Menu")) {
            e.setCancelled(true);
            if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("Your Assigned Tickets")) {
                pl.listAssignedGui(p);
            } else if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("Unassigned Tickets")) {
                pl.listUnassignedGui(p);
            } else if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("All Open Tickets")) {
                pl.listOpenGui(p);
            } else if (e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().equalsIgnoreCase("All Closed Tickets")) {
                pl.listClosedGui(p);
            }
        }
        if (name.equalsIgnoreCase("Ticket List - Assigned to you") || name.equalsIgnoreCase("Ticket List - Unassigned ALL") || name.equalsIgnoreCase("Ticket List - Open ALL") || name.equalsIgnoreCase("Ticket List - Closed ALL")) {
            e.setCancelled(true);
            if(e.getClickedInventory().getItem(slot).hasItemMeta() && e.getClickedInventory().getItem(slot).getItemMeta().getLore().toArray().length == 2 && e.getClickedInventory().getItem(slot).getItemMeta().getLore().get(1).equalsIgnoreCase("Main Menu")){pl.openTicketGui(p);}
            else {
                int ticketID = Integer.parseInt(e.getClickedInventory().getItem(slot).getItemMeta().getDisplayName().split(" - ")[1]);
                pl.specificTicketGui(p, ticketID, "Ticket - " + ticketID, name.split(" - ")[1].split(" ")[0]);
            }
        }

        if (name.split(" - ") != null && name.split(" - ")[0].equalsIgnoreCase("Ticket")){
            e.setCancelled(true);
            if(e.getClickedInventory().getItem(slot).hasItemMeta() && e.getClickedInventory().getItem(slot).getItemMeta().getLore().toArray().length > 1){
                String command = e.getClickedInventory().getItem(slot).getItemMeta().getLore().get(1);
                if(command.equalsIgnoreCase("Open")){pl.listOpenGui(p);}
                else if(command.equalsIgnoreCase("Closed")){pl.listClosedGui(p);}
                else if(command.equalsIgnoreCase("Assigned")){pl.listAssignedGui(p);}
                else if(command.equalsIgnoreCase("Unassigned")){pl.listUnassignedGui(p);}
                else if (e.getClickedInventory().getItem(slot).getType() != Material.BOOK){Bukkit.dispatchCommand(p, command);}
                else if (e.getClickedInventory().getItem(slot).getType() == Material.BOOK){
                    if(p.hasPermission("tbtickets.admin")){
                        command = "tbticketadmin view " + name.split(" - ")[1];
                        Bukkit.dispatchCommand(p, command);
                    }
                    else{
                        command = "tbta view " + name.split(" - ")[1];
                        Bukkit.dispatchCommand(p, command);
                    }
                }

            }
        }
    }
}





