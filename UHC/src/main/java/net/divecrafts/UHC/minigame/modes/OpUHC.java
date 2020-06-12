package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

class OpUHC implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final List<Material> blackList;

    OpUHC(Main plugin){
        Api.PLUGIN_MANAGER.registerEvents(this, plugin);
        blackList = new ArrayList<>(Arrays.asList(
                Material.DIAMOND_ORE, Material.GOLD_ORE, Material.IRON_ORE, Material.EMERALD_ORE, Material.COAL_ORE,
                Material.GLOWING_REDSTONE_ORE, Material.LAPIS_ORE, Material.QUARTZ_ORE, Material.REDSTONE_ORE
        ));
    }


    /** REST **/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        blockFilter(event);
    }


    /** OTHERS **/

    /**
     * This function check if block is in blackList and multiply the drop
     * @param event
     */
    private void blockFilter(final BlockBreakEvent event){
        final Material material = event.getBlock().getType();
        if (!blackList.contains(material)) blockMultiplier(event);
    }


    /**
     * This function multiply the items
     * @param event
     */
    private void blockMultiplier(final BlockBreakEvent event){
        final Block block = event.getBlock();
        final Collection<? extends ItemStack> drops = block.getDrops();
        for (ItemStack i : drops) i.setAmount(i.getAmount() * 3);
    }


}
