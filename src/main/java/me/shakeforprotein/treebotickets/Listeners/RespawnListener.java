package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class RespawnListener implements Listener {

    private TreeboTickets pl;

    private HashMap<String, ItemStack> docketMap = new HashMap<>();

    public RespawnListener(TreeboTickets main) {
        this.pl = main;
    }

    @EventHandler
    public boolean onPlayerDeath(PlayerDeathEvent e) {
        if (!e.getDrops().isEmpty()) {
            String key1 = UUID.randomUUID().toString();
            String key2 = UUID.randomUUID().toString();
            String key3 = key1 + "-" + key2;

            File deathFile = new File(pl.getDataFolder() + File.separator + "deaths", File.separator + e.getEntity().getUniqueId().toString() + "_" + key3 + ".yml");
            FileConfiguration deathYaml = YamlConfiguration.loadConfiguration(deathFile);


            Player p = e.getEntity();
            Location loc = e.getEntity().getLocation();
            int droppedXP = p.getTotalExperience();
            String deathMessage = e.getDeathMessage();

            List<ItemStack> itemList = e.getDrops();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            ItemStack docket = new ItemStack(Material.PAPER, 1);
            ItemMeta docketMeta = docket.getItemMeta();
            docketMeta.setDisplayName(pl.badge + "Death Docket - " + date);
            deathYaml.set("date", date);

            List<String> docketLore = new ArrayList<String>();
            docketLore.add("Player - " + p.getName());
            deathYaml.set("player", p.getName());
            deathYaml.set("uuid", p.getUniqueId().toString());
            docketLore.add("Location - " + loc.getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
            deathYaml.set("world", loc.getWorld().getName());
            deathYaml.set("xyz", loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
            docketLore.add("XP - " + droppedXP);
            deathYaml.set("experience", droppedXP);
            docketLore.add("Death Message - " + deathMessage);
            deathYaml.set("deathMessage", deathMessage);
            docketLore.add("SecretKey - " + key3);
            deathYaml.set("secretKey", key3);
            int i = 0;
            for (ItemStack item : itemList) {
                deathYaml.set("inventory.slot_" + i, item);
                i++;
                docketLore.add(item.getAmount() + " X " + item.getType().name());
                if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
                    for (Enchantment enchantment : item.getItemMeta().getEnchants().keySet()) {
                        docketLore.add(" - " + enchantment.getKey() + " LvL " + item.getItemMeta().getEnchants().get(enchantment));
                    }
                } else if (item.hasItemMeta() && item.getItemMeta() instanceof PotionMeta) {
                    docketLore.add(" - " + ((PotionMeta) item.getItemMeta()).getBasePotionData().getType() + " Ext:" + ((PotionMeta) item.getItemMeta()).getBasePotionData().isExtended() + " Upg:" + ((PotionMeta) item.getItemMeta()).getBasePotionData().isUpgraded());
                } else if (item.hasItemMeta() && item.getItemMeta().hasAttributeModifiers()) {
                    for (Attribute attrib : item.getItemMeta().getAttributeModifiers().keySet()) {
                        docketLore.add(" - " + attrib.name() + " LvL " + item.getItemMeta().getAttributeModifiers().get(attrib));
                    }
                }
            }
            docketMeta.setLore(docketLore);
            docket.setItemMeta(docketMeta);
            docketMap.put(p.getUniqueId().toString(), docket);
            deathYaml.set("used", "false");
            try {
                deathYaml.save(deathFile);
            } catch (IOException err) {
                pl.makeLog(err);
            }
        }
        return true;
    }

    @EventHandler
    public void onRespawnEvent(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (!pl.getConfig().getBoolean("deathDocket.disable")) {
            if (docketMap.containsKey(p.getUniqueId().toString())) {
                if (pl.getConfig().get("deathDocket.toggle." + p.getUniqueId()) == null || pl.getConfig().get("deathDocket.toggle." + p.getUniqueId().toString()).equals("false")) {

                    p.getInventory().setItemInOffHand(docketMap.get(p.getUniqueId().toString()));
                    p.sendMessage(pl.badge + "You been issued a death docket. You can disable this feature at any time with /toggledeathdocket, but in doing so, staff will be unable to assist with item recovery");
                } else {
                    p.sendMessage(pl.badge + "You have Death Dockets disabled. Re-enable with /toggledeathdocket");
                }
            }
        }
    }

}
