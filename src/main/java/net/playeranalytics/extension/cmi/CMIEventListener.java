package net.playeranalytics.extension.cmi;


import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.events.CMIPlayerJailEvent;
import com.Zrips.CMI.events.CMIPlayerUnjailEvent;
import com.Zrips.CMI.events.CMIUserHomeCreateEvent;
import com.Zrips.CMI.events.CMIUserHomeRemoveEvent;
import com.djrapitops.plan.extension.Caller;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class CMIEventListener implements Listener {

    private final Caller caller;

    public CMIEventListener(Caller caller) {
        this.caller = caller;
    }

    public static void register(Caller caller) {
        Plugin plan = Bukkit.getPluginManager().getPlugin("Plan");
        Bukkit.getPluginManager().registerEvents(new CMIEventListener(caller), plan);
    }

/*
    Jail events
 */

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailEntered(CMIPlayerJailEvent event) {
        CMIUser user = event.getUser();
        UUID playerUUID = user.getUniqueId();
        String playerName = user.getName();

        caller.updatePlayerData(playerUUID, playerName);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailLeft(CMIPlayerUnjailEvent event) {
        CMIUser user = event.getUser();
        UUID playerUUID = user.getUniqueId();
        String playerName = user.getName();

        caller.updatePlayerData(playerUUID, playerName);
    }

/*
    Home events
 */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailLeft(CMIUserHomeCreateEvent event) {
        CMIUser user = event.getUser();
        UUID playerUUID = user.getUniqueId();
        String playerName = user.getName();

        caller.updatePlayerData(playerUUID, playerName);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailLeft(CMIUserHomeRemoveEvent event) {
        CMIUser user = event.getUser();
        UUID playerUUID = user.getUniqueId();
        String playerName = user.getName();

        caller.updatePlayerData(playerUUID, playerName);
    }

/*
    TODO: Implement mute Listener, currently unavailable through CMI API check https://github.com/Zrips/CMI-API/issues/37

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMuteStatusChange(MuteStatusChangeEvent event) {
        IUser affected = event.getAffected();
        UUID playerUUID = affected.getBase().getUniqueId();
        String playerName = affected.getName();

        caller.updatePlayerData(playerUUID, playerName);
    }

 */

}
