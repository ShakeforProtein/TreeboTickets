package me.shakeforprotein.treebotickets.Methods.Guis;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OpenTicketGui {

    private TreeboTickets pl;

    public OpenTicketGui(TreeboTickets main){this.pl = main;}


    public void openTicketGui(Player p) {
        Inventory mainTicketInv = Bukkit.createInventory(null, 9, "Tickets Main Menu");

        //Define Icons
        ItemStack assignedIcon = new ItemStack(Material.PAPER, 1);
        ItemStack unassignedIcon = new ItemStack(Material.PAPER, 1);
        ItemStack openIcon = new ItemStack(Material.PAPER, 1);
        ItemStack closedIcon = new ItemStack(Material.PAPER, 1);

        //Assign M
        ItemMeta assignedMeta = assignedIcon.getItemMeta();
        ItemMeta unassignedMeta = assignedIcon.getItemMeta();
        ItemMeta openMeta = assignedIcon.getItemMeta();
        ItemMeta closedMeta = assignedIcon.getItemMeta();

        assignedMeta.setDisplayName("Your assigned tickets");
        unassignedMeta.setDisplayName("Unassigned tickets");
        openMeta.setDisplayName("All open tickets");
        closedMeta.setDisplayName("All closed tickets");

        List<String> assignedMetaLore = new ArrayList<String>();
        List<String> unassignedMetaLore = new ArrayList<String>();
        List<String> openMetaLore = new ArrayList<String>();
        List<String> closedMetaLore = new ArrayList<String>();

        assignedIcon.setItemMeta(assignedMeta);
        unassignedIcon.setItemMeta(unassignedMeta);
        openIcon.setItemMeta(openMeta);
        closedIcon.setItemMeta(closedMeta);


        mainTicketInv.addItem(assignedIcon);
        mainTicketInv.addItem(unassignedIcon);
        mainTicketInv.addItem(openIcon);
        mainTicketInv.addItem(closedIcon);

        p.openInventory(mainTicketInv);
    /*TODO: Stats
          Reload
     */
    }
}
