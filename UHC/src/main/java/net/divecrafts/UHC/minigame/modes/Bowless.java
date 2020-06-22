package net.divecrafts.UHC.minigame.modes;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;

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

class Bowless implements Listener {


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
        final SUser user = SServer.getUser(player);

        switch (target){
            case BOW:
            case ARROW:
                player.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.bowless"));
                event.setCancelled(true);
                break;
        }
    }


}
