package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.commands.GodCmd;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex
 * On 30/04/2020
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

public class GodHandler implements Listener {

    private final List<World> allowedWorlds = Arrays.asList(Bukkit.getWorld("world"), Bukkit.getWorld("world_nether"));

    @EventHandler
    public void onPlayerBeHitted(EntityDamageEvent event){
        final Entity entity = event.getEntity();

        if (entity instanceof Player){
            if (GodCmd.players.contains(entity.getName()) && allowedWorlds.contains(entity.getWorld())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHasHunger(FoodLevelChangeEvent event){
        final Player player = (Player) event.getEntity();

        if (GodCmd.players.contains(player.getName()) && allowedWorlds.contains(player.getWorld())){
            event.setCancelled(true);
        }
    }
}
