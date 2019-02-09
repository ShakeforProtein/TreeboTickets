package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class RetrieveOntime {

    private TreeboTickets pl;
    public RetrieveOntime(TreeboTickets main){this.pl = main;}

    public void retrieveOntime(String  p, CommandSender s) {
        String query = "SELECT * FROM `"+ pl.ontimetable +"` WHERE `CurrentName`=\"" + p + "\" ORDER BY ID DESC LIMIT 1";

        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery(query);
            if(response.next()) {

                String uUID = (response.getString("UUID"));
                String currentName = response.getString("CurrentName");
                String currentOnRaw = response.getString("CurrentOn");
                String totalOnRaw = response.getString("TotalOn");
                String currentIP = response.getString("CurrentIP");
                String otherNames = response.getString("OtherNames");
                String firstJoinRaw = response.getString("FirstJoin");
                String lastOffRaw = response.getString("LastLeft");
                Long totalOn = Long.parseLong(totalOnRaw);
                Long firstJoin = Long.parseLong((firstJoinRaw));
                Long lastOff = Long.parseLong(lastOffRaw);
                Long currentOn = Long.parseLong(currentOnRaw);
                Long currentTime = System.currentTimeMillis();
                String cColour;

                int oneDay = 1000 * 60 * 60 * 24;

                if (currentTime - lastOff > (oneDay * 7)){
                    cColour = ChatColor.RED + "";
                }
                else if (currentTime - lastOff > (oneDay * 3)){
                    cColour = ChatColor.YELLOW + "";
                }
                else {
                    cColour = ChatColor.GREEN + "";
                }



                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = format.format( new Date());

                    String fjDateStr = format.format(new Date(firstJoin));
                    String coDateStr = format.format(new Date(currentOn));
                    String llDateStr = format.format(new Date(lastOff));
                    String cTime = format.format(new Date(currentTime));

                String fjdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(firstJoin), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + fjDateStr;
                String codt = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentOn), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + coDateStr;
                String lldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastOff), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + llDateStr;
                String cdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + llDateStr;

                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Current Time  -  " + ChatColor.RESET + cdt + " - (" + cTime + ")");
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "UUID  -  " + ChatColor.RESET + uUID);
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "CurrentName  -  "+ ChatColor.RESET + currentName);
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "OtherNames  -  " + ChatColor.RESET + otherNames);
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "CurrentIP  -  " + ChatColor.RESET + currentIP);
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "FirstJoin  -  " + ChatColor.RESET + "" + fjdt + " - (" + firstJoin + ")");
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "LatestOntime  -  " + ChatColor.RESET + ChatColor.GREEN + codt +" - (" + currentOn + ")");
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "LastLeft  -  " + ChatColor.RESET + cColour + lldt + " - (" + lastOff + ")");
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Offline for - " + ChatColor.RESET + cColour + formatTime(currentTime - lastOff));
                s.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "TotalOntime  -  " + ChatColor.RESET + ChatColor.GOLD + formatTime(totalOn));

            }
            else{s.sendMessage(ChatColor.RED + "No Data Matching that player name");}
        } catch (SQLException e) {
            System.out.println("Encountered " + e.toString() + " during retrieveOntime()");
            pl.makeLog(e);
        }
    }

public static String formatTime(long millis) {
    long seconds = Math.round((double) millis / 1000);
    long hours = TimeUnit.SECONDS.toHours(seconds);
    if (hours > 0)
        seconds -= TimeUnit.HOURS.toSeconds(hours);
    long minutes = seconds > 0 ? TimeUnit.SECONDS.toMinutes(seconds) : 0;
    if (minutes > 0)
        seconds -= TimeUnit.MINUTES.toSeconds(minutes);
    return hours > 0 ? String.format("%02dH:%02dM:%02dS", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
}
}