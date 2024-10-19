package net.playeranalytics.extension.CMI;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.djrapitops.plan.extension.DataExtension;
import com.djrapitops.plan.extension.NotReadyException;
import com.djrapitops.plan.extension.annotation.DoubleProvider;
import com.djrapitops.plan.extension.annotation.PluginInfo;
import com.djrapitops.plan.extension.icon.Color;
import com.djrapitops.plan.extension.icon.Family;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@PluginInfo(name = "CMIEco", iconName = "coins", iconFamily = Family.SOLID, color = Color.DEEP_ORANGE)
public class CMIEcoExtension implements DataExtension {

    public static AtomicBoolean enabled = new AtomicBoolean();

    private CMI cmi;

    CMIEcoExtension(boolean b) {
        /* For test construction */
    }

    CMIEcoExtension() {
        cmi = JavaPlugin.getPlugin(CMI.class);
        enabled.set(cmi.getEconomyManager().isEnabled());
    }

    @DoubleProvider(
            text = "Balance", iconName = "coins", priority = 60, iconColor = Color.GREEN
    )
    public double balance(UUID playerUUID) {
        if (!cmi.getEconomyManager().isEnabled()) {
            throw new NotReadyException();
        }

        return getUser(playerUUID).getBalance();
    }

    private CMIUser getUser(UUID playerUUID) {
        CMIUser user = cmi.getPlayerManager().getUser(playerUUID);
        if (user == null) throw new NotReadyException();
        return user;
    }

}