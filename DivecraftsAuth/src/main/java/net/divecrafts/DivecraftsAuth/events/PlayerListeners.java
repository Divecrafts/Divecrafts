package net.divecrafts.DivecraftsAuth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Alex
 * On 03/06/2020
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
 * All rights reserved for clonalejandro ©DivecraftsAuth 2017/2020
 */

public class PlayerListeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        event.setLeaveMessage(null);
    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            ((Player) event.getEntity()).setHealth(20);
        }
    }

    @EventHandler
    public void onPlayerHasHunger(FoodLevelChangeEvent event){
        if (event.getEntity() instanceof Player){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        event.setCancelled(true);
    }
}
