package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alex
 * On 02/06/2020
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

    public static final List<String> disabledWorlds = Collections.singletonList("eventos");

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event){
        final Player player = event.getPlayer();
        toggleFly(player, disabledWorlds.contains(player.getWorld().getName().toLowerCase()));
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        toggleFly(player, disabledWorlds.contains(player.getWorld().getName().toLowerCase()));
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event){
        final Player player = event.getPlayer();
        toggleFly(player, disabledWorlds.contains(player.getWorld().getName().toLowerCase()));
    }

    @EventHandler
    public void onPlayerChangeEvent(PlayerChannelEvent event){
        final Player player = event.getPlayer();
        toggleFly(player, disabledWorlds.contains(player.getWorld().getName().toLowerCase()));
    }

    private void toggleFly(final Player player, final boolean mode){
        if (!player.getAllowFlight()) return;

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            SUser user = SServer.getUser(player.getUniqueId());

            if (user != null && user.getUserData().getRank().getRank() < SCmd.Rank.BUILDER.getRank()) {
                if (player.getAllowFlight()) {
                    player.setAllowFlight(!mode);
                    player.setFlying(!mode);
                }
            }
        }, 20L);
    }
}
