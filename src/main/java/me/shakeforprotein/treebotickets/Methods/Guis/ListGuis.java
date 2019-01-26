package me.shakeforprotein.treebotickets.Methods.Guis;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.entity.Player;

public class ListGuis {

    private TreeboTickets pl;
    private GuiStaffList guiStaffList;

    public ListGuis(TreeboTickets main){
        pl = main;
        this.guiStaffList = new GuiStaffList(main);
    }

    public void listAssignedGui(Player p) {
        guiStaffList.guiStaffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='" + p.getName() + "' AND STATUS='OPEN' ORDER BY id DESC", "Ticket List - Assigned to you");
    }

    public void listUnassignedGui(Player p){
        guiStaffList.guiStaffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED' ORDER BY id DESC", "Ticket List - Unassigned ALL");
    }

    public void listOpenGui(Player p){
        guiStaffList.guiStaffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN' ORDER BY id DESC", "Ticket List - Open ALL");
    }

    public void listClosedGui(Player p){
        guiStaffList.guiStaffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='CLOSED' AND STAFF!='DELETED' ORDER BY id DESC", "Ticket List - Closed ALL");
    }

    public void builderListOpenGui(Player p){
        guiStaffList.guiStaffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN' AND STAFF='Builders' ORDER BY id DESC", "Ticket List - Builder List");
    }
}
