package me.shakeforprotein.treebotickets.Commands;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import me.shakeforprotein.treebotickets.TreeboTickets;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class FriendlyRestart implements CommandExecutor {

    private TreeboTickets pl;


    public FriendlyRestart(TreeboTickets main) {
        this.pl = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        for(Player player : pl.getServer().getOnlinePlayers()){
            pl.getConfig().set("pullbackPlayers." + player.getName(), true);
         }
        Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
            @Override
            public void run() {
                if(pl.getServer().getOnlinePlayers().isEmpty()){
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
                }
                else{
                    for(Player player : pl.getServer().getOnlinePlayers()){
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(pl.badge + ChatColor.RED + ChatColor.BOLD + "This server needs to restart for maintenance. Expected downtime is 2 minutes").create());
                        if(!pl.getConfig().getString("serverName").equalsIgnoreCase("hub")) {
                            pl.api.connect(player, "hub");
                        }
                        else{
                            pl.api.connect(player, "creative");
                        }
                    }
                }
            }
        }, 100L, 100L);
        return true;
    }



    public static boolean isNumeric (String str)
    {
        return str.matches("\\d+");
    }

}