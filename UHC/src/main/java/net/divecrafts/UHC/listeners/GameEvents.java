package net.divecrafts.UHC.listeners;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.Lobby;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Scoreboard;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

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

    @EventHandler
    public void serverListPing(ServerListPingEvent event){
        if (Api.getState() == State.RUNNING)
            event.setMotd("Running");
    }


    /** OTHERS **/

    /**
     * This function execute the onPlayerJoin order while state is "Running"
     * @param e
     */
    private void whilePlayerCanJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();
        e.setJoinMessage(null);
        player.kickPlayer(Api.translator("&c&lUHC> &fThe game is started :("));
    }


    /**
     * This function execute the onPlayerKickEvent order while state is "Running" or "Ending"
     * @param e
     */
    private void whilePlayerCanLeave(PlayerKickEvent e){
        Api.ALIVE_PLAYERS.remove(e.getPlayer());
    }


    /**
     * This function execute the onPlayerQuitEvent order while state is "Running" or "Ending"
     * @param e
     */
    private void whilePlayerCanLeave(PlayerQuitEvent e){
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
            player.teleport(new Lobby().getLocation());

            if (killer != null){
                if (!Api.KILLS.containsKey(killer))
                    Api.KILLS.put(killer, 1);
                else {
                    int kills = Api.KILLS.get(killer);
                    Api.KILLS.remove(killer);
                    Api.KILLS.put(killer, kills + 1);
                }

                final SUser user = SServer.getUser(killer);
                user.getUserData().addKill(SServer.GameID.UHC);
                user.save();
            }

            final SUser user = SServer.getUser(player);
            user.getUserData().addDeath(SServer.GameID.UHC);
            user.save();

            Bukkit.broadcastMessage(String.valueOf(SServer.getUser(player).getUserData().getDeaths(SServer.GameID.UHC)));
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
            Api.playSound(Bukkit.getWorld("Normal_tmp"), Sound.LEVEL_UP, 1F, 1F);

            Bukkit.getScheduler().runTaskTimer(Main.instance, () -> Bukkit.getOnlinePlayers().forEach(p -> spawnFireworks(p.getLocation(), 5)), 1L, 15L);
            Bukkit.getOnlinePlayers().forEach(p -> {
                final SUser user = SServer.getUser(p);
                p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.winner").replace("%player%", name));
            });

            if (!name.equalsIgnoreCase("nobody")){
                final SUser user = SServer.getUser(Api.ALIVE_PLAYERS.get(0));
                user.getUserData().addWin(SServer.GameID.UHC);
                user.save();
            }

            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Api.getGame().gameStop(), 10L * 20L);
        }
    }


    private void spawnFireworks(Location location, int amount){
        final Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        final FireworkMeta meta = firework.getFireworkMeta();

        meta.setPower(1);
        meta.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        firework.setFireworkMeta(meta);
        firework.detonate();

        for(int i = 0; i < amount; i++){
            final int randX = Api.getRandom(0, -5), randZ = Api.getRandom(0, -5);
            final Location customLoc = new Location(location.getWorld(), location.getX() - randX, location.getY(), location.getZ() - randZ);
            final Firework firework1 = (Firework) customLoc.getWorld().spawnEntity(customLoc, EntityType.FIREWORK);

            firework1.setFireworkMeta(meta);
        }
    }
}
