package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Mineraless;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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

class DiamondLess extends Mineraless {


    /** SMALL CONSTRUCTORS **/

    DiamondLess(){
        super(Material.DIAMOND_ORE, new ItemStack(Material.DIAMOND, 1));
    }


    /** REST **/

    @Override
    public void onBlockBreakCancel(final BlockBreakEvent event) {
        final Player player = event.getPlayer();

        player.sendMessage(Api.translator(
                Api.getConfigManager().getErrDiamondLess()
        ));

        event.setCancelled(true);
    }


}
