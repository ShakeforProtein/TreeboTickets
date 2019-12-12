package me.shakeforprotein.treebotickets.Methods.Helps;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;

public class InfoHelp {

    private TreeboTickets pl;

    public InfoHelp(TreeboTickets main) {
        this.pl = main;
    }

    public void infoHelp(String fileName, CommandSender p) {
        File listFile = new File(pl.getDataFolder(), File.separator + "infoList.yml");
        FileConfiguration infoList = YamlConfiguration.loadConfiguration(listFile);
        if (!listFile.exists()) {
            try {
                listFile.createNewFile();
                try {
                    infoList.options().copyDefaults();
                    infoList.save(listFile);
                } catch (FileNotFoundException e) {
                    pl.makeLog(e);
                }
            } catch (IOException e) {
                pl.makeLog(e);
            }
        }


        File infoFile = new File(pl.getDataFolder() + File.separator + "infoFiles" + File.separator, fileName + ".txt");

        if (!infoFile.exists()) {
            p.sendMessage(pl.err + "No data found on requested topic " + ChatColor.YELLOW + fileName);
        }
        else {
            try {
                BufferedReader in = new BufferedReader(new FileReader(infoFile));

                String line = null;
                
                if (infoList.get(fileName) != null) {
                    if (infoList.getString(fileName).equalsIgnoreCase("text")) {

                        while ((line = in.readLine()) != null) {
                            p.sendMessage(format(line).replace("%player%", p.getName()));
                        }
                    } else if (infoList.getString(fileName).equalsIgnoreCase("json")) {
                        String tempString = "";
                        while ((line = in.readLine()) != null) {
                            tempString += line;
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + tempString);
                    }

                } else {
                    p.sendMessage(pl.err + ChatColor.GREEN + "Data found " + ChatColor.RED + "but not configured");
                }
            } catch (Exception err) {
                pl.makeLog(err);
            }
        }

    }

    private String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

}
