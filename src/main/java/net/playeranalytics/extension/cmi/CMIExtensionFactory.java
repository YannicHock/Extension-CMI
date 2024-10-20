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

import com.djrapitops.plan.extension.Caller;
import com.djrapitops.plan.extension.DataExtension;
import com.djrapitops.plan.settings.ListenerService;
import org.bukkit.Bukkit;

import java.util.Optional;

/**
 * Factory for DataExtension.
 *
 * @author YannicHock
 */
public class CMIExtensionFactory {

    private boolean isAvailable() {
        try {
            Class.forName("com.Zrips.CMI.CMI");
            return Bukkit.getPluginManager().isPluginEnabled("CMI");
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public Optional<DataExtension> createCMIExtension() {
        if (isAvailable()) {
            return Optional.of(new CMIExtension());
        }
        return Optional.empty();
    }


    public void registerCMIUpdateListeners(Caller caller) {
       new CMIEventListener(caller).register();
    }

}