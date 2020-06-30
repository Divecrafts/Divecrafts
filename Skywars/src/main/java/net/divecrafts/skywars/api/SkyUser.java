package net.divecrafts.skywars.api;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.game.GameState;
import io.clonalejandro.DivecraftsCore.utils.ScoreboardUtil;
import net.divecrafts.skywars.SkyWars;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SkyUser extends SUser {

    private final SkyWars plugin = SkyWars.getInstance();

    public SkyUser(UUID id) {
        super(id);
    }

    public void setWaitScoreboard() {
        ScoreboardUtil board = new ScoreboardUtil(Main.getPREFIX(), "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (GameState.state == GameState.LOBBY) {
                    board.setName("§a§lSkyWars");
                    board.text(5, "§d ");
                    board.text(4, "§fJugadores: §a" + plugin.getGameArena().getPlayersInGame().size());
                    board.text(3, "§a ");
                    board.text(2, "§eEsperando...");
                    board.text(1, "§e ");
                    board.text(0, "§ewww.divecrafts.net");
                    if (getPlayer() != null) board.build(getPlayer());
                } else {
                    board.reset();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    public void setGameScoreboard() {
        ScoreboardUtil board = new ScoreboardUtil(Main.getPREFIX(), "game");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

                if (GameState.state == GameState.GAME) {
                    board.setName(Main.getPREFIX());
                    board.text(3, "§7Jugadores: ");
                    board.text(2, "§f" + plugin.getGameArena().getPlayersInGame().size());
                    board.text(1, "§f ");
                    board.text(0, "§ewww.divecrafts.net");
                } else {
                    board.reset();
                    cancel();
                }

                if (getPlayer() != null) board.build(getPlayer());
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    public void setLobbyPlayer() {
        setWaitScoreboard();
        plugin.getGameArena().addPlayerToGame(this);
        setCleanPlayer(GameMode.ADVENTURE);
    }

    public void setSpectator() {
        setCleanPlayer(GameMode.SPECTATOR);
        plugin.getGameArena().getSpectators().add(this);
        plugin.getGameArena().getPlayersInGame().forEach(ig -> ig.getPlayer().hidePlayer(getPlayer()));
    }

    public void setCleanPlayer(GameMode gameMode) {
        getPlayer().setHealth(getPlayer().getMaxHealth());
        getPlayer().setFoodLevel(20);
        getPlayer().setExp(0);
        getPlayer().setTotalExperience(0);
        getPlayer().setLevel(0);
        getPlayer().setFireTicks(0);
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setArmorContents(null);
        getPlayer().setGameMode(gameMode);
        getPlayer().getActivePotionEffects().forEach(ef -> getPlayer().removePotionEffect(ef.getType()));
    }

    public void death() {
        setCleanPlayer(GameMode.SPECTATOR);
        getPlayer().getInventory().clear();
        setSpectator();
        plugin.getGameArena().removePlayerFromGame(this);
        getPlayer().sendMessage("*Escribe &e/lobby &fpara volver al Lobby");
        getUserData().addDeath(SServer.GameID.SKYWARS);
        save();
    }

    public void spawn() {
        getPlayer().teleport(SkyIsland.getIsland(getUuid()).getSpawn());
    }
}
