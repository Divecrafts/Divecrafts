package net.divecrafts.UHC.minigame;

import io.clonalejandro.DivecraftsCore.api.SServer;
import net.divecrafts.UHC.minigame.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.task.BorderTask;
import net.divecrafts.UHC.task.ScoreTask;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Scoreboard;
import org.bukkit.event.Listener;

import java.util.HashMap;

/**
 * Created by alejandrorioscalera
 * On 17/1/18
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

public final class Game {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;
    private final Arena arena;
    public static HashMap<Player, Location> playerSpawn = new HashMap<>();

    public static Game game;

    public Game(Main instance, Arena arena){
        this.plugin = instance;
        this.arena = arena;

        Game.game = this;
    }


    /** REST **/

    public synchronized void gameStart(){
        Api.setState(State.RUNNING);
        Api.ALIVE_PLAYERS.addAll(Api.getOnlinePlayers());

        playerSpawn.forEach((player, loc) -> {
            player.getInventory().clear();
            Scoreboard.gameScoreboard(player);
            SServer.getUser(player).getUserData().addPlay(SServer.GameID.UHC);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(loc);
        });

        Api.SELECTED_MODES.forEach(mode -> {
            Bukkit.broadcastMessage(mode.toString());
            Api.PLUGIN_MANAGER.registerEvents((Listener) mode.getClazz(), Main.instance);
        });

        new ScoreTask().runTaskTimer(plugin, 1L, 20L);
        new BorderTask().runTaskTimer(Main.instance, 1L, 60L * 20L);//This be executed per 60s
    }


    public synchronized void gameStop(){
        Api.setState(State.ENDING);

        Bukkit.broadcastMessage(Api.translator("&a&lUHC> &fThe game is ending"));
        Bukkit.getOnlinePlayers().forEach(p -> SServer.getUser(p).sendToServer("lobby"));
        Bukkit.getScheduler().runTaskLater(Main.instance, Bukkit::shutdown, 10L * 20L);
    }


}
