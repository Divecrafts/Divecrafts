package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Mineraless;

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

    GoldLess(Main plugin){
        super(plugin, Material.GOLD_ORE, new ItemStack(Material.GOLD_INGOT, 16));
    }


    /** REST **/

    @Override
    public void onBlockBreakCancel(final BlockBreakEvent event) {
        final Player player = event.getPlayer();

        player.sendMessage(Api.translator(
                Api.getConfigManager().getErrGoldLess()
        ));

        event.setCancelled(true);
    }


    @EventHandler
    public void onPlayerDeaz(PlayerDeathEvent event){
        playerDropper(event);
    }


    /** OTHERS **/

    /**
     * This function manage function onPlayerDeaz
     * @param event
     */
    private void playerDropper(final PlayerDeathEvent event){
        final Player player = event.getEntity();
        final ItemStack skull = Api.getPlayerSkull(
                player.getName(), player.getDisplayName()
        );

        event.getDrops().add(skull);
    }


}
