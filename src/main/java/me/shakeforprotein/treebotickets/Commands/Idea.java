package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Idea implements CommandExecutor {

    private TreeboTickets pl;

    public Idea(TreeboTickets main) {
        pl = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();
            if (cmd.getName().equalsIgnoreCase("idea")) {
                if(args.length > 0){
                        StringBuilder argsText = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            argsText.append(args[i] + " ");
                        }
                    pl.getConfig().set("players." + p.getName() + ".actualCommand", cmd.getName() + " " + argsText);
                }
                else{
                    pl.getConfig().set("players." + p.getName() + ".actualCommand", cmd.getName());
                }
                pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                pl.getConfig().set("players." + p.getName() + ".type", "Idea");
                p.sendMessage(pl.badge + "Please give a brief description of your idea.");
            }
        }
        return true;
    }
}
