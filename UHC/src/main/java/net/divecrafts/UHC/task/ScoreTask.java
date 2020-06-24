package net.divecrafts.UHC.task;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;

import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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

    private static int seconds = 0, minutes = 0, hours = 0;


    /** REST **/

    @Override
    public void run() {
        seconds++;

        if (minutes == 59){
            hours++;
            minutes = 0;
        }

        if (seconds == 59){
            minutes++;
            seconds = 0;
        }

        final int deathMatchOffset = Api.getConfigManager().getDeathMatchTime() - getTimeInMinutes();

        if (deathMatchOffset == 10 || deathMatchOffset == 5 || deathMatchOffset == 1){
            Api.playSound(Api.getArena().getWorld(), Sound.LEVEL_UP, 1F, 1F);
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Languaje.getLangMsg(SServer.getUser(p).getUserData().getLang(), "UHC.deathmatchAlert").replace("%tiempo%", deathMatchOffset + "min")));
        }
        else {
            Api.playSound(Api.getArena().getWorld(), Sound.BLAZE_HIT, 1F, 1F);
            Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Languaje.getLangMsg(SServer.getUser(p).getUserData().getLang(), "UHC.deathmatchStart").replace("%tiempo%", deathMatchOffset + "min")));
            Api.getArena().getBorder().setSize(100.0D);
            Api.ALIVE_PLAYERS.forEach(p -> p.teleport(Arena.genRandomSpawn(63, 100)));
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
        return hours == 0 ? timeFormat(minutes) + ":" + timeFormat(seconds) : timeFormat(hours) + timeFormat(minutes) + ":" + timeFormat(seconds);
    }

    public static int getTimeInMinutes(){
        return hours * 60 + minutes;
    }

    public static int getTimeInSeconds(){
        return getTimeInMinutes() * 60 + seconds;
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
