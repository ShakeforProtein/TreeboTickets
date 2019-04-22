package me.shakeforprotein.treebotickets.Methods.Teleports;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuilderTp {

    private TreeboTickets pl;

    public BuilderTp(TreeboTickets main){this.pl = main;}

    public void builderTP(Player p, String query, int t) {
    ResultSet response;
    try {
        response = pl.connection.createStatement().executeQuery(query);
        while (response.next()) {
            String tStaff = response.getString("STAFF");
            if (p.hasPermission("tbtickets.builder") && tStaff.equalsIgnoreCase("Builders")) {
                int tX = response.getInt("X");
                int tY = response.getInt("Y");
                int tZ = response.getInt("Z");
                String tServer = response.getString("SERVER");
                String tWorld = response.getString("WORLD");
                if (!tServer.equalsIgnoreCase(pl.getConfig().getString("serverName"))) {
                    p.sendMessage(pl.badge + "As you were on the wrong server, you will need to repeat the command.");
                    p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "Changing your server for you now.");
                    pl.api.connectOther(p.getName(), tServer);
                } else {
                    p.teleport(new Location(Bukkit.getWorld(tWorld), tX, tY, tZ));
                }
            } else {
                p.sendMessage(pl.err + "Insufficient permissions");
            }
        }
    } catch (SQLException e) {
        p.sendMessage(pl.err + "Something went wrong");
        System.out.println(pl.err + "Encountered " + e.toString() + " during staffTP()");
        pl.makeLog(e);

    }
}
}
