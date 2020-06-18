package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.task.NoCleanTask;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

/**
 * Created by alejandrorioscalera
 * On 19/2/18
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
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2018
 */

class NoClean implements Listener {


    /** SMALL CONSTRUCTORS **/

    private ArrayList<Player> invincibles = new ArrayList<>();
    private final Main plugin;

    NoClean(Main instance){
        plugin = instance;
        Api.PLUGIN_MANAGER.registerEvents(this, plugin);
    }


    /** REST **/

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        playerDeather(event);
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        playerDamagerByPlayer(event);
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        playerDamager(event);
    }


    /**
     * This function return a invincibles list
     * @return
     */
    public ArrayList<Player> getInvincibles() {
        return invincibles;
    }


    /** OTHERS **/

    /**
     * This function manage the function onPlayerDeath
     * @param event
     */
    private void playerDeather(final PlayerDeathEvent event){
        final Player player = event.getEntity();
        final Player killer = player.getKiller();

        invincibles.add(killer);
        runTask(killer);
    }


    /**
     * This function manage the task of this process
     * @param killer
     * @return
     */
    private NoCleanTask runTask(final Player killer){
        final NoCleanTask task = new NoCleanTask(invincibles, killer);
        task.runTaskTimer(plugin, 1L, 20L);
        return task;
    }


    /**
     * This function manage the function onEntityDamageByEntity
     * @param event
     */
    private void playerDamagerByPlayer(final EntityDamageByEntityEvent event){
        final Entity entity = event.getEntity();
        final Entity damager = event.getDamager();

        if (isPlayer(entity) && isPlayer(damager)){
            final Player playerEntity = (Player) entity;
            final Player playerDamager = (Player) damager;

            if (invincibles.contains(playerDamager))
                invincibles.remove(playerDamager);

            if (invincibles.contains(playerEntity))
                event.setCancelled(true);
        }
    }


    /**
     * This function manage the function onEntityDamage
     * @param event
     */
    private void playerDamager(final EntityDamageEvent event){
        final Entity entity = event.getEntity();

        if (isPlayer(entity)){
            final Player player = (Player) entity;

            if (invincibles.contains(player))
                event.setCancelled(true);
        }
    }


    /**
     * This function check if entity is player
     * @param entity
     * @return
     */
    private boolean isPlayer(final Entity entity){
        return entity.getType() == EntityType.PLAYER;
    }


}
