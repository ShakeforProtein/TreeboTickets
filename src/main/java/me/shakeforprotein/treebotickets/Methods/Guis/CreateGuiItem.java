package me.shakeforprotein.treebotickets.Methods.Guis;

import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreateGuiItem {

    private TreeboTickets pl;

    public CreateGuiItem(TreeboTickets main){
        this.pl = main;
    }

    public ItemStack createGuiItem(String icon, String displayName){

        ItemStack item = new ItemStack(Material.getMaterial("" + icon), 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        return item;
    }
}
