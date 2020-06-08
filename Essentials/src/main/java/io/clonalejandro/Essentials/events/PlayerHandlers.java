package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Alex
 * On 09/06/2020
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

public class PlayerHandlers implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        final Player player = event.getEntity();
        final Entity entity = player.getKiller();

        String msg = event.getDeathMessage() == null ? "" : event.getDeathMessage();

        msg = msg.replace(player.getName(), player.getDisplayName());

        if (entity != null && entity instanceof Player)
            msg = msg.replace(entity.getName(), ((Player) entity).getDisplayName());

        event.setDeathMessage(Utils.colorize("&7") + msg);
    }
}
