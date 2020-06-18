package net.divecrafts.UHC;

import net.divecrafts.UHC.commands.GameCMD;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.task.ITask;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.ConfigManager;
import net.divecrafts.UHC.utils.Data;
import net.divecrafts.UHC.utils.clonadoc.Getter;
import net.divecrafts.UHC.utils.clonadoc.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import net.divecrafts.UHC.listeners.GameEvents;
import net.divecrafts.UHC.listeners.LobbyEvents;

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

public class Main extends JavaPlugin {


    /** SMALL CONSTRUCTORS **/

    public static Main instance;
    private static List<ITask> taskList = new ArrayList<>();


    /** REST **/

    @Override
    public void onLoad(){
        Bukkit.getConsoleSender().sendMessage("§bUHC> §fPlugin cargado");
    }


    @Override
    public void onEnable(){
        try {
            instance = this;

            Config();
            Events();
            Commands();

            Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);

            Api.setState(State.LOBBY); //Set state lobby on enable
            Api.sendConsole(Api.translator("&a" + Api.PREFIX + "&fPlugin activado"));

            startMemo();
        }
        catch (Exception ex){
            ex.printStackTrace();
            Api.sendConsole(Api.translator("&4" + Api.PREFIX + "&cSeveral error"));
            onDisable();
        }
    }


    @Override
    public void onDisable(){
        endMemo();

        try {
            Api.PLUGIN_MANAGER.disablePlugin(instance);
            Api.sendConsole(Api.translator("&8" + Api.PREFIX + "&eKilling process plugin..."));
        }
        catch (Exception ex){
            if (Api.getConfigManager().getDebugMode()) ex.printStackTrace();
            Api.PLUGIN_MANAGER.disablePlugin(this);
            Api.sendConsole(Api.translator("&4" + Api.PREFIX + "&cKilling process plugin with errors!"));
        }

        Api.sendConsole(Api.translator("&c" + Api.PREFIX + "&fPlugin desactivado"));
        instance = null;
    }


    /** OTHERS **/

    /**
     * This function initialize events
     */
    private void Events(){
        Api.PLUGIN_MANAGER.registerEvents(new LobbyEvents(instance), instance);
        Api.PLUGIN_MANAGER.registerEvents(new GameEvents(), instance);

        Api.sendConsole(Api.translator("&9" + Api.PREFIX + "&fEventos cargados"));
    }


    /**
     * This function initialize commands
     */
    private void Commands(){
        getCommand("uhc").setExecutor(new GameCMD());
    }


    /**
     * This function initialize config
     */
    private void Config(){
        saveDefaultConfig();

        new Data();
        new ConfigManager();
    }


    /**
     * Save in memory the arenas and save the instance manager
     */
    private void startMemo(){
        Api.setArena(new Arena());
    }


    /**
     * Remove from memory the arenas
     */
    private void endMemo(){
        if (Api.getArena() != null)
            Api.getArena().remove();
    }


    /**
     * This function sets the mode of the game
     */
    private void loadMode(){
        //Api.getConfigManager().getMode
    }


    /** SETTERS **/

    @Setter
    public static void addTask(ITask task){
        taskList.add(task);
    }


    /** GETTERS **/

    @Getter
    public static List<ITask> getTaskList(){
        return taskList;
    }


}
