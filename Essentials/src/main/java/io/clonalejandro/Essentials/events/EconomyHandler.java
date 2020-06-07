package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.utils.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

/**
 * Created by Alex
 * On 07/06/2020
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

public class EconomyHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();

        if (Economy.economyPlayers.containsKey(player))
            Economy.economyPlayers.remove(player);

        try {
            Economy.economyPlayers.put(player, new Economy(player.getUniqueId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        final Player player = event.getPlayer();

        if (Economy.economyPlayers.containsKey(player)){
            try {
                Economy.economyPlayers.get(player).save();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        final Player player = event.getPlayer();

        if (Economy.economyPlayers.containsKey(player)){
            try {
                Economy.economyPlayers.get(player).save();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
