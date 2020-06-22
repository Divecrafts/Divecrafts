package net.divecrafts.UHC.minigame.modes;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

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

class Barebones implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final PlayerTeleportEvent.TeleportCause NETHER = PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
    private final List<Material> blackListDrops = Arrays.asList(Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GLOWING_REDSTONE_ORE, Material.GOLD_ORE, Material.LAPIS_ORE, Material.QUARTZ_ORE, Material.REDSTONE_ORE);
    private final ItemStack[] drops =  new ItemStack[]{new ItemStack(Material.DIAMOND, 1), new ItemStack(Material.GOLDEN_APPLE, 1), new ItemStack(Material.ARROW, 32), new ItemStack(Material.STRING, 2)};
    private final List<Material> blackListCraft = Arrays.asList(Material.ENCHANTMENT_TABLE, Material.ANVIL, Material.GOLDEN_APPLE, Material.SKULL, Material.SKULL_ITEM);


    /** REST **/

    @EventHandler
    public void onPortalCreate(PlayerPortalEvent event){
        portalLocker(event);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        ironDrop(event);
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        playerDropsAdd(event);
    }


    @EventHandler
    public void onCraft(CraftItemEvent event){
        craftsLocker(event);
    }


    /** OTHERS **/

    /**
     * This function manage onCraft
     * @param event
     */
    private void craftsLocker(final CraftItemEvent event){
        if (blackListCraft.contains(event.getRecipe().getResult().getType()))
            lockEventCraft(event);
    }


    /**
     * This function lock a event craft when result is item in blackList
     * @param event
     */
    private void lockEventCraft(final CraftItemEvent event){
        final SUser user = SServer.getUser((Player) event.getWhoClicked());
        user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.barebones").replace("%target%", event.getRecipe().getResult().getItemMeta().getDisplayName()));
        event.setCancelled(true);
    }


    /**
     * This function manage onPlayerDeath
     * @param event
     */
    private void playerDropsAdd(final PlayerDeathEvent event){
        final Location location = event.getEntity().getLocation();
        for (ItemStack drop : drops)
            location.getWorld().dropItemNaturally(location, drop);
    }


    /**
     * This function manage onPortalCreate
     * This function lock the nether portals
     * @param event
     */
    private void portalLocker(final PlayerPortalEvent event){
        if (event.getCause() == NETHER)
            event.setCancelled(true);
    }


    /**
     * This function manage on BlockBreak
     * @param event
     */
    private void ironDrop(final BlockBreakEvent event){
        if (blackListDrops.contains(event.getBlock().getType()))
            addDropToBlock(event);
    }


    /**
     * This function drop item of block
     * @param event
     */
    private void addDropToBlock(final BlockBreakEvent event){
        final Location location = event.getBlock().getLocation();
        final World world = location.getWorld();

        world.dropItemNaturally(location, new ItemStack(Material.IRON_INGOT, 1));
    }


}
