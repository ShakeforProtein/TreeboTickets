package me.shakeforprotein.treebotickets.Listeners.InventoryEvents;

import me.shakeforprotein.treebotickets.TreeboTickets;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Set;

public class PlayerUseItem implements Listener {

    private TreeboTickets pl;


    public PlayerUseItem(TreeboTickets main) {
        this.pl = main;
    }

    @EventHandler
    public void playerUseItem(PlayerInteractEvent e) {
        Inventory inv = Bukkit.createInventory(null,9,"AntiHack Triggered");
        Player p = e.getPlayer();
        if (e.getItem() != null) {
            if (e.getItem().getType() == Material.WRITTEN_BOOK) {
                if (e.getItem().hasItemMeta()) {
                    if (e.getItem().getItemMeta() instanceof BookMeta) {
                        net.minecraft.server.v1_15_R1.ItemStack nmsItem = getNMSItem(e.getItem());
                        NBTTagCompound compound = getCompound(nmsItem);
                        Set<String> compoundKeys = compound.getKeys();
                        for(String item : compoundKeys){
                            if(compound.get(item).asString().contains("clickEvent")){
                                e.getItem().setItemMeta(null);
                                e.getPlayer().openInventory(inv);
                                e.getPlayer().closeInventory();
                                break;
                            }
                        }
                    }
                }
            }
            if (e.getPlayer().hasPermission("tbtickets.admin.restoreinventory") && e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().contains("Death Docket -")) {
                if (e.getItem().getItemMeta().getLore().get(4) != null) {
                    String cmd = "tellraw " + e.getPlayer().getName() + " [\"\",{\"text\":\"Click \"},{\"text\":\"[HERE]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/restoreplayerinventory " + e.getItem().getItemMeta().getLore().get(0).split(" - ")[1] + " " + e.getItem().getItemMeta().getLore().get(4).split(" - ")[1] + "\"}},{\"text\":\" to restore player inventory.\"}]";
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);

                }
            }
        }
    }

    public net.minecraft.server.v1_15_R1.ItemStack  getNMSItem(ItemStack item){
        net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return nmsItem;
    }

    public NBTTagCompound getCompound(net.minecraft.server.v1_15_R1.ItemStack nmsItem){
        NBTTagCompound nmsCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        return nmsCompound;
    }

    public ItemStack getBukkitItem(net.minecraft.server.v1_15_R1.ItemStack nmsItem){
        ItemStack bukkitItem = CraftItemStack.asBukkitCopy(nmsItem);
        return bukkitItem;
    }
}
