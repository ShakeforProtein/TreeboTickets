package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
//import me.shakeforprotein.treebotickets.Methods.Teleports.ToWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;


public class ServerTransfers implements CommandExecutor {


    private TreeboTickets pl;
//    private ToWorld toWorld;

    public ServerTransfers(TreeboTickets main) {
        pl = main;
//        this.toWorld = new ToWorld(pl);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase(label)) {
            File serversFile = new File(pl.getDataFolder(), File.separator + "servers.yml");
            FileConfiguration serverList = YamlConfiguration.loadConfiguration(serversFile);

            Player p = (Player) sender;
            String serverTo = serverList.getString("servers." + label + ".server");
            String worldTo = serverList.getString("servers." + label + ".world");
//            toWorld.toWorld(serverTo, worldTo, p);
        }
        return true;
    }
}
