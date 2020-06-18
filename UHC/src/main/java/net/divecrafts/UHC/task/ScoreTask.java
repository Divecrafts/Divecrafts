package net.divecrafts.UHC.task;

import net.divecrafts.UHC.utils.Api;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import net.divecrafts.UHC.Main;

/**
 * Created by alejandrorioscalera
 * On 16/1/18
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

public class ScoreTask extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    private static int seconds = 0, minutes = 0;


    /** REST **/

    @Override
    public void run() {
        seconds++;

        if (seconds == 59){
            minutes++;
            seconds = 0;
        }

        for (Player player : Api.getOnlinePlayers()){
            final Scoreboard scoreboard = player.getScoreboard();
            scoreboard.getTeam("time").setSuffix(getTime());
            player.setScoreboard(scoreboard);
        }

        if (Api.getOnlinePlayers() == null || Api.getOnline() == 0 || (!GameCountDown.isForced && Api.getOnline() == 1))
            kill();
    }


    /**
     * This function return a time
     * @return
     */
    public static String getTime(){
        return timeFormat(minutes) + ":" + timeFormat(seconds);
    }

    private static String timeFormat(int time){
        final String str = String.valueOf(time);
        return str.length() == 1 ? "0" + str : str;
    }


    @Override
    public void kill(){
        seconds = 0;
        minutes = 0;

        Api.getGame().gameStop();
        cancel();
    }


    @Override
    public void addTaskToList(){
        Main.addTask(this);
    }


}
