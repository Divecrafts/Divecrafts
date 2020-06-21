package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

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

class Limitations implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final HashMap<Player, int[]> map = new HashMap<>();
    private enum WhiteList {IRON, GOLD, DIAMOND}


    /** REST **/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        blockLimiter(event);
    }


    /** OTHERS **/

    /**
     * This function manage the function onBlockBreak
     * @param event
     */
    private void blockLimiter(final BlockBreakEvent event){
       final Player player = event.getPlayer();
       final Material material = event.getBlock().getType();
       final int maxIron = 64, maxGold = 32, maxDiamond = 16;
       final int[] vals = initMap(player);

       switch (material){
           case IRON_ORE:
               if (vals[0] == maxIron) cancelEvent(event, WhiteList.IRON);
               else updateMap(player, new int[]{
                       vals[0] + 1,
                       vals[1],
                       vals[2]
               });
               break;
           case GOLD_ORE:
               if (vals[1] == maxGold) cancelEvent(event, WhiteList.GOLD);
               else updateMap(player, new int[]{
                       vals[0],
                       vals[1] + 1,
                       vals[2]
               });
               break;
           case DIAMOND_ORE:
               if (vals[2] == maxDiamond) cancelEvent(event, WhiteList.DIAMOND);
               else updateMap(player, new int[]{
                       vals[0],
                       vals[1],
                       vals[2] + 1
               });
               break;
       }
    }


    /**
     * This function init the map and return the value
     * @param player
     */
    private int[] initMap(final Player player){
        if (!map.containsKey(player)) map.put(player, new int[]{0, 0, 0});
        return map.get(player);
    }


    /**
     * This function update the map
     * @param player
     * @param value
     */
    private void updateMap(final Player player, final int[] value){
        map.remove(player);
        map.put(player, value);
    }


    /**
     * This function cancel the event
     * @param event
     * @param material
     */
    private void cancelEvent(final BlockBreakEvent event, final WhiteList material){
        final Player player = event.getPlayer();
        final String message = Api.getConfigManager().getErrLimitations().replace(
                "{BLOCK}", material.toString()
        );

        player.sendMessage(Api.translator(message));
        event.setCancelled(true);
    }


}
