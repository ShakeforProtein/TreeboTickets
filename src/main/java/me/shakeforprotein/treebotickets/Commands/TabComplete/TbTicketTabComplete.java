package me.shakeforprotein.treebotickets.Commands.TabComplete;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TbTicketTabComplete implements TabCompleter {

    private TreeboTickets pl;

    public TbTicketTabComplete(TreeboTickets main){
        this.pl = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("tbticket") || cmd.getName().equalsIgnoreCase("ticket")) {
            ArrayList<String> subCommands = new ArrayList<String>();
            subCommands.add("close");
            subCommands.add("list");
            subCommands.add("open");
            subCommands.add("update");
            subCommands.add("view");

            ArrayList<String> outputsStrings = new ArrayList<>();
            if (args.length == 1) {
                if (!args[0].equals("")) {
                    for (String item : subCommands) {
                        if (item.toLowerCase().startsWith(args[0].toLowerCase())) {
                            outputsStrings.add(item);
                        }
                    }
                }
                else{
                    for (String item : subCommands) {
                        outputsStrings.add(item);
                    }
                }

            }


            return outputsStrings;
        }
        return null;
    }
}
