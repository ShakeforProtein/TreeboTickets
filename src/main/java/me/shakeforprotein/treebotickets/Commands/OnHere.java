package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.CalculateConnection;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnHere implements CommandExecutor {

    private TreeboTickets pl;
    private CalculateConnection calculateConnection;

    public OnHere(TreeboTickets main) {
        pl = main;
        this.calculateConnection = new CalculateConnection(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("onHere")) {
            if (sender.hasPermission("tbtickets.ontime")) {
                calculateConnection.calculateConnection((Player) sender, ((Player) sender).getUniqueId());
            }
        }
        return true;
    }
}
