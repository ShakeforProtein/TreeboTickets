package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.TreeboTickets;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyHub implements Listener {

    private TreeboTickets pl;

    public NotifyHub(TreeboTickets main) {
        this.pl = main;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {

        if ((pl.getConfig().getString("isLobbyServer").equalsIgnoreCase("true")) && (!e.getPlayer().hasPlayedBefore())) {
            Bukkit.getServer().getScheduler().runTaskLater(pl, new Runnable() {
                public void run() {
                    String command;
                    Player p = e.getPlayer();
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    command = "title " + p.getName() + " times 20 100 20";
                    Bukkit.dispatchCommand(console, command);
                    //command = "title " + p.getName() + " subtitle [\"\",{\"text\":\"Use\",\"color\":\"green\"},{\"text\":\" \"},{\"text\":\"/hub\",\"color\":\"red\"},{\"text\":\" \"},{\"text\":\"to navigate the server\",\"color\":\"green\"}]";
                    //Bukkit.dispatchCommand(console, command);
                    command = "title " + p.getName() + " title {\"text\":\"Welcome to TreeboMC\",\"color\":\"green\"}\n";
                    Bukkit.dispatchCommand(console, command);

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatColor.GREEN + "Use " + ChatColor.RED + "/hub" + ChatColor.GREEN + " to navigate the Treebo Servers").create());
                }
            }, 200L);
        }
    }
}
