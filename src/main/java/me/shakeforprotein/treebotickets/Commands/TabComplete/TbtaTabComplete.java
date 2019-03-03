package me.shakeforprotein.treebotickets.Commands.TabComplete;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TbtaTabComplete implements TabCompleter {

    private TreeboTickets pl;

    public TbtaTabComplete(TreeboTickets main){
        this.pl = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("tbta") && sender.hasPermission("tbtickets.view.any")) {
            ArrayList<String> subCommands = new ArrayList<String>();
            ArrayList<String> outputsStrings = new ArrayList<>();

            if (args.length == 1) {
                subCommands.add("claim");
                subCommands.add("close");
                subCommands.add("gui");
                subCommands.add("list");
                subCommands.add("reopen");
                subCommands.add("tp");
                subCommands.add("unclaim");
                subCommands.add("update");
                subCommands.add("view");
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

            if(args.length == 2 && args[0].equalsIgnoreCase("list")){
                subCommands.add("assigned");
                subCommands.add("unassigned");
                subCommands.add("open");
                subCommands.add("closed");
                subCommands.add("idea");
                if(!args[1].equals("")){
                    for (String item : subCommands) {
                        if (item.toLowerCase().startsWith(args[1].toLowerCase())) {
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
