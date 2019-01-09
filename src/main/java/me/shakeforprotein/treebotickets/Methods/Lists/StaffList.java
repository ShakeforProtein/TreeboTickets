package me.shakeforprotein.treebotickets.Methods.Lists;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffList {

    private TreeboTickets pl;

    public StaffList(TreeboTickets main){this.pl = main;}

    public String staffList(Player p, String query) {
        ResultSet response;
        String output = "";
        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
        p.sendMessage(ChatColor.AQUA + "Id  -   Player  -   World   -   Coordinates -   Assigned    -   Status");

        try {
            response = pl.connection.createStatement().executeQuery(query);
            while (response.next()) {
                String tStaff = response.getString("STAFF");
                String tPlayer = response.getString("IGNAME");

                int tId = response.getInt("ID");
                int tX = response.getInt("X");
                int tY = response.getInt("Y");
                int tZ = response.getInt("Z");
                String tWorld = response.getString("WORLD");
                String tStatus = response.getString("STATUS");
                p.sendMessage(ChatColor.WHITE + "" + tId + "  -   " + tPlayer + "    -   " + tWorld + "    -   " + tX + " " + tY + " " + tZ + "   -   " + tStaff + "   -   " + tStatus);

            }
            p.sendMessage(ChatColor.DARK_BLUE + "#EndOfList");
        } catch (SQLException e) {
            p.sendMessage(ChatColor.RED + "Something went wrong getting statistics");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
            pl.makeLog(e);
        }
        return output;
    }
}
