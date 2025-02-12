package net.playeranalytics.extension.cmi;


import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.events.*;
import com.djrapitops.plan.extension.Caller;
import com.djrapitops.plan.settings.ListenerService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Optional;
import java.util.UUID;

public class CMIEventListener implements Listener {

    private final Caller caller;

    public CMIEventListener(Caller caller) {
        this.caller = caller;
    }

    public void register() {
        ListenerService.getInstance().registerListenerForPlan(this);
    }

/*
    Jail events
 */

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailEntered(CMIPlayerJailEvent event) {
        Optional<Player> player = Optional.ofNullable(event.getUser().getPlayer());

        player.ifPresent((p) -> caller.updatePlayerData(p.getUniqueId(), p.getName()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailLeft(CMIPlayerUnjailEvent event) {
        Optional<Player> player = Optional.ofNullable(event.getUser().getPlayer());

        player.ifPresent((p) -> caller.updatePlayerData(p.getUniqueId(), p.getName()));
    }

/*
    Home events
 */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailLeft(CMIUserHomeCreateEvent event) {
        Optional<Player> player = Optional.ofNullable(event.getUser().getPlayer());

        player.ifPresent((p) -> caller.updatePlayerData(p.getUniqueId(), p.getName()));
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onJailLeft(CMIUserHomeRemoveEvent event) {
        Optional<Player> player = Optional.ofNullable(event.getUser().getPlayer());

        player.ifPresent((p) -> caller.updatePlayerData(p.getUniqueId(), p.getName()));
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

    /*
    Money events
     */

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBalanceChange(CMIUserBalanceChangeEvent event) {
        Optional<Player> player = Optional.ofNullable(event.getUser().getPlayer());

        player.ifPresent((p) -> caller.updatePlayerData(p.getUniqueId(), p.getName()));
    }

}
