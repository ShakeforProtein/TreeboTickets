package me.shakeforprotein.treebotickets.Commands.TabComplete;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InfoTabComplete implements TabCompleter {

    private TreeboTickets pl;

    public InfoTabComplete(TreeboTickets main) {
        this.pl = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tinfo")) {
            if (args.length == 1) {
                ArrayList<String> outputStrings = new ArrayList<>();

                File listFile = new File(pl.getDataFolder(), File.separator + "infoList.yml");
                FileConfiguration infoList = YamlConfiguration.loadConfiguration(listFile);
                if (!args[0].equals("")) {
                    for (String item : infoList.getKeys(false)) {
                        if (item.toLowerCase().startsWith(args[0].toLowerCase())) {
                            outputStrings.add(item);
                        }
                    }

                }
                return outputStrings;
            }
        }
        return null;
    }
}
