package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ToggleDeathDocket implements CommandExecutor {

    private TreeboTickets pl;

    public ToggleDeathDocket(TreeboTickets main) {
        this.pl = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (pl.getConfig().get("deathDocket.toggle." + p.getUniqueId()) == null || pl.getConfig().get("deathDocket.toggle." + p.getUniqueId()).equals("false")) {
                    pl.getConfig().set("deathDocket.toggle." + p.getUniqueId(), "true");
                } else {
                    pl.getConfig().set("deathDocket.toggle." + p.getUniqueId(), "false");
                }
            } else {
                sender.sendMessage(pl.err + "Only players can run this command");
            }
        return true;
    }
}
