package io.clonalejandro.DivecraftsCore.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.kits.Kit;
import io.clonalejandro.DivecraftsCore.utils.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Game implements Listener {

    protected final Main plugin = Main.getInstance();

    @Getter private final SServer.GameID game;
    @Getter private final String name;
    @Getter private final List<String> desc;

    @Getter @Setter @NonNull private Arena arena;

    @Getter private final ArrayList<SUser> playersInGame = new ArrayList<>();
    @Getter private final ArrayList<SUser> spectators = new ArrayList<>();

    @Getter @Setter private boolean canBreak = false;
    @Getter @Setter private boolean canPlace = false;

    /**
     * Default constructor
     *
     * @param game GameID, extracted from SServer.GameID
     * @param name The Game Name
     * @param desc Small description about the game
     *
     * @see SServer.GameID
     */
    public Game(SServer.GameID game, String name, String... desc) {
        this.game = game;
        this.name = name;
        this.desc = Arrays.asList(desc);
    }


    @EventHandler
    public abstract void onDamageEvent(EntityDamageByEntityEvent e);
    @EventHandler
    public abstract void onPlayerDamageEvent(EntityDamageEvent e);
    @EventHandler
    public abstract void onLogin(PlayerLoginEvent e);
    @EventHandler
    public abstract void onJoin(PlayerJoinEvent e);
    @EventHandler
    public abstract void onLeave(PlayerQuitEvent e);
    @EventHandler
    public abstract void onInteract(PlayerInteractEvent e);
    @EventHandler
    public abstract void onInteractEntity(PlayerInteractAtEntityEvent e);

    /**
     * Available Kits for the Game
     *
     * @return List<Kit>
     */
    public abstract List<Kit> availableKits();

    /**
     * Available Items for the player
     *
     * @return List<ItemStack>
     */
    public abstract List<ItemStack> playerItems();


    @EventHandler
    public void onBreakEvent(BlockBreakEvent e) {
        SUser user = SServer.getUser(e.getPlayer());

        if (!isPlayerInGame(user)) {
            if (user.isOnRank(SCmd.Rank.BUILDER)) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(canBreak);
        }
    }

    @EventHandler
    public void onPlaceEvent(BlockPlaceEvent e) {
        SUser user = SServer.getUser(e.getPlayer());

        if (!isPlayerInGame(user)) {
            if (user.isOnRank(SCmd.Rank.BUILDER)) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(canPlace);
        }
    }


    public boolean canStart() {
        return playersInGame.size() >= arena.getArenaData().getMinPlayers();
    }

    /**
     * Checks if a user wins (return null if the game is still working)
     *
     * @return winner (SUser)
     */
    public SUser checkWinner() {
        if (!isInGame()) return null;
        if (playersInGame.size() > 2) return null;
        SUser winner = playersInGame.get(0);

        GameState.state = GameState.ENDING;
        return winner;
    }

    /**
     * Starts the game
     */
    public void startGame() {
        if (playersInGame.size() < arena.getArenaData().getMinPlayers()) return;
        playersInGame.forEach(p -> arena.teleportToArena(p));

        if (!playerItems().isEmpty()) playersInGame.forEach(p -> p.getInventory().addItem(playerItems().toArray(new ItemStack[0])));
    }

    /**
     * Method to check if the JSON is correct for the game
     */
    public void correctMap() {
        if (!arena.getArenaData().getGame().equalsIgnoreCase(game.name().toLowerCase())) {
            Log.log(Log.ERROR, "El mapa seleccionado no corresponde a este tipo de juego");
            Log.log(Log.ERROR, "Modo de juego: " + game.name());
            Log.log(Log.ERROR, "Tipo de mapa: " + arena.getArenaData().getGame());
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    /**
     * Checks if the user is in game
     *
     * @param user
     * @return true if player is in game
     */
    public boolean isPlayerInGame(SUser user) {
        return playersInGame.contains(user);
    }

    /**
     * Adds a player to the game
     *
     * @param player
     */
    public void addPlayerToGame(SUser player) {
        removePlayerFromGame(player);
        playersInGame.add(player);
    }

    /**
     * Removes a player to the game
     *
     * @param player
     */
    public void removePlayerFromGame(SUser player) {
        if (playersInGame.contains(player)) playersInGame.remove(player);
    }

    /**
     * Checks if the game accepts players
     *
     * @return if the game accept players
     */
    public boolean acceptPlayers() {
        return GameState.state == GameState.PREPARING || GameState.state == GameState.LOBBY;
    }

    /**
     * Checks if the game is started
     *
     * @return if the game is started
     */
    public boolean isInGame() {
        return GameState.state == GameState.GAME || GameState.state == GameState.DEATHMATCH;
    }
}
