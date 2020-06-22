package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.utils.Api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
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

class CutClean implements Listener {


    /** REST **/

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        blockBreaker(event);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        entityGraver(event);
    }


    /** OTHERS **/

    /**
     * This function manage action of the onBlockBreak
     * @param event
     */
    private void blockBreaker(final BlockBreakEvent event){
        final Material block = event.getBlock().getType();

        switch (block){
            case IRON_ORE:
                modifyBlockDrops(event, new ItemStack[]{
                        new ItemStack(Material.IRON_INGOT, 1)
                });
                break;
            case GOLD_ORE:
                modifyBlockDrops(event, new ItemStack[]{
                        new ItemStack(Material.GOLD_INGOT, 1)
                });
                break;
            case GRAVEL:
                modifyBlockDrops(event, new ItemStack[]{
                        new ItemStack(Material.GRAVEL, Api.getRandom(1, 0)),
                        new ItemStack(Material.FLINT, Api.getRandom(2, 1))
                });
                break;
        }
    }


    /**
     * This function manage action of the onEntityDeath
     * @param event
     */
    private void entityGraver(final EntityDeathEvent event){
        switch (event.getEntity().getType()){
            case COW:
                modifyEntityDrops(event, new ItemStack[]{
                        new ItemStack(Material.COOKED_BEEF, Api.getRandom(3, 1)),
                        new ItemStack(Material.LEATHER, Api.getRandom(4, 2))
                });
                break;
            case CHICKEN:
                modifyEntityDrops(event, new ItemStack[]{
                        new ItemStack(Material.COOKED_CHICKEN, 1),
                        new ItemStack(Material.FEATHER, Api.getRandom(4, 2))
                });
                break;
            case PIG:
                modifyEntityDrops(event, new ItemStack[]{
                        new ItemStack(Material.GRILLED_PORK, 2)
                });
                break;
        }
    }


    /**
     * This function modify drop of this event
     * @param event
     * @param item
     */
    private void modifyBlockDrops(final BlockBreakEvent event, final ItemStack[] item){
        final Inventory inventory = event.getPlayer().getInventory();

        clearBlockDrop(event);

        if (Api.isInventoryFull(inventory))
            for (ItemStack i : item){
                final Location location = event.getBlock().getLocation();
                location.getWorld().dropItemNaturally(location, i);
            }
        else inventory.addItem(item);
    }


    /**
     * This function modify drop of this event
     * @param event
     * @param item
     */
    private void modifyEntityDrops(final EntityDeathEvent event, final ItemStack[] item){
        final Inventory inventory = event.getEntity().getKiller().getInventory();

        clearEntityDrop(event);

        if (Api.isInventoryFull(inventory))
            for (ItemStack i : item){
                final Location location = event.getEntity().getLocation();
                location.getWorld().dropItemNaturally(location, i);
            }
        else inventory.addItem(item);
    }


    /**
     * This function clear drop of this event
     * @param event
     */
    private void clearBlockDrop(final BlockBreakEvent event){
        event.getBlock().getDrops().clear();
    }


    /**
     * This function clear drop of this event
     * @param event
     */
    private void clearEntityDrop(final EntityDeathEvent event){
        event.getDrops().clear();
    }


}
