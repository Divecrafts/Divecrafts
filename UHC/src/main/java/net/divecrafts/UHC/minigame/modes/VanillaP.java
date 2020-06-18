package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by alejandrorioscalera
 * On 13/2/18
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

public class VanillaP implements Listener {


    /** SMALL CONSTRUCTORS **/

    private int counterApple = 0,
                counterGravel = 0;

    VanillaP(Main plugin){
        Api.PLUGIN_MANAGER.registerEvents(this, plugin);
    }


    /** REST **/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        appleRate(event);
        gravelRate(event);
    }


    /** OTHERS **/

    /**
     * This function manage the chances of the apple drop rate
     * @param event
     */
    private void appleRate(final BlockBreakEvent event){
        final Block block = event.getBlock();
        final int max = Api.getRandom(4, 0);

        if (block.getType() == Material.LEAVES){
            counterApple++;
            if (counterApple == max) appleCounterClear(event);
        }
    }


    /**
     * This function manage the chances of the gravel and flint drop rate
     * @param event
     */
    private void gravelRate(final BlockBreakEvent event){
        final Block block = event.getBlock();
        final int max = Api.getRandom(4, 2);

        if (block.getType() == Material.GRAVEL){
            counterGravel++;
            if (counterGravel == max) gravelCounterClear(event);
        }
    }


    /**
     * This function restore the counter variable for the chances of the apple drop rate
     * @param event
     */
    private void appleCounterClear(final BlockBreakEvent event){
        final Block block = event.getBlock();
        final World world = block.getWorld();

        counterApple = 0;
        world.dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE, 1));
    }


    /**
     * This function restore the counter variable for the chances of the gravel and flint drop rate
     * @param event
     */
    private void gravelCounterClear(final BlockBreakEvent event){
        final Block block = event.getBlock();
        final World world = block.getWorld();

        counterGravel = 0;

        world.dropItemNaturally(block.getLocation(), new ItemStack(Material.FLINT, 1));
        world.dropItemNaturally(block.getLocation(), new ItemStack(Material.GRAVEL,
                Api.getRandom(1, 0)
        ));
    }


}
