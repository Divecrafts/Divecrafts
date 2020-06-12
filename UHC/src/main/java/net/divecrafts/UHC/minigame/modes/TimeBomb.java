package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.task.TimeBombTask;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

class TimeBomb implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;

    TimeBomb(Main instance){
        plugin = instance;
        Api.PLUGIN_MANAGER.registerEvents(this, plugin);
    }


    /** REST **/

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        inventoryExploder(event);
    }


    /** OTHERS **/

    /**
     * This function manage action of the onPlayerDeath
     * @param event
     */
    private void inventoryExploder(final PlayerDeathEvent event){
        final Player player = event.getEntity().getPlayer();
        final Location location = permutateLocation(player.getLocation());

        passToChest(location, event.getDrops());
        event.getDrops().clear();

        runTask(location);
    }


    /**
     * This function manage task of this process
     * @param location
     * @return
     */
    private TimeBombTask runTask(final Location location){
        final TimeBombTask task = new TimeBombTask(location);
        task.runTaskTimer(plugin, 1L, 20L);
        return task;
    }


    /**
     * This function permutate the Location values for get a block
     * @param location
     * @return
     */
    private Location permutateLocation(Location location){
        while (location.getBlock() == null || location.getBlock().getType() == Material.AIR){
            final double x = location.getX(),
                    y = location.getY(),
                    z = location.getZ();
            final float yaw = location.getYaw(),
                    pitch = location.getPitch();
            final World world = location.getWorld();

            location = new Location(world, x, y - 1, z, yaw, pitch);
        }
        return location;
    }


    /**
     * This function move the drop to chest inventory
     * @param location
     * @param drops
     * @return
     */
    private Chest passToChest(final Location location, final List<ItemStack> drops){
        location.getBlock().setType(Material.CHEST);

        final Chest chest = (Chest) location.getBlock();
        for (ItemStack item : drops)
            chest.getInventory().addItem(item);

        return chest;
    }


}
