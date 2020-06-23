package io.clonalejandro.SQLAlive.events;

import io.clonalejandro.SQLAlive.SQLAlive;
import io.clonalejandro.SQLAlive.tasks.InjectorTask;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Alex
 * On 24/06/2020
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
 * All rights reserved for clonalejandro ©SQLAlive 2017/2020
 */

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if (SQLAlive.getTask() != null){
            SQLAlive.getTask().cancel();
            SQLAlive.setTask(null);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        playerLeaveCallback();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playerLeaveCallback();
    }

    private void playerLeaveCallback(){
        if (Bukkit.getOnlinePlayers().size() == 0){
            SQLAlive.setTask(new InjectorTask().runTaskTimerAsynchronously(SQLAlive.getPlugin(), 1L, 60 * 20L));
        }
    }
}
