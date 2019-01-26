package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.Methods.AdminDeleteTicket;
import me.shakeforprotein.treebotickets.Methods.DatabaseMaintenance.CleanupDatabase;
import me.shakeforprotein.treebotickets.Methods.Helps.TbTicketAdminHelp;
import me.shakeforprotein.treebotickets.Methods.Lists.AdminStaffList;
import me.shakeforprotein.treebotickets.Methods.Lists.StaffList;
import me.shakeforprotein.treebotickets.Methods.Stats.AdminStats;
import me.shakeforprotein.treebotickets.Methods.Teleports.StaffTp;
import me.shakeforprotein.treebotickets.Methods.TicketAssignments.AdminAssign;
import me.shakeforprotein.treebotickets.Methods.TicketCloses.AdminClose;
import me.shakeforprotein.treebotickets.Methods.TicketUpdates.AdminUpdate;
import me.shakeforprotein.treebotickets.Methods.ViewTickets.StaffViewTicket;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.shakeforprotein.treebotickets.Commands.Commands.isNumeric;

public class TbTicketAdmin implements CommandExecutor {
    private TreeboTickets pl;
    private TbTicketAdminHelp ticketAdminHelp;
    private StaffList staffList;
    private CleanupDatabase cleanupDatabase;
    private AdminStats adminStats;
    private AdminStaffList adminListStaff;
    private StaffTp staffTp;
    private AdminClose adminClose;
    private AdminDeleteTicket adminDeleteTicket;
    private AdminUpdate adminUpdate;
    private AdminAssign adminAssign;
    private StaffViewTicket staffViewTicket;

    public TbTicketAdmin(TreeboTickets main) {
        pl = main;


        this.ticketAdminHelp = new TbTicketAdminHelp(main);
        this.staffList = new StaffList(main);
        this.cleanupDatabase = new CleanupDatabase(main);
        this.adminStats = new AdminStats(main);
        this.adminListStaff = new AdminStaffList(main);
        this.staffTp = new StaffTp(main);
        this.adminClose = new AdminClose(main);
        this.adminDeleteTicket = new AdminDeleteTicket(main);
        this.adminUpdate = new AdminUpdate(main);
        this.adminAssign = new AdminAssign(main);
        this.staffViewTicket = new StaffViewTicket(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();

            if (cmd.getName().equalsIgnoreCase("tbticketadmin") && p.hasPermission("tbtickets.admin")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("stats") && p.hasPermission("tbtickets.admin")){adminStats.adminStats(p);}
                else if (args.length == 1 && args[0].equalsIgnoreCase("version") && p.hasPermission("tbtickets.admin")){p.sendMessage(pl.getConfig().getString("networkName") + " - Version:  " +  pl.getDescription().getVersion());}
                else if (args.length == 1 && args[0].equalsIgnoreCase("reload") && p.hasPermission("tbtickets.admin")){pl.reloadConfig(); p.sendMessage(pl.getConfig().getString("networkName") +  " plugin config reloaded");}
                else if (args.length == 1 && args[0].equalsIgnoreCase("trim") && p.hasPermission("tbtickets.admin")){
                    cleanupDatabase.cleanupDatabase(40);}
                else if (args.length == 2 && args[0].equalsIgnoreCase("trim") && p.hasPermission("tbtickets.admin")){
                    cleanupDatabase.cleanupDatabase(Integer.parseInt(args[1]));
                }

                else if (args.length < 2) {
                    ticketAdminHelp.tbTicketAdminHelp(p);

                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list") && (p.hasPermission("tbtickets.admin")) || p.hasPermission("tickets.view.any")){
                        if (args[1].equalsIgnoreCase("unassigned")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED' AND STATUS='OPEN' ORDER BY id DESC");
                        }
                        else if (args[1].equalsIgnoreCase("assigned")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF!='UNASSIGNED' AND STATUS='OPEN' ORDER BY id DESC");
                        }
                        else if (args[1].equalsIgnoreCase("open")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN' ORDER BY id DESC");
                        }
                        else if (args[1].equalsIgnoreCase("closed")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='CLOSED' ORDER BY id DESC");
                        }
                        else if (args[1].equalsIgnoreCase("deleted")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='DELETED' ORDER BY id DESC");
                        }
                        else if (args[1].equalsIgnoreCase("idea")) {
                            staffList.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE TYPE='Idea' AND STATUS='OPEN' ORDER BY id DESC");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("staffList")) {
                        adminListStaff.adminListStaff(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='" + args[1] + "' ORDER BY id DESC", args[1]);
                    }
                    else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.admin")){
                        adminClose.adminCloseTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("delete") && p.hasPermission("tbtickets.admin")){
                        adminDeleteTicket.adminDeleteTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.admin")){
                        staffViewTicket.staffViewTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("tp")) {
                        if (isNumeric(args[1])) {
                            staffTp.staffTP(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + args[1] + "'", Integer.parseInt(args[1]));
                        }
                    }

                }

                else if (args.length >= 3 ){

                    if (args[0].equalsIgnoreCase("assign")) {
                        adminAssign.adminAssign(p, Integer.parseInt(args[1]), args[2]);
                    }

                    else if(args[0].equalsIgnoreCase("update")) {
                        StringBuilder staffText = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            staffText.append(args[i] + " ");
                        }

                        adminUpdate.adminUpdate(p, Integer.parseInt(args[1]), staffText.toString());
                    }
                }
                else{ticketAdminHelp.tbTicketAdminHelp(p);}
            }
            else if (cmd.getName().equalsIgnoreCase("tbticketadmin") && !p.hasPermission("tbtickets.admin")){p.sendMessage(ChatColor.RED + "You lack the required permissions for these commands");}
        }
        return true;
    }
}
