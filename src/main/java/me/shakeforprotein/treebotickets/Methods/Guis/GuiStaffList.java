package me.shakeforprotein.treebotickets.Methods.Guis;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiStaffList {

    private TreeboTickets pl;

    public GuiStaffList(TreeboTickets main){this.pl = main;}

    public void guiStaffList(Player p, String query, String menuName) {
        ResultSet response;
        String output = "";
        try {
            response = pl.connection.createStatement().executeQuery(query);
            Inventory ticketListGui = Bukkit.createInventory(null, 54, menuName);
            while (response.next()) {
                String tStaff = response.getString("STAFF");
                String tPlayer = response.getString("IGNAME");

                int tId = response.getInt("ID");
                int tX = response.getInt("X");
                int tY = response.getInt("Y");
                int tZ = response.getInt("Z");
                String tWorld = response.getString("WORLD");
                String tStatus = response.getString("STATUS");


                String fromMenu = "Main";
                ItemStack newTicket = new ItemStack(Material.PAPER, 1);
                ItemMeta newTicketMeta = newTicket.getItemMeta();
                newTicketMeta.setDisplayName("Ticket - " + tId);
                List<String> newTicketLore = new ArrayList<String>();
                String coloredStatus;
                if(tStatus.equalsIgnoreCase("OPEN")){coloredStatus = ChatColor.RED + tStatus + ChatColor.RESET;}
                else if (tStatus.equalsIgnoreCase("CLOSED")){coloredStatus = ChatColor.RED + tStatus + ChatColor.RESET;}
                else {coloredStatus = tStatus;}

                newTicketLore.add("Assigned to - " + tStaff);
                newTicketLore.add("Status - " + coloredStatus);
                newTicketLore.add("Player - " + tPlayer);
                newTicketLore.add("World - " + tWorld);
                newTicketLore.add("Coords - " + tX + "," + tY + "," + tZ);


                newTicketMeta.setLore(newTicketLore);
                newTicket.setItemMeta(newTicketMeta);
                ticketListGui.addItem(new ItemStack(newTicket));

            }
            ItemStack backIcon =  new ItemStack(Material.BARRIER, 1);
            ItemMeta backIconMeta = backIcon.getItemMeta();
            backIconMeta.setDisplayName("Previous Menu");
            List<String> backIconLore = new ArrayList<String>();
            backIconLore.add("");
            backIconLore.add("Main Menu");
            backIconMeta.setLore(backIconLore);
            backIcon.setItemMeta(backIconMeta);
            ticketListGui.setItem(53, backIcon);

            p.openInventory(ticketListGui);
        }

        catch (SQLException e) {
            p.sendMessage(pl.err + "Something went wrong getting statistics");
            System.out.println(pl.err + "Encountered " + e.toString() + " during guiStaffList()");
            pl.makeLog(e);
        }
    }
}
