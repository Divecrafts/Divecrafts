package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.commands.FlyCmd;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Alex
 * On 30/04/2020
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

public class FlyHandler implements Listener {

    @EventHandler
    public void onFlyerJoin(PlayerJoinEvent event){
        if (FlyCmd.players.contains(event.getPlayer().getName())){
            event.getPlayer().setAllowFlight(true);
            event.getPlayer().setFlying(true);
        }
    }
}
