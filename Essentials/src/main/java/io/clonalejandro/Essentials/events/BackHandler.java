package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.commands.BackCmd;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Alex
 * On 02/05/2020
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

public class BackHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        BackCmd.lastLocation.put(event.getEntity(), event.getEntity().getLocation());
    }
}
