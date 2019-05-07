package me.shakeforprotein.treebotickets.Listeners.InventoryEvents;

        import me.shakeforprotein.treebotickets.Methods.Guis.ListGuis;
        import me.shakeforprotein.treebotickets.TreeboTickets;
        import org.bukkit.Bukkit;
        import org.bukkit.configuration.file.FileConfiguration;
        import org.bukkit.configuration.file.YamlConfiguration;
        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.inventory.InventoryClickEvent;
        import org.bukkit.inventory.Inventory;
        import org.bukkit.scheduler.BukkitRunnable;

        import java.io.File;
        import java.util.Arrays;
        import java.util.Set;

public class HubMenuInventoryListener implements Listener {

    private TreeboTickets pl;

    public HubMenuInventoryListener(TreeboTickets main) {
        pl = main;
    }


    @EventHandler
    public void invClickEvent(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        String name = e.getView().getTitle();
        String clickedButton = "";
        int slot = e.getSlot();
        if(e.getView().getTitle().toLowerCase().equals("hubmenu")){
            File menuYml = new File(pl.getDataFolder(), File.separator + "hubMenu.yml");
            FileConfiguration hubMenu = YamlConfiguration.loadConfiguration(menuYml);

            try{
                Set menuItems = hubMenu.getConfigurationSection("hubmenu.menuItems").getKeys(false);
                String[] menuItemStrings = Arrays.copyOf(menuItems.toArray(), menuItems.size(), String[].class);

                for(String item : menuItemStrings) {
                    int position = hubMenu.getInt("hubmenu.menuItems." + item + ".position");
                    if (slot == position) {
                        clickedButton = item;
                        break;
                    }
                }

                if(!clickedButton.equals("")){
                    String command;
                    String executor;
                    command = hubMenu.getString("hubmenu.menuItems." + clickedButton + ".command");
                    executor = hubMenu.getString("hubmenu.menuItems." + clickedButton + ".executor");
                    if(executor.equals("console")){
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    }
                    else{
                        Bukkit.dispatchCommand(e.getWhoClicked(), command);
                    }
                    //e.getWhoClicked().sendMessage(command + "\n" + executor + "\n" + clickedButton);
                }
            }
            catch (Exception err){
                pl.makeLog(err);
            }
            e.setCancelled(true);

        }
    }
}