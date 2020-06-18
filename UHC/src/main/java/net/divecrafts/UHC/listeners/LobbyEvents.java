package net.divecrafts.UHC.listeners;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.Game;
import net.divecrafts.UHC.minigame.Lobby;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.task.GameCountDown;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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

public class LobbyEvents implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final Main plugin;
    private Lobby lobby;

    public LobbyEvents(Main instance){
        plugin = instance;
    }


    /** REST **/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (Api.getState() == State.LOBBY) {
            whilePlayerCanJoin(e);
            Game.playerSpawn.put(e.getPlayer(), Arena.genRandomSpawn(63));
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        if (Api.getState() == State.LOBBY) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if (Api.getState() == State.LOBBY) e.getEntity().teleport(
                lobby.getLocation()
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        if (Api.getState() == State.LOBBY) onQuit();
        e.setQuitMessage(Api.NULL);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (Api.getState() == State.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onDamageByItem(EntityDamageByEntityEvent event) {
        if (Api.getState() == State.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        if (Api.getState() == State.LOBBY) onQuit();
        e.setLeaveMessage(Api.NULL);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        final boolean isDefaultWorld = e.getPlayer().getWorld().getName().equalsIgnoreCase("world");

        if (Api.getState() == State.LOBBY && isDefaultWorld)
            e.setCancelled(true);
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e){
        if ((Api.getState() == State.LOBBY || Api.getState() == State.ENDING) &&
                e.getEntityType() == EntityType.PLAYER){
            e.setCancelled(true);
        }
    }


    /** OTHERS **/

    /**
     * This function execute the onPlayerJoin order while state is "Lobby"
     * @param event
     */
    private void whilePlayerCanJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final ItemStack air = new ItemStack(Material.AIR);

        lobby = new Lobby();

        event.setJoinMessage(Api.translator(
                Api.getConfigManager().getJoinMessage().replace(
                "{PLAYER}", player.getName()
                )
        ));

        Scoreboard.lobbyScoreboard(player); // Set scoreboard to a player join
        Scoreboard.updateScoreboard("onlineplayers", Api.translator(
                "&f" + (Api.getOnline())
        ));

        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[]{air, air, air, air});
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.ADVENTURE);
        }, 20L);

        if (lobby.getLocation() != null)
            player.teleport(lobby.getLocation());

        if (Api.getOnline() == Api.getConfigManager().getPlayersForStart())
            startCountDown();
    }


    /**
     * This function execute the onPlayerKickEvent order while state is "Lobby"
     */
    private void onQuit(){
        Scoreboard.updateScoreboard("onlineplayers", Api.translator(
                "&f" + (Api.getOnline() -1)
        ));
    }


    /**
     * This function start a game count down
     */
    private void startCountDown(){
        new GameCountDown(plugin, false).runTaskTimer(plugin, 1L, 20L);
    }


}
