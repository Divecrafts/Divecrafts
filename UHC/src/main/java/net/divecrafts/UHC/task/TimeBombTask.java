package net.divecrafts.UHC.task;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Location;
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

public class TimeBombTask extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    private int time = 30;
    private final Location location;

    public final int PID = getTaskId();

    public TimeBombTask(Location location){
        this.location = location;
    }


    /** REST **/

    @Override
    public void run() {
        if (time != 0) time--;
        else kill();
    }


    @Override
    public void kill(){
        Api.createExplossion(location);
        cancel();
    }


    @Override
    public void addTaskToList(){
        Main.addTask(this);
    }


}
