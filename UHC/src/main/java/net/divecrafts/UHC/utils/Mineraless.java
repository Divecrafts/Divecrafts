package net.divecrafts.UHC.utils;

import net.divecrafts.UHC.Main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

public abstract class Mineraless implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final Material target;
    private final ItemStack mineral;

    public Mineraless(Main plugin, Material target, ItemStack mineral){
        Api.PLUGIN_MANAGER.registerEvents(this, plugin);
        this.target = target;
        this.mineral = mineral;
    }


    /** REST **/

    public abstract void onBlockBreakCancel(final BlockBreakEvent event);

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        mineralPreventer(event);
    }


    @EventHandler
    public void onExplossion(EntityExplodeEvent event){
        mineralPreventer(event);
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        mineralPutter(event);
    }


    /** OTHERS **/

    /**
     * This function manage function onBlockBreak
     * @param event
     */
    private void mineralPreventer(final BlockBreakEvent event){
        final Material material = event.getBlock().getType();

        if (material == target) cancelEvent(event);
    }


    /**
     * This function manage function onExplosion
     * @param event
     */
    private void mineralPreventer(final EntityExplodeEvent event){
        final List<Block> blocks = event.blockList();
        blocks.removeIf(block -> block.getType() == target);
    }


    /**
     * This function manage function onPlayerDeath
     * @param event
     */
    private void mineralPutter(final PlayerDeathEvent event){
        event.getDrops().add(mineral);
    }


    /**
     * This function cancel the event
     * @param event
     */
    private void cancelEvent(final Object event){
        onBlockBreakCancel((BlockBreakEvent) event);
    }


}
