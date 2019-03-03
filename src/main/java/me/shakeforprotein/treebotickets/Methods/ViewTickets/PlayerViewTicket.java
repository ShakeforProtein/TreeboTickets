package me.shakeforprotein.treebotickets.Methods.ViewTickets;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerViewTicket {

    private TreeboTickets pl;

    public PlayerViewTicket(TreeboTickets main){this.pl = main;}

    public void getTicket(Player p, Integer t) {
        int tId = -1;
        String tPlayer, tCoords, tDesc, tStaffS, tUserS, tStatus, tOpened, tModified, tServer, tWorld = "";
        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
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
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                    p.sendMessage(ChatColor.GREEN + "Opened by Player: " + ChatColor.WHITE + tPlayer);
                    p.sendMessage(ChatColor.GREEN + "Status: " + ChatColor.WHITE + tStatus);
                    p.sendMessage(ChatColor.GREEN + "Opened at: " + ChatColor.WHITE + tOpened);
                    p.sendMessage(ChatColor.GREEN + "Last Updated: " + ChatColor.WHITE + tModified);
                    p.sendMessage(ChatColor.GREEN + "On World: " + ChatColor.WHITE + tWorld);
                    p.sendMessage(ChatColor.GREEN + "At Coordinates: " + ChatColor.GOLD + tCoords);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "User Description: " + ChatColor.WHITE + tDesc.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                    p.sendMessage("");
                    p.sendMessage(ChatColor.BLUE + "Steps taken by user: " + ChatColor.WHITE + tUserS.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED + "Staff comments / actions: " + ChatColor.WHITE + tStaffS.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                } else {
                    p.sendMessage(ChatColor.RED + "Sorry but that ticket does not belong to you.");
                }
            }
        } catch (SQLException e) {
            // p.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during getTicket()");
            pl.getConfig().set("lastStackTrace", e.getStackTrace());
            pl.makeLog(e);
        }
    }
}
