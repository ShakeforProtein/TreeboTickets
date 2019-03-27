package me.shakeforprotein.treebotickets.Methods.Guis;

import me.shakeforprotein.treebotickets.TreeboTickets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class OpenHubMenu {

    private TreeboTickets pl;

    public OpenHubMenu(TreeboTickets main){
        this.pl = main;
    }

    public boolean openHubMenu(Player p){

        File menuYml = new File(pl.getDataFolder(), File.separator + "hubMenu.yml");
        FileConfiguration hubMenu = YamlConfiguration.loadConfiguration(menuYml);
        if(!menuYml.exists()) {
            try {
                menuYml.createNewFile();
                try{
                    hubMenu.options().copyDefaults();
                    hubMenu.save(menuYml);
                }
                catch (FileNotFoundException e){pl.makeLog(e);}
            } catch (IOException e) {
                pl.makeLog(e);
            }
        }

            try{
            Set menuItems = hubMenu.getConfigurationSection("hubmenu.menuItems").getKeys(false);
            String[] menuItemStrings = Arrays.copyOf(menuItems.toArray(), menuItems.size(), String[].class);
            int invSize = hubMenu.getInt("hubmenu.menuRows") * 9;
            Inventory HubMenu = Bukkit.createInventory(null, invSize, "HubMenu");

            for(String item : menuItemStrings){
                Material icon = Material.getMaterial(hubMenu.getString("hubmenu.menuItems." + item + ".icon"));
                int position = hubMenu.getInt("hubmenu.menuItems." + item + ".position");
                String colour = "WHITE";
                if(hubMenu.getString("hubmenu.menuItems." + item + ".colour") != null){
                    colour = hubMenu.getString("hubmenu.menuItems." + item + ".colour");
                }
                String displayName = ChatColor.valueOf(colour)+ hubMenu.getString("hubmenu.menuItems." + item + ".label");
                ItemStack newItem = new ItemStack(icon, 1);
                ItemMeta itemMeta = newItem.getItemMeta();
                itemMeta.setDisplayName(displayName);
                List<String> itemLore = new ArrayList<String>();


                if(hubMenu.getConfigurationSection("hubmenu.menuItems." + item + ".lore") != null){
                    for(String newLore: hubMenu.getConfigurationSection("hubmenu.menuItems." + item + ".lore").getKeys(false)){
                        itemLore.add(hubMenu.getString("hubmenu.menuItems." + item + ".lore." + newLore));
                    }
                }
                itemMeta.setLore(itemLore);
                newItem.setItemMeta(itemMeta);
                HubMenu.setItem(position, newItem);
            }
            p.openInventory(HubMenu);
        }
        catch(NullPointerException e){
            pl.makeLog(e);
        }


        return true;
    }
}
