package net.divecrafts.UHC.task;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by alejandrorioscalera
 * On 21/2/18
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

public abstract class AirDropsTask extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    private int time = Api.getConfigManager().getAirDropTime();


    /** REST **/

    /**
     * This function represents an action while task end
     */
    public abstract void onEnd();


    @Override
    public void run(){
        if (time != 0) time--;
        else restart();

        if (Api.getState() == State.ENDING)
            kill();
    }


    @Override
    public void kill(){
        cancel();
    }


    @Override
    public void addTaskToList(){
        Main.addTask(this);
    }


    /** OTHERS **/

    /**
     * This function restart the dropTask
     */
    private void restart(){
        onEnd();
        time = Api.getConfigManager().getAirDropTime();
    }


}
