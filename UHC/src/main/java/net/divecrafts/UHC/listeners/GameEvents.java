package net.divecrafts.UHC.listeners;

import io.clonalejandro.DivecraftsCore.api.SServer;
import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

public class GameEvents implements Listener {


    /** REST **/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (Api.getState() == State.ENDING ||Api.getState() == State.RUNNING)
            whilePlayerCanJoin(e);
    }


    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        if (Api.getState() == State.RUNNING) checkEndGame();
        if (Api.getState() == State.ENDING || Api.getState() == State.RUNNING) whilePlayerCanLeave(e);
    }


    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent e){
        if (Api.getState() == State.RUNNING)
            checkEndGame();
    }


    /** OTHERS **/

    /**
     * This function execute the onPlayerJoin order while state is "Running"
     * @param e
     */
    private void whilePlayerCanJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();

        if (!Api.DESCONECTED.contains(player.getName()))
            player.kickPlayer(Api.translator("&c&lUHC> &fThe game is started :("));
        else {
            Api.ALIVE_PLAYERS.remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            Scoreboard.gameScoreboard(player);
        }
    }


    /**
     * This function execute the onPlayerKickEvent order while state is "Running" or "Ending"
     * @param e
     */
    private void whilePlayerCanLeave(PlayerKickEvent e){
        Api.DESCONECTED.add(e.getPlayer().getName());
        Api.ALIVE_PLAYERS.remove(e.getPlayer());
    }


    /**
     * This function execute the onPlayerQuitEvent order while state is "Running" or "Ending"
     * @param e
     */
    private void whilePlayerCanLeave(PlayerQuitEvent e){
        Api.DESCONECTED.add(e.getPlayer().getName());
        Api.ALIVE_PLAYERS.remove(e.getPlayer());
    }


    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (Api.getState() == State.RUNNING){
            Scoreboard.updateScoreboard(e.getPlayer(), "borderdistance", String.valueOf(Api.getArena().getBorder().resolveDistanceBetweenPlayer(e.getPlayer())));
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        final Player player = e.getEntity();

        if (Api.getState() == State.RUNNING){
            final Player killer = e.getEntity().getKiller();

            player.setGameMode(GameMode.SPECTATOR);
            Api.ALIVE_PLAYERS.remove(player);

            if (killer != null){
                if (!Api.KILLS.containsKey(killer))
                    Api.KILLS.put(killer, 1);
                else {
                    int kills = Api.KILLS.get(killer);
                    Api.KILLS.remove(killer);
                    Api.KILLS.put(killer, kills + 1);
                }

                SServer.getUser(killer).getUserData().addKill(SServer.GameID.UHC);
                //killer.getScoreboard().getTeam("kills").setSuffix(String.valueOf(Api.KILLS.get(killer)));
            }

            SServer.getUser(player).getUserData().addDeath(SServer.GameID.UHC);

            Scoreboard.updateScoreboard("spectators", String.valueOf(Api.getOnline() - Api.ALIVE_PLAYERS.size()));
            Scoreboard.updateScoreboard("aliveplayers", String.valueOf(Api.ALIVE_PLAYERS.size()));

            checkEndGame();
        }
    }


    private void checkEndGame(){
        if (Api.ALIVE_PLAYERS.size() <= 1){
            final String name = Api.ALIVE_PLAYERS.size() == 0 ? "nobody" : Api.ALIVE_PLAYERS.get(0).getDisplayName();
            if (Api.getState() == State.ENDING) return;

            Api.setState(State.ENDING);
            Bukkit.broadcastMessage(Api.translator(
                    String.format("&9&lUHC> &fThe winner is &e%s", name)
            ));

            if (!name.equalsIgnoreCase("nobody")){
                SServer.getUser(Api.ALIVE_PLAYERS.get(0)).getUserData().addWin(SServer.GameID.UHC);
            }

            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Api.getGame().gameStop(), 10L * 20L);
        }
    }
}
