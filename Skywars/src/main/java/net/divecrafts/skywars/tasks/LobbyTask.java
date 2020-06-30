package net.divecrafts.skywars.tasks;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.game.GameState;
import io.clonalejandro.DivecraftsCore.game.GamesTask;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import net.divecrafts.skywars.SkyWars;
import net.divecrafts.skywars.game.GameArena;

import org.bukkit.Sound;

import java.util.stream.Collectors;

public class LobbyTask extends GamesTask {

    private final SkyWars plugin;
    private final GameArena game;

    public LobbyTask(SkyWars instance) {
        this.plugin = instance;
        game = plugin.getGameArena();
    }

    private int count = 30;

    @Override
    public void run() {
        if (!game.canStart()) {
            GameState.state = GameState.LOBBY;
            cancel();
            return;
        }

        game.getPlayersInGame().forEach(p -> p.sendActionBar(Main.getPREFIX() + "&a&lEl juego empieza en: " + count));

        switch (count) {
            case 30:
                Utils.broadcastMsg("Games.start");
                break;
            case 6:
                game.getPlayersInGame().forEach(user ->
                        user.teleport(plugin.getGameArena().getIslands().stream()
                                .filter(island -> island.getOwner() == user.getUuid())
                                .collect(Collectors.toList())
                                .get(0).getSpawn())
                );
                break;
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                Utils.broadcastMsg("Games.start");
                game.getPlayersInGame().forEach(p -> p.getPlayer().playSound(p.getLoc(), Sound.NOTE_PLING, 1F, 1F));
                break;

            case 0:
                game.setDamageOnFall(false);
                game.fillChests();
                end();
                break;
        }
        --count;
    }

    @Override
    protected void end() {
        new GameTask(plugin).runTaskTimer(plugin, 0, 20);
        cancel();
    }
}