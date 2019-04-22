package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.PlayerStatistics.GetStatistic;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetStat implements CommandExecutor {

    private TreeboTickets pl;
    private GetStatistic getStatistic;

    public GetStat(TreeboTickets main) {
        this.pl = main;
        this.getStatistic = new GetStatistic(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("getstat")){
            if(sender instanceof Player) {
                if (args.length == 2) {
                    sender.sendMessage(getStatistic.getStatistic(args[0], args[1], (Player) sender));

                } else if (args.length == 1) {
                    sender.sendMessage(pl.err + "No playername detected, getting own stat");
                    sender.sendMessage(getStatistic.getStatistic(args[0], sender.getName(), (Player) sender));
                } else {
                    sender.sendMessage(pl.err + "Incorrect command usage. Please specify stat and player.");
                    sender.sendMessage("/getstat <stat> <playername>");
                }
            }
            else {
                if (args.length == 3) {
                    getStatistic.getStatistic2(args[0], args[1], args[2]);
                } else if (args.length == 1) {
                    sender.sendMessage(pl.err + "This command requires a player argument and server name / skyblock gamemode name when run from console");
                    sender.sendMessage("/getstat <stat> <playername> <tableName>");
                } else {
                    sender.sendMessage(pl.err + "Incorrect command usage. Please specify stat and player.");
                    sender.sendMessage("/getstat <stat> <playername> <tableName>");
                }

            }
        }
        return true;
    }
}
