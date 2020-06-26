package me.shakeforprotein.treebotickets.Webhooks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;

import java.io.IOException;


public class Webhook {
    /*

    private TreeboTickets pl;

    public Webhook(TreeboTickets main) {
        this.pl = main;
    }

    public void webhook(String id, String user, String type, String contents) {
        String hookTitle = ChatColor.stripColor(pl.badge);
        String hookPing = "Pong";
        String hookURL = "1";
        String hookColor = "111111";
        String hookIcon = "";
        String authorName = "TreeboTickets";
        String authorURL = "https://treebomc.com";
        if(type!=null){
           hookURL =  pl.getConfig().getString("webhook.hookLink_" + type);
           hookPing = "";
           hookColor = pl.getConfig().getString("webhook.hookColor_" + type);
           hookIcon = pl.getConfig().getString("webhook.hookIcon_" + type);
        }

        JsonParser jsonParser = new JsonParser();
        JsonObject hookObject = new JsonObject();
        JsonElement hId = hookObject.addProperty("content", contents); {
        };


        //Curl
        String command = "";
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.getInputStream();
        } catch (
                IOException e) {
            pl.makeLog(e);
        }
    }
    */
}
