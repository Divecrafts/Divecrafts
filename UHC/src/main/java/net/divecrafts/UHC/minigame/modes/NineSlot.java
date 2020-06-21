package net.divecrafts.UHC.minigame.modes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alejandrorioscalera
 * On 27/2/18
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

class NineSlot implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final List<Integer> blackList;

    NineSlot(){
        blackList = new ArrayList<>(Arrays.asList(
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 36, 37, 38, 39
        ));
    }


    /** REST **/

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        inventoryLimiter(event);
    }


    /** OTHERS **/

    /**
     * This function manage the onInventoryClick
     * @param event
     */
    private void inventoryLimiter(final InventoryClickEvent event){
        if (blackList.contains(
                event.getSlot()
        )) event.setCancelled(true);
    }


}
