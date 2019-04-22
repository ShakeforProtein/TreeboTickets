package me.shakeforprotein.treebotickets.Methods.Lists;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminStaffList {

    private TreeboTickets pl;

    public AdminStaffList(TreeboTickets main){this.pl = main;}

    public String adminListStaff(Player p, String query, String staff) {
        ResultSet response;
        String output = "";
        if (p.hasPermission("tbtickets.admin")) {
            p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
            p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.AQUA + "Assigned to " + staff);
            p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Status");

            try {
                response = pl.connection.createStatement().executeQuery(query);
                while (response.next()) {
                    String tPlayer = response.getString("IGNAME");
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");
                    p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.WHITE + "" + tId + "  -   " + tPlayer + "    -   " + tWorld + "    -   " + tX + " " + tY + " " + tZ + "   -   " + tStatus);
                }

            } catch (SQLException e) {
                p.sendMessage(pl.err + "Something went wrong");
                System.out.println(pl.err + "Encountered " + e.toString() + " during genericQuery()");
                pl.makeLog(e);
            }
        }
        return output;
    }
}
