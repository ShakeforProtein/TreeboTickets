package me.shakeforprotein.treebotickets.Methods.Guis;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecificTicketGui {

    private TreeboTickets pl;

    public SpecificTicketGui(TreeboTickets main){this.pl = main;}

    public void specificTicketGui(Player p, int t, String menuName, String fromMenu) {
        Inventory individualTicketGui = Bukkit.createInventory(null, 27, menuName);
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
            response = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
            if (!response.isBeforeFirst()) {
                p.sendMessage("No Data Matching ticket number " + t);
                System.out.println("No Data Matching ticket number " + t);
            }
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

                String tX = response.getString("X");
                String tY = response.getString("Y");
                String tZ = response.getString("Z");
                try {
                    tModified = response.getDate("MODIFIED").toString();
                } catch (NullPointerException e) {
                    tModified = "BLANK VALUE";
                }


                //Make Ticket details item
                ItemStack newTicket = new ItemStack(Material.BOOK, 1);
                ItemMeta newTicketMeta = newTicket.getItemMeta();
                newTicketMeta.setDisplayName("Ticket - " + tId);
                List<String> newTicketLore = new ArrayList<String>();
                String coloredStatus;
                if(tStatus.equalsIgnoreCase("OPEN")){coloredStatus = ChatColor.RED + tStatus + ChatColor.RESET;}
                else if (tStatus.equalsIgnoreCase("CLOSED")){coloredStatus = ChatColor.RED + tStatus + ChatColor.RESET;}
                else {coloredStatus = tStatus;}

                newTicketLore.add("Assigned to: " + tStaff);
                newTicketLore.add("Status: " + coloredStatus);
                newTicketLore.add("Player: " + tPlayer);
                newTicketLore.add("World: " + tWorld);
                newTicketLore.add("Modified: " + tModified);
                newTicketLore.add("Coords:" + tX + "," + tY + "," + tZ);
                newTicketLore.add("");
                newTicketLore.add("Description: " + tDesc.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                newTicketLore.add("");
                newTicketLore.add("User Steps:" + tUserS);
                newTicketLore.add("");
                newTicketLore.add("Staff Notes:" + tStaffS);


                newTicketMeta.setLore(newTicketLore);
                newTicket.setItemMeta(newTicketMeta);
                individualTicketGui.setItem(5, new ItemStack(newTicket));

                ItemStack claimIcon = new ItemStack(Material.NAME_TAG, 1);
                ItemStack unclaimIcon = new ItemStack(Material.SHEARS, 1);
                ItemStack openIcon = new ItemStack(Material.WRITABLE_BOOK, 1);
                ItemStack closeIcon = new ItemStack(Material.WRITTEN_BOOK, 1);
                ItemStack teleportIcon = new ItemStack(Material.ENDER_PEARL, 1);
                ItemStack deleteIcon = new ItemStack(Material.FLINT_AND_STEEL, 1);
                ItemStack backIcon = new ItemStack(Material.BARRIER, 1);
                ItemStack replyIcon = new ItemStack(Material.INK_SAC, 1);


                ItemMeta claimMeta = claimIcon.getItemMeta();
                ItemMeta unclaimMeta = unclaimIcon.getItemMeta();
                ItemMeta closeMeta = closeIcon.getItemMeta();
                ItemMeta openMeta = openIcon.getItemMeta();
                ItemMeta teleportMeta = teleportIcon.getItemMeta();
                ItemMeta deleteMeta = deleteIcon.getItemMeta();
                ItemMeta backMeta = backIcon.getItemMeta();
                ItemMeta replyMeta = replyIcon.getItemMeta();

                claimMeta.setDisplayName("Claim Ticket");
                unclaimMeta.setDisplayName("Unclaim Ticket");
                closeMeta.setDisplayName("Close Ticket");
                openMeta.setDisplayName("Re-Open Ticket");
                teleportMeta.setDisplayName("Teleport to Ticket");
                deleteMeta.setDisplayName("Delete Ticket");
                backMeta.setDisplayName("Back");
                replyMeta.setDisplayName("Reply");

                List<String> claimMetaLore = new ArrayList<String>();
                List<String> unclaimMetaLore = new ArrayList<String>();
                List<String> closeMetaLore = new ArrayList<String>();
                List<String> openMetaLore = new ArrayList<String>();
                List<String> teleportMetaLore = new ArrayList<String>();
                List<String> deleteMetaLore = new ArrayList<String>();
                List<String> backMetaLore = new ArrayList<String>();
                List<String> replyMetaLore = new ArrayList<String>();


                claimMetaLore.add("Claims a ticket for yourself");
                unclaimMetaLore.add("Unclaims a ticket claimed by yourself");
                closeMetaLore.add("Closes this ticket");
                openMetaLore.add("Reopens this ticket");
                teleportMetaLore.add("Teleports to this ticket");
                deleteMetaLore.add("Deletes this ticket");
                backMetaLore.add("Returns you to the previous menu");
                replyMetaLore.add("Reply to this ticket");

                if(fromMenu.equalsIgnoreCase("Open")){backMetaLore.add("Open");}
                if(fromMenu.equalsIgnoreCase("Closed")){backMetaLore.add("Closed");}
                if(fromMenu.equalsIgnoreCase("Assigned")){backMetaLore.add("Assigned");}
                if(fromMenu.equalsIgnoreCase("Unassigned")){backMetaLore.add("Unassigned");}
                if(fromMenu.equalsIgnoreCase("Builder")){backMetaLore.add("Builder");}



                claimMetaLore.add("tbta claim "+ tId);
                unclaimMetaLore.add("tbta unclaim " + tId);

                if (p.hasPermission("tbtickets.admin")){closeMetaLore.add("tbticketadmin close " +tId);
                    replyMetaLore.add("tellraw " + p.getName() + " {\"text\":\"Reply to ticket - "+ tId + "\",\"color\":\"dark_aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tbticketadmin update "+ tId + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click me to Prefill command.\"}}");
                }
                else if (p.hasPermission("tbtickets.view.any")){closeMetaLore.add("tbta close " +tId);
                    replyMetaLore.add("tellraw " + p.getName() + " {\"text\":\"Reply to ticket - "+ tId + "\",\"color\":\"dark_aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tbta update "+ tId + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click me to Prefill command.\"}}");
                }
                else if (p.hasPermission("tbtickets.builder")){closeMetaLore.add("review close " + tId);
                    replyMetaLore.add("tellraw " + p.getName() + " {\"text\":\"Reply to ticket - "+ tId + "\",\"color\":\"dark_aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/review update "+ tId + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Click me to Prefill command.\"}}");
                }

                openMetaLore.add("tbta reopen " + tId);

                if (p.hasPermission("tbtickets.admin")){teleportMetaLore.add("tbticketadmin tp " +tId );}
                else if (p.hasPermission("tbtickets.view.any")){teleportMetaLore.add("tbta tp " +tId );}
                else if (p.hasPermission("tbtickets.builder")){teleportMetaLore.add("review tp " +tId );}

                if (p.hasPermission("tbtickets.admin")){deleteMetaLore.add("tbticketadmin delete "+ tId);}
                else if (p.hasPermission("tbtickets.view.any")){deleteMetaLore.add("tbta close " +tId );}
                else if (p.hasPermission("tbtickets.builder")){teleportMetaLore.add("review close " +tId );}



                claimMeta.setLore(claimMetaLore);
                unclaimMeta.setLore(unclaimMetaLore);
                closeMeta.setLore(closeMetaLore);
                openMeta.setLore(openMetaLore);
                teleportMeta.setLore(teleportMetaLore);
                deleteMeta.setLore(deleteMetaLore);
                backMeta.setLore(backMetaLore);
                replyMeta.setLore(replyMetaLore);

                claimIcon.setItemMeta(claimMeta);
                unclaimIcon.setItemMeta(unclaimMeta);
                closeIcon.setItemMeta(closeMeta);
                openIcon.setItemMeta(openMeta);
                teleportIcon.setItemMeta(teleportMeta);
                deleteIcon.setItemMeta(deleteMeta);
                backIcon.setItemMeta(backMeta);
                replyIcon.setItemMeta(replyMeta);

                if (tStaff.equalsIgnoreCase("Unassigned")){
                    individualTicketGui.setItem( 8+ 2, claimIcon );}
                else if(tStaff.equalsIgnoreCase(p.getName())){
                    individualTicketGui.setItem(8 + 2, unclaimIcon);
                }

                if (tStatus.equalsIgnoreCase("CLOSED")){
                    individualTicketGui.setItem(8 + 4, openIcon );}
                else if(tStatus.equalsIgnoreCase("OPEN")){
                    individualTicketGui.setItem(8 + 4, closeIcon);
                }

                individualTicketGui.setItem(8 + 6, teleportIcon);
                individualTicketGui.setItem(8 + 8, deleteIcon );
                individualTicketGui.setItem(26, backIcon );
                individualTicketGui.setItem(23, replyIcon);

                p.openInventory(individualTicketGui);



            }
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during staffViewTicket()");
            pl.makeLog(e);
        }
    }
}
