package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

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

class Horseless implements Listener {


    /** SMALL CONSTRUCTORS **/

    Horseless(){
        clearHorses();
    }


    /** REST **/

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event){
        horseCanceller(event);
    }


    /** OTHERS **/

    /**
     * This function prevent spawn horse
     * @param event
     */
    private void horseCanceller(final CreatureSpawnEvent event){
        final Entity entity = event.getEntity();

        if (entity.getType() == EntityType.HORSE)
            event.setCancelled(true);
    }


    /**
     * This function remove all horses from normal_tmp world
     */
    private void clearHorses(){
        final World world = Bukkit.getWorld("Normal_tmp");
        final List<LivingEntity> entities = world.getLivingEntities();

        for (LivingEntity entity : entities)
            if (entity.getType() == EntityType.HORSE)
                entity.remove();
    }


}
