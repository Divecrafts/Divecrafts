package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.commands.TpaCmd;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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

public class TpaHandlers implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        cancelTpa(event.getPlayer());
    }

    @EventHandler
    public void onPlayerBeHitted(EntityDamageEvent event){
        if (event.getEntity() instanceof Player)
            cancelTpa((Player) event.getEntity());
    }

    @EventHandler
    public void onEntityBeHittedByEntity(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player)
            cancelTpa((Player) event.getDamager());
    }

    private void cancelTpa(Player player){
        TpaCmd.tpaUsers.forEach((key, value) -> {
            if (value == player){
                if (key.isOnline())
                    key.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha rechazado tu peticion", value.getName())));
                if (value.isOnline())
                    value.sendMessage(Main.translate("&c&lServer> &fPetición de teletransporte cancelada"));

                TpaCmd.tpaUsers.remove(key);
                TpaCmd.tpaType.remove(key);

                if (Main.awaitingPlayersToTeleport.containsKey(value))
                    Main.awaitingPlayersToTeleport.get(value).cancel();
            }
        });
    }
}
