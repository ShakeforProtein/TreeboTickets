package me.shakeforprotein.treebotickets.Methods.Teleports;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffTp {

    private TreeboTickets pl;
    public StaffTp(TreeboTickets main){this.pl = main;}

    public void staffTP(Player p, String query, int t) {
        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery(query);
            while (response.next()) {
                String tPlayer = response.getString("IGNAME");
                if (p.hasPermission("tbtickets.mod.view") || p.hasPermission("tbtickets.admin")) {
                    int tId = response.getInt("ID");
                    int tX = response.getInt("X");
                    int tY = response.getInt("Y");
                    int tZ = response.getInt("Z");
                    String tServer = response.getString("SERVER");
                    String tCoords = tX + " " + tY + " " + tZ;
                    String tWorld = response.getString("WORLD");
                    String tStatus = response.getString("STATUS");
                    if (!tServer.equalsIgnoreCase(pl.getConfig().getString("serverName"))) {
                        p.sendMessage(pl.badge + "As you were on the wrong server, you will need to repeat the command.");
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "Changing your server for you now.");
                        pl.api.connectOther(p.getName(), tServer);
                    } else {
                        p.teleport(new Location(Bukkit.getWorld(tWorld), tX, tY, tZ));
                    }
                }
            }
        } catch (SQLException e) {
            p.sendMessage(pl.err + "Something went wrong");
            System.out.println(pl.err + "Encountered " + e.toString() + " during staffTP()");
            pl.makeLog(e);

        }
    }
}
