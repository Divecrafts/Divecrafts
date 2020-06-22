package net.divecrafts.UHC.task;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import net.divecrafts.UHC.minigame.Game;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.utils.Api;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

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

public class GameCountDown extends BukkitRunnable implements ITask {


    /** SMALL CONSTRUCTORS **/

    public static boolean isRunning = false;
    public static boolean isForced = false;

    public static int PID;

    private int time;

    public GameCountDown(Main instance, boolean isForced){
        isRunning = true;
        time = Api.getConfigManager().getTimeCountDown();

        GameCountDown.isForced = isForced;
        Api.setGame(new Game(instance, Api.getArena()));
    }


    /** REST **/

    @Override
    public void run() {
        if (Api.getState() == State.RUNNING) {
            super.cancel();
            return;
        }

        if (time != 0) decrementTime();
        else {
            startGame();
            kill();
        }

        if (Api.getOnlinePlayers() == null || Api.getOnline() == 0 || (!isForced && Api.getOnline() == 1)){
            kill();
            time = Api.getConfigManager().getTimeCountDown(); //Reset task
        }
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


    /** OTHERS **/

    /**
     * This function decrement the time and then print this
     */
    private void decrementTime(){
        if (time == Api.getConfigManager().getTimeCountDown() || time <= 5)
            Bukkit.getOnlinePlayers().forEach(p -> {
                final SUser user = SServer.getUser(p);
                p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.countdown").replace("%tiempo%", String.valueOf(time)));
            });

        time--;
    }


    /**
     * This function manage the order start game
     */
    private void startGame(){
        Bukkit.getOnlinePlayers().forEach(p -> {
            final SUser user = SServer.getUser(p);
            p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.started"));
        });
        Api.getGame().gameStart();
    }


}
