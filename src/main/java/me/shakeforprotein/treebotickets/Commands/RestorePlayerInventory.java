package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class RestorePlayerInventory implements CommandExecutor {

    private TreeboTickets pl;

    public RestorePlayerInventory(TreeboTickets main) {
        this.pl = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            if (args.length == 2) {

                if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                    Player p = Bukkit.getPlayer(args[0]);
                    File deathFile = new File(pl.getDataFolder() + File.separator + "deaths", File.separator + p.getUniqueId().toString() + "_" + args[1] + ".yml");
                    File deathFileUsed = new File(pl.getDataFolder() + File.separator + "deaths", File.separator + "USED_" + p.getUniqueId().toString() + "_" + args[1] + ".yml");

                    if (deathFile.exists()) {
                        sender.sendMessage(pl.badge + "Death file found");
                        FileConfiguration deathYaml = YamlConfiguration.loadConfiguration(deathFile);
                        if (deathYaml.getString("used").equalsIgnoreCase("false")) {
                            if (deathYaml.getString("uuid").equalsIgnoreCase(p.getUniqueId().toString())) {
                                if (p.getWorld().getName().equalsIgnoreCase(deathYaml.getString("world"))) {
                                    int i = 0;
                                    for (String key : deathYaml.getConfigurationSection("inventory").getKeys(false)) {
                                        p.getInventory().addItem(deathYaml.getItemStack("inventory.slot_" + i));
                                        i++;
                                    }
                                    p.setTotalExperience(deathYaml.getInt("experience"));
                                    deathYaml.set("used", true);
                                    try {
                                        deathYaml.save(deathFile);
                                        deathFile.renameTo(deathFileUsed);
                                    } catch (IOException err) {
                                        pl.makeLog(err);
                                        sender.sendMessage(pl.err + "Failed to update 'used' status on docket " + deathFile.toString());
                                    }
                                } else {
                                    sender.sendMessage(pl.err + "Player world mismatch. This inventory is for World:" + deathYaml.getString("world"));
                                }
                            } else {
                                sender.sendMessage(pl.err + "Player UUID mismatch. This inventory does not belong to Player: " + args[0]);
                            }
                        } else {
                            sender.sendMessage(pl.err + "This inventory has already been recovered");
                        }
                    } else {
                        sender.sendMessage(pl.err + "No matching death file on record.");
                    }
                } else {
                    sender.sendMessage(pl.err + "Player: " + args[0] + " not found.");
                }
            } else if (args.length < 2) {
                sender.sendMessage(pl.err + "Insufficient arguments. This command requires a <playername> and <secretkey> argument.");
            } else {
                sender.sendMessage(pl.err + "Too many arguments. This command requires only a <playername> and <secretkey> argument.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have access to this command. You require permission node " + ChatColor.GOLD + "tbtickets.admin.restoreinventory");
        }
        return true;
    }

}
