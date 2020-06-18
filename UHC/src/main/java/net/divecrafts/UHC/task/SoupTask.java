package net.divecrafts.UHC.task;

import net.divecrafts.UHC.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

public class SoupTask extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    private Player player;

    public final int PID = getTaskId();

    public SoupTask(Player player){
        this.player = player;
    }


    /** REST **/

    @Override
    public void run() {
        if (!isFood()) kill();
    }


    @Override
    public void kill() {
        final double health = player.getHealth();

        player.setHealth(health + 2.0F);
        cancel();
    }


    @Override
    public void addTaskToList(){
        Main.addTask(this);
    }


    /** OTHERS **/

    /**
     * This function check if itemStack is SOUP
     * @return
     */
    private boolean isFood(){
        boolean result = false;
        final ItemStack stack = player.getItemInHand();

        if (stack != null){
            final Material item = stack.getType();
            switch (item){
                case MUSHROOM_SOUP:
                    result = true;
                    break;
            }
        }

        return result;
    }


}
