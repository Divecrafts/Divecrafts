package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.commands.TpaCmd;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Iterator;

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
        if (checkPermissions(player, SCmd.Rank.MEGALODON)) return;

        if (Main.awaitingPlayersToTeleport.containsKey(player)) {
            Main.awaitingPlayersToTeleport.get(player).cancel();
            player.sendMessage(Main.translate("&c&lServer> &fTeletransporte cancelada"));
            Main.awaitingPlayersToTeleport.remove(player);
        }
    }

    public boolean checkPermissions(Player player, SCmd.Rank rank){
        final SUser user = SServer.getUser(player.getUniqueId());

        if (user.getUserData().getRank().getRank() < rank.getRank())
            return sendErrMessage(player);

        return false;
    }

    private boolean sendErrMessage(Player player){
        player.sendMessage(Utils.colorize("&c&lServer> &fNo tienes permisos para hacer eso!"));
        return true;
    }
}
