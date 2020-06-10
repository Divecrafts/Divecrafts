package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
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
        if (event.getFrom().getX() != event.getTo().getX() ||
                event.getFrom().getY() != event.getTo().getY() ||
                event.getFrom().getZ() != event.getTo().getZ())
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
        if (Main.awaitingPlayersToTeleport.containsKey(player)) {
            if (!checkPermissions(player, SCmd.Rank.MEGALODON)) return;

            Main.awaitingPlayersToTeleport.get(player).cancel();
            player.sendMessage(Main.translate("&c&lServer> &fTeletransporte cancelado"));
            Main.awaitingPlayersToTeleport.remove(player);
        }
    }

    public boolean checkPermissions(Player player, SCmd.Rank rank){
        final SUser user = SServer.getUser(player.getUniqueId());
        return user.getUserData().getRank().getRank() < rank.getRank();
    }
}
