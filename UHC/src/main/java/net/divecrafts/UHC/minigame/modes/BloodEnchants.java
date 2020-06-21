package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

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

class BloodEnchants implements Listener {


    /** REST **/

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event){
        itemEnchantLevel(event);
    }


    /** OTHERS **/

    /**
     * This function manage function onEnchantItem
     * @param event
     */
    private void itemEnchantLevel(final EnchantItemEvent event){
        final Player player = event.getEnchanter();
        final double health = player.getHealth();
        final int levels = event.getExpLevelCost();

        player.setHealth(health - (double) levels);
    }


}
