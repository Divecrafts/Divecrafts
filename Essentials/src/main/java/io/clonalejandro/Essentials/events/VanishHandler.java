package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.utils.VanishPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Alex
 * On 01/05/2020
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

public class VanishHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        VanishPacket.getVanishPlayers().forEach(vanishPlayer -> {
            final Player player = Bukkit.getPlayer(vanishPlayer);
            if (player != null)
                VanishPacket.vanishForPlayer(player, event.getPlayer());
        });
    }
}
