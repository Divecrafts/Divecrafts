package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by alejandrorioscalera
 * On 20/2/18
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

class BloodDiamonds implements Listener {


    /** REST **/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        diamondBreaker(event);
    }


    /** OTHERS **/

    /**
     * This function manage the function onBlockBreak
     * @param event
     */
    private void diamondBreaker(final BlockBreakEvent event){
        final Player player = event.getPlayer();
        final Material block = event.getBlock().getType();
        final double health = player.getHealth();

        if (block == Material.DIAMOND_ORE)
            player.setHealth(health - 1.0D);
    }


}
