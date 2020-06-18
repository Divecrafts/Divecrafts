package net.divecrafts.UHC.task;

import net.divecrafts.UHC.Main;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

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

public class NoCleanTask extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    private int time = 30;
    private final ArrayList<Player> invincibles;
    private final Player killer;

    public final int PID = getTaskId();

    public NoCleanTask(ArrayList<Player> invincibles, Player killer){
        this.invincibles = invincibles;
        this.killer = killer;
    }


    /** REST **/

    @Override
    public void run() {
        if (time != 0) time--;
        else kill();

        if (!invincibles.contains(killer)) kill();
    }


    @Override
    public void kill(){
        if (invincibles.contains(killer)) invincibles.remove(killer);
        cancel();
    }


    @Override
    public void addTaskToList(){
        Main.addTask(this);
    }


}
