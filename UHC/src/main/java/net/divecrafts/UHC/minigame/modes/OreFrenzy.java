package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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

class OreFrenzy implements Listener {


    /** REST **/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        blockDropper(event);
    }


    /** OTHERS **/

    /**
     * This function manage the onBlockBreak
     * @param event
     */
    private void blockDropper(final BlockBreakEvent event){
        blockChecker(event);
    }


    /**
     * This function check if block is in blacklist and drop this value
     * @param event
     */
    private void blockChecker(final BlockBreakEvent event){
        final Material material = event.getBlock().getType();

        switch (material){
            case LAPIS_ORE:
                materialDropper(event, new ItemStack(Material.POTION, 1, (short) 8197));
                break;
            case EMERALD_ORE:
                materialDropper(event, new ItemStack(Material.ARROW, 32));
                break;
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
                materialDropper(event, new ItemStack(Material.BOOK, 1));
                break;
            case DIAMOND_ORE:
                materialDropper(event,
                        new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.EXP_BOTTLE, 4)
                );
                break;
            case QUARTZ_ORE:
                materialDropper(event, new ItemStack(Material.TNT, 1));
                break;
        }
    }


    /**
     * This function drop an item in block location
     * @param event
     * @param item
     */
    private void materialDropper(final BlockBreakEvent event, final ItemStack... item){
        final Location location = event.getBlock().getLocation();
        for (ItemStack i : item) location.getWorld().dropItemNaturally(location, i);
    }


}
