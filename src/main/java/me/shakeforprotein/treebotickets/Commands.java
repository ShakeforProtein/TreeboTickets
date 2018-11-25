package me.shakeforprotein.treebotickets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import me.shakeforprotein.treebotickets.TreeboTickets;
import me.shakeforprotein.treebotickets.PlayerInput;

public class Commands extends JavaPlugin {


    private TreeboTickets pl;
    private PlayerInput pi;
    public Commands(TreeboTickets pl){
        this.pl = pl;
    }




    public boolean goodAnswer = false;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender instanceof Player))
        {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("tbticket")) {
            p.sendMessage(("XXXNETWORKNAMEXXX" + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
            pi.listenTo(p);


            }
        }
        return true;
    }

}
