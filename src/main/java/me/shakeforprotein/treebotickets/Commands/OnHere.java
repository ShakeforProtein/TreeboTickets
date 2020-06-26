package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Methods.ConnectionLoging.RetrieveOntime;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
            if (sender.hasPermission("tbtickets.staffmanager") || sender.getName().equalsIgnoreCase("ShakeforProtein")) {
                if (args.length == 1) {
                    if(args[0].equalsIgnoreCase("staff")){
                    if (pl.getConfig().getString("serverName").equalsIgnoreCase("hub") || pl.getConfig().getString("serverName").equalsIgnoreCase("test")) {
                        File listFile = new File(pl.getDataFolder(), "staffList.yml");
                        FileConfiguration staffList = YamlConfiguration.loadConfiguration(listFile);

                        for (String item : staffList.getKeys(false)) {
                            String staff = staffList.getString(item);
                            retrieveOntime.retrieveOntime(staff, sender, "false");
                        }

                    }
                    } else {
                        retrieveOntime.retrieveOntime(args[0], sender, "false");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("staff")) {
                        if (pl.getConfig().getString("serverName").equalsIgnoreCase("hub")) {
                            File listFile = new File(pl.getDataFolder(), "staffList.yml");
                            FileConfiguration staffList = YamlConfiguration.loadConfiguration(listFile);

                            for (String item : staffList.getKeys(false)) {
                                String staff = staffList.getString(item);
                                retrieveOntime.retrieveOntime(staff, sender, args[1]);
                            }

                        } else {
                            sender.sendMessage(pl.err + "The staff command only works from hub");
                        }
                    } else {
                        retrieveOntime.retrieveOntime(args[0], sender, args[1]);
                    }
                }
            }
            else{
                retrieveOntime.retrieveOntime(sender.getName(), sender, "personal");
            }
        }
        return true;
    }
}