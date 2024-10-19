package net.playeranalytics.extension.cmi;

import com.Zrips.CMI.events.CMIUserBalanceChangeEvent;
import com.djrapitops.plan.extension.Caller;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CMIEcoEventListener implements Listener {
    private final Caller caller;

    public CMIEcoEventListener(Caller caller) {
        this.caller = caller;
    }

    public static void register(Caller caller) {
        Plugin plan = Bukkit.getPluginManager().getPlugin("Plan");
        Bukkit.getPluginManager().registerEvents(new CMIEcoEventListener(caller), plan);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBalanceChange(CMIUserBalanceChangeEvent event) {
        Player player = event.getUser().getPlayer();

        caller.updatePlayerData(player.getUniqueId(), player.getName());
    }
}
