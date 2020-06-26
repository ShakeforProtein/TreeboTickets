package me.shakeforprotein.treebotickets.Methods.ConnectionLoging;

import me.shakeforprotein.treebotickets.TreeboTickets;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class RetrieveOntime {

    private TreeboTickets pl;

    public RetrieveOntime(TreeboTickets main) {
        this.pl = main;
    }


    public void retrieveOntime(String p, CommandSender s, String t) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {

                String query = "SELECT * FROM `" + pl.ontimetable + "` WHERE `CurrentName`=\"" + p + "\" ORDER BY ID DESC LIMIT 1";

                ResultSet response;
                try {
                    response = pl.connection.createStatement().executeQuery(query);
                    if (response.next()) {
                        TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("");

                        String uUID = (response.getString("UUID"));
                        String currentName = response.getString("CurrentName");
                        String currentOnRaw = response.getString("CurrentOn");
                        String totalOnRaw = response.getString("TotalOn");
                        String currentIP = response.getString("CurrentIP");
                        String otherNames = response.getString("OtherNames");
                        String firstJoinRaw = response.getString("FirstJoin");
                        String lastOffRaw = response.getString("LastLeft");
                        String timeAFK = response.getString("AFKTIME");
                        Long totalOn = Long.parseLong(totalOnRaw);
                        Long firstJoin = Long.parseLong((firstJoinRaw));
                        Long lastOff = Long.parseLong(lastOffRaw);
                        Long currentOn = Long.parseLong(currentOnRaw);
                        Long currentTime = System.currentTimeMillis();
                        String cColour;

                        int oneDay = 1000 * 60 * 60 * 24;

                        if (currentTime - lastOff > (oneDay * 5)) {
                            cColour = ChatColor.RED + "";
                        } else if (currentTime - lastOff > (oneDay * 3)) {
                            cColour = ChatColor.YELLOW + "";
                        } else {
                            cColour = ChatColor.GREEN + "";
                        }


                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        String fjDateStr = format.format(new Date(firstJoin));
                        String coDateStr = format.format(new Date(currentOn));
                        String llDateStr = format.format(new Date(lastOff));
                        String cTime = format.format(new Date(currentTime));


                        String fjdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(firstJoin), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + fjDateStr;
                        String codt = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentOn), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + coDateStr;
                        String lldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastOff), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + llDateStr;
                        String cdt = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), TimeZone.getDefault().toZoneId()).toString().split("T")[1] + " - " + cTime;

                        if (t.equalsIgnoreCase("minimal")) {
                            //s.sendMessage(pl.badge + "Retrieving data for " + currentName);
                            msg.addExtra(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "User: " + cColour + ChatColor.BOLD  + currentName);

                            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                                    ChatColor.AQUA + "" + ChatColor.BOLD + "LastLeft  -  " + ChatColor.RESET + cColour + lldt + " - (" + lastOff + ")\n" +
                                    ChatColor.AQUA + "" + ChatColor.BOLD + "Offline for - " + ChatColor.RESET + cColour + formatTime(currentTime - lastOff) + "\n" +
                                    ChatColor.AQUA + "" + ChatColor.BOLD + "Time AFK - " + ChatColor.RESET + cColour + formatTime(Integer.parseInt(timeAFK) * 60000)
                            ).create());
                            msg.setHoverEvent(hoverEvent);
                            s.spigot().sendMessage(msg);
                            //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "User: " + ChatColor.RESET + currentName);
                            //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "LastLeft  -  " + ChatColor.RESET + cColour + lldt + " - (" + lastOff + ")");
                            //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Offline for - " + ChatColor.RESET + cColour + formatTime(currentTime - lastOff));
                            //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Time AFK - " + ChatColor.RESET + cColour + formatTime(Integer.parseInt(timeAFK) * 60000));
                        }
                        else if (t.equalsIgnoreCase("personal")){
                            s.sendMessage(pl.badge + "Retrieving data for " + currentName);
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "FirstJoin  -  " + ChatColor.RESET + "" + fjdt + " - (" + firstJoin + ")");
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "TotalOntime  -  " + ChatColor.RESET + ChatColor.GOLD + formatTime(totalOn));
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Time AFK - " + ChatColor.RESET + cColour + formatTime(Integer.parseInt(timeAFK) * 60000));
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Adjusted On Time - " + ChatColor.RESET + cColour + formatTime(totalOn - (Integer.parseInt(timeAFK) * 60000)));

                        }
                        else if (t.equalsIgnoreCase("true")) {
                            //s.sendMessage(pl.badge + "Retrieving data for " + currentName);
                            msg.addExtra(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "User: " + cColour + ChatColor.BOLD + currentName);
                            if (cColour.equals(ChatColor.YELLOW + "") || cColour.equals(ChatColor.RED + "")) {
                                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                                        ChatColor.AQUA + "" + ChatColor.BOLD + "LastLeft  -  " + ChatColor.RESET + cColour + lldt + " - (" + lastOff + ")\n" +
                                             ChatColor.AQUA + "" + ChatColor.BOLD + "Offline for - " + ChatColor.RESET + cColour + formatTime(currentTime - lastOff) +"\n" +
                                             ChatColor.AQUA + "" + ChatColor.BOLD + "Time AFK - " + ChatColor.RESET + cColour + formatTime(Integer.parseInt(timeAFK) * 60000)
                                ).create());
                                msg.setHoverEvent(hoverEvent);
                                //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "User: " + ChatColor.RESET + currentName);
                                //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "LastLeft  -  " + ChatColor.RESET + cColour + lldt + " - (" + lastOff + ")");
                                //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Offline for - " + ChatColor.RESET + cColour + formatTime(currentTime - lastOff));
                                //s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Time AFK - " + ChatColor.RESET + cColour + formatTime(Integer.parseInt(timeAFK) * 60000));
                            s.spigot().sendMessage(msg);
                            }

                        } else {

                            s.sendMessage(pl.badge + "Retrieving data for " + currentName);
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Current Time  -  " + ChatColor.RESET + cdt + " - (" + cTime + ")");
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "UUID  -  " + ChatColor.RESET + uUID);
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "CurrentName  -  " + ChatColor.RESET + currentName);
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "OtherNames  -  " + ChatColor.RESET + otherNames);
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "CurrentIP  -  " + ChatColor.RESET + currentIP);
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "FirstJoin  -  " + ChatColor.RESET + "" + fjdt + " - (" + firstJoin + ")");
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Latest Connect  -  " + ChatColor.RESET + ChatColor.GREEN + codt + " - (" + currentOn + ")");
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Latest Disconnect  -  " + ChatColor.RESET + cColour + lldt + " - (" + lastOff + ")");
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Offline for - " + ChatColor.RESET + cColour + formatTime(currentTime - lastOff));
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "TotalOntime  -  " + ChatColor.RESET + ChatColor.GOLD + formatTime(totalOn));
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Time AFK - " + ChatColor.RESET + cColour + formatTime(Integer.parseInt(timeAFK) * 60000));
                            s.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.AQUA + "" + ChatColor.BOLD + "Adjusted On Time - " + ChatColor.RESET + cColour + formatTime(totalOn - (Integer.parseInt(timeAFK) * 60000)));

                        }


                    } else {
                        s.sendMessage(pl.err + "No Data Matching that player name");
                    }
                } catch (SQLException e) {
                    System.out.println(pl.err + "Encountered " + e.toString() + " during retrieveOntime()");
                    pl.makeLog(e);
                }

            }
        });
    }




    public static String formatTime(long millis) {
        if (millis == 0) {
            return "0S";
        } else {
            long seconds = Math.round((double) millis / 1000);
            long days = seconds / 86400;
            seconds = seconds - (days * 86400);
            long hours = seconds / 3600;
            seconds = seconds - (hours * 3600);
            long minutes = seconds / 60;
            seconds = seconds - (minutes * 60);
            String output = "";
            if (days > 0) {
                output += days + "D:" + hours + "H:" + minutes + "M:" + seconds + "S";
            } else if (hours > 0) {
                output += hours + "H:" + minutes + "M:" + seconds + "S";
            } else if (minutes > 0) {
                output += minutes + "M:" + seconds + "S";
            } else if (seconds > 0) {
                output += seconds + "S";
            } else {
                output += "ERROR ERROR ERROR ERROR ERROR";
            }

            return output;
        }
    }
}