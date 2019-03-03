package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.RetrieveOntime;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OnHere implements CommandExecutor {

    private TreeboTickets pl;
    private RetrieveOntime retrieveOntime;

    public OnHere(TreeboTickets main) {
        pl = main;
        this.retrieveOntime = new RetrieveOntime(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("onHere") || cmd.getName().equalsIgnoreCase("seen")) {
            if (sender.hasPermission("tbtickets.ontime")) {
                if(args.length == 1){
                    retrieveOntime.retrieveOntime(args[0], sender);
                }

                if(args.length == 3 && args[2] == "minimal"){
                    retrieveOntime.retrieveOntime(args[0], sender, true);
                }
            }
        }
        return true;
    }
}