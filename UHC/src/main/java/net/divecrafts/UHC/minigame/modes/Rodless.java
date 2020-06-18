package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

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

class Rodless implements Listener {


    /** SMALL CONSTRUCTORS **/

    Rodless(Main plugin){
        Api.PLUGIN_MANAGER.registerEvents(this, plugin);
    }


    /** REST **/

    @EventHandler
    public void onCraftItem(CraftItemEvent event){
        itemPreventer(event);
    }


    /** OTHERS **/

    /**
     * This function manage the function onCraftItem
     * @param event
     */
    private void itemPreventer(final CraftItemEvent event){
        final Material target = event.getRecipe().getResult().getType();
        final Player player = (Player) event.getWhoClicked();

        switch (target){
            case FISHING_ROD:
                sendMessage(player);
                event.setCancelled(true);
                break;
        }
    }


    /**
     * This function send error to specific Player
     * @param player
     */
    private void sendMessage(final Player player){
        player.sendMessage(Api.translator(
                Api.getConfigManager().getErrRodless()
        ));
    }


}
