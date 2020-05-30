package io.clonalejandro.Essentials.hooks;

import io.clonalejandro.Essentials.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

/**
 * Created by Alex
 * On 30/05/2020
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class VaultHook {

    private Economy provider;

    public void hook(){
        provider = Main.instance.economyProvider;
        Bukkit.getServicesManager().register(Economy.class, this.provider, Main.instance, ServicePriority.Normal);
    }

    public void unhook(){
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
    }
}
