package net.divecrafts.UHC.minigame.modes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

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

class ColdWeapons implements Listener {


    /** REST **/

    @EventHandler
    public void onEnchantEvent(EnchantItemEvent event){
        itemClean(event);
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        playerDeather(event);
    }


    /** OTHERS **/

    /**
     * This function manage the function onEnchantEvent
     * @param event
     */
    private void itemClean(final EnchantItemEvent event){
        final ItemStack item = event.getItem();
        final ItemMeta meta = enchantsRemover(item.getItemMeta());

        for (Map.Entry<Enchantment, Integer> enchantment: event.getEnchantsToAdd().entrySet()) {
            final Enchantment enchant = enchantment.getKey();

            if (enchant == Enchantment.FIRE_ASPECT || enchant == Enchantment.ARROW_FIRE)
                event.getEnchantsToAdd().remove(enchantment.getKey());
        }

        item.setItemMeta(meta);
    }


    /**
     * This function manage the function onPlayerDeath
     * This function set cobwebs
     * @param event
     */
    private void playerDeather(final PlayerDeathEvent event){
        final Player player = event.getEntity();
        final Location location = permutateLocation(player.getLocation());
        final Location[] locations = permutateLocations(location);

        for (Location loc : locations) loc.getBlock().setType(Material.WEB);
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
     * This function permutate the locations and return array of locations
     * @param location
     * @return
     */
    private Location[] permutateLocations(final Location location){
        final double x = location.getX(),
                     y = location.getY(),
                     z = location.getZ();
        final float yaw = location.getYaw(),
                    pitch = location.getPitch();
        final World world = location.getWorld();

        return new Location[]{
                new Location(world, x + 1, y, z, yaw, pitch),
                new Location(world, x - 1, y, z, yaw, pitch),
                new Location(world, x, y, z + 1, yaw, pitch),
                new Location(world, x, y, z - 1, yaw, pitch)
        };
    }


    /**
     * This function clear the specific enchants
     * @param meta
     * @return
     */
    private ItemMeta enchantsRemover(final ItemMeta meta){
        if (meta.hasEnchant(Enchantment.ARROW_FIRE))
            meta.removeEnchant(Enchantment.ARROW_FIRE);
        if (meta.hasEnchant(Enchantment.FIRE_ASPECT))
            meta.removeEnchant(Enchantment.FIRE_ASPECT);

        return meta;
    }


    /**
     * This function check if entity is player
     * @param entity
     * @return
     */
    private boolean isPlayer(final Entity entity){
        return entity.getType() == EntityType.PLAYER;
    }


}
