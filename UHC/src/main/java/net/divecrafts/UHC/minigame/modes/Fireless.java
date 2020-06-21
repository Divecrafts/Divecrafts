package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

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

class Fireless implements Listener {


    /** REST **/

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        entityDamageModify(event);
    }


    /** OTHERS **/

    /**
     * This function manages the function onEntityDamage
     * @param event
     */
    private void entityDamageModify(final EntityDamageEvent event){
        final Entity entity = event.getEntity();
        final Location location = entity.getLocation();

        if (isPlayer(entity) && isWorldSuitable(location))
            cancelDamages(event);
    }


    /**
     * This function cancel fire damages
     * @param event
     */
    private void cancelDamages(final EntityDamageEvent event){
        final EntityDamageEvent.DamageCause cause = event.getCause();

        switch (cause){
            case FIRE:
            case LAVA:
                event.setCancelled(true);
                break;
        }
    }


    /**
     * This function return If world is suitable
     * @param location
     * @return
     */
    private boolean isWorldSuitable(final Location location){
        return location.getWorld().getName().equalsIgnoreCase("Normal_tmp");
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
