package net.divecrafts.UHC.task;

import net.divecrafts.UHC.minigame.arena.worlds.Border;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Scoreboard;

import org.bukkit.scheduler.BukkitRunnable;

import net.divecrafts.UHC.Main;

/**
 * Created by alejandrorioscalera
 * On 2019-07-08
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
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2019
 */

public class BorderTask extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    public static boolean isRunning = false;


    /** REST **/

    @Override
    public void run() {
        final Border border = Api.getArena().getBorder();

        if (border.getSize() != 10.0D){
            border.setSize(border.getSize() - 10.0D);
            Scoreboard.updateScoreboard("border", String.valueOf(Math.round(border.getSize())));
        }
        else kill();
    }


    @Override
    public void kill(){
        cancel();
        isRunning = false;
    }


    @Override
    public void addTaskToList(){
        Main.addTask(this);
    }
}
