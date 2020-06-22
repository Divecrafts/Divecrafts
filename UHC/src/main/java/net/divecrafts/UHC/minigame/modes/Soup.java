package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.task.SoupTask;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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

class Soup implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin = Main.instance;


    /** REST **/

    @EventHandler
    public void onEatSoup(PlayerInteractEvent event){
        playerFeed(event);
    }


    /** OTHERS **/

    /**
     * This function manage onEatSoup
     * @param event
     */
    private void playerFeed(final PlayerInteractEvent event){
        final Player player = event.getPlayer();
        final Action action = event.getAction();

        if ((action == Action.RIGHT_CLICK_BLOCK ||
                action == Action.RIGHT_CLICK_AIR) &&
                isFood(player))
            runTask(player);
    }


    /**
     * This function manage the task of this process
     * @param player
     * @return
     */
    private SoupTask runTask(final Player player){
        final SoupTask task = new SoupTask(player);
        task.runTaskLater(plugin, 100L);
        return task;
    }


    /**
     * This function check if itemStack is SOUP
     * @param player
     * @return
     */
    private boolean isFood(final Player player){
        final ItemStack stack = player.getItemInHand();
        return stack != null && stack.getType() == Material.MUSHROOM_SOUP;
    }


}
