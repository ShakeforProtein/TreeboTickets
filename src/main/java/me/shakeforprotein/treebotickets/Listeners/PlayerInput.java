package me.shakeforprotein.treebotickets.Listeners;

import me.shakeforprotein.treebotickets.Commands.Commands;
import me.shakeforprotein.treebotickets.TreeboTickets;
import me.shakeforprotein.treebotickets.UpdateChecker.UpdateChecker;
import org.bukkit.event.Listener;

public class PlayerInput implements Listener {


    private TreeboTickets pl;
    private UpdateChecker uc;
    private Commands cmds;

    public PlayerInput(TreeboTickets main) {
        pl = main;
        this.cmds = cmds;
        this.uc = new UpdateChecker(pl);
    }

}





