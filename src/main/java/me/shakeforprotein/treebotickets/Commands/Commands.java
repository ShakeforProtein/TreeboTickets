package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;



public class Commands implements CommandExecutor {

    private TreeboTickets pl;


    public Commands(TreeboTickets main) {
        this.pl = main;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        return true;
    }



    public static boolean isNumeric (String str)
    {
        return str.matches("\\d+");
    }

}