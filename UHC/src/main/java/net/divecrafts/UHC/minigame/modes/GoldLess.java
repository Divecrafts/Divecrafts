package net.divecrafts.UHC.minigame.modes;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;

import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Mineraless;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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

class GoldLess extends Mineraless {


    /** SMALL CONSTRUCTORS **/

    GoldLess(){
        super(Material.GOLD_ORE, new ItemStack(Material.GOLD_INGOT, 16));
    }


    /** REST **/

    @Override
    public void onBlockBreakCancel(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final SUser user = SServer.getUser(player);

        player.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.goldless"));
        event.setCancelled(true);
    }


    @EventHandler
    @Override
    public void onPlayerDeath(PlayerDeathEvent event){
        super.onPlayerDeath(event);
        playerDropper(event);
    }


    /** OTHERS **/

    /**
     * This function manage function onPlayerDeaz
     * @param event
     */
    private void playerDropper(final PlayerDeathEvent event){
        final Player player = event.getEntity();
        final ItemStack skull = Api.getPlayerSkull(player.getName(), player.getDisplayName());
        final Location location = event.getEntity().getLocation();

        location.getWorld().dropItemNaturally(location, skull);
    }


}
