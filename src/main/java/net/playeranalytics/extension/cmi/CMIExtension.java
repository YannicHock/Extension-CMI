/*
    Copyright(c) 2021 AuroraLS3

    The MIT License(MIT)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files(the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions :
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
*/
package net.playeranalytics.extension.cmi;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.djrapitops.plan.extension.CallEvents;
import com.djrapitops.plan.extension.DataExtension;
import com.djrapitops.plan.extension.FormatType;
import com.djrapitops.plan.extension.NotReadyException;
import com.djrapitops.plan.extension.annotation.*;
import com.djrapitops.plan.extension.icon.Color;
import com.djrapitops.plan.extension.icon.Family;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * DataExtension.
 *
 * @author YannicHock
 */
@PluginInfo(name = "CMI", iconName = "gear", iconFamily = Family.SOLID, color = Color.DEEP_ORANGE)
@TabInfo(tab = "Money", iconName = "coins", elementOrder = {})
@TabInfo(tab = "General Info", iconName = "list", elementOrder = {})
public class CMIExtension implements DataExtension {

    private CMI cmi;

    CMIExtension(boolean b) {
        /* For test construction */
    }

    CMIExtension() {
        cmi = JavaPlugin.getPlugin(CMI.class);
    }

    @Override
    public CallEvents[] callExtensionMethodsOn() {
        return new CallEvents[]{
                CallEvents.PLAYER_JOIN, CallEvents.PLAYER_LEAVE,
                CallEvents.SERVER_EXTENSION_REGISTER, CallEvents.SERVER_PERIODICAL
        };
    }

    private Optional<CMIUser> getUser(UUID playerUUID) {
        CMIUser user = cmi.getPlayerManager().getUser(playerUUID);
        return Optional.ofNullable(user);
    }

    /*
    General Info
     */

    @BooleanProvider(
            text = "Jailed", iconName = "ban", priority = 100, iconColor = Color.DEEP_ORANGE, conditionName = "isJailed"
    )
    @Tab("General Info")
    public boolean isJailed(UUID playerUUID) {
        return getUser(playerUUID).map(CMIUser::isJailed).orElse(false);
    }

    @Conditional("isJailed")
    @NumberProvider(
            text = "Jail Expires", iconName = "calendar-times", priority = 99, iconFamily = Family.REGULAR, iconColor = Color.DEEP_ORANGE, format = FormatType.DATE_SECOND
    )
    @Tab("General Info")
    public long jailTimeout(UUID playerUUID) {
        return getUser(playerUUID).map(CMIUser::getJailedForTime).orElse(0L);
    }

    @BooleanProvider(
            text = "Muted", iconName = "bell-slash", priority = 80, iconColor = Color.BLUE_GREY, conditionName = "isMuted"
    )
    @Tab("General Info")
    public boolean isMuted(UUID playerUUID) {
        return getUser(playerUUID).map(CMIUser::isMuted).orElse(false);
    }

    @Conditional("isMuted")
    @NumberProvider(
            text = "Mute Expires", iconName = "calendar-times", priority = 79, iconFamily = Family.REGULAR, iconColor = Color.BLUE_GREY, format = FormatType.DATE_SECOND
    )
    @Tab("General Info")
    public long muteTimeout(UUID playerUUID) {
        return getUser(playerUUID).map(CMIUser::getMutedUntil).orElse(0L);
    }

    @StringProvider(
            text = "homes", iconName = "home", priority = 70, iconColor = Color.GREEN
    )
    @Tab("General Info")
    public String playerHomes(UUID playerUUID) {
        ArrayList<String> homes = getUser(playerUUID).map(CMIUser::getHomesList).orElse(new ArrayList<>());

        Collections.sort(homes);
        if (homes.isEmpty()) {
            return "-";
        }

        Collections.sort(homes);

        StringBuilder homeString = new StringBuilder();
        int size = homes.size();

        for (int i = 0; i < size; i++) {
            homeString.append(homes.get(i));
            if (i < size - 1) {
                homeString.append(", ");
            }
        }

        return homeString.toString();
    }


    /*
    Money
     */
    @DoubleProvider(
            text = "Balance", iconName = "coins", priority = 60, iconColor = Color.GREEN
    )
    @Tab("Money")
    public double balance(UUID playerUUID) {
        if (!cmi.getEconomyManager().isEnabled()) {
            throw new NotReadyException();
        }
        return getUser(playerUUID).map(CMIUser::getBalance).orElse(0.0);
    }
}