package net.divecrafts.skywars.tasks;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.game.GameState;
import io.clonalejandro.DivecraftsCore.game.GamesTask;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import net.divecrafts.skywars.SkyWars;
import net.divecrafts.skywars.game.*;

import net.divecrafts.skywars.game.votes.*;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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

        game.getPlayersInGame().forEach(u -> u.sendActionBar(Utils.colorize(Languaje.getLangMsg(u.getUserData().getLang(), "SW.brstarttime").replace("a", String.valueOf(count)))));

        switch (count) {
            case 30:
                game.getPlayersInGame().forEach(u -> u.getPlayer().sendMessage(Utils.colorize(Languaje.getLangMsg(u.getUserData().getLang(), "SW.brstarttime").replace("a", String.valueOf(count)))));
                break;
            case 6:
                game.getPlayersInGame().forEach(user ->
                        user.teleport(plugin.getGameArena().getIslands().stream()
                                .filter(island -> island.getOwner() == user.getUuid())
                                .collect(Collectors.toList())
                                .get(0).getSpawn())
                );
                GameArena.setCanMoves(false);
                break;
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                game.getPlayersInGame().forEach(u -> u.getPlayer().sendMessage(Utils.colorize(Languaje.getLangMsg(u.getUserData().getLang(), "SW.brstarttime").replace("a", String.valueOf(count)))));
                game.getPlayersInGame().forEach(p -> p.getPlayer().playSound(p.getLoc(), Sound.NOTE_PLING, 1F, 1F));
                break;

            case 0:
                game.setDamageOnFall(false);

                GameArena.setDifficulty(resolveMode().getId());
                game.fillChests();
                game.getArena().getArenaData().getWorld().setTime(resolveTime().getTime());
                game.getArena().getArenaData().getWorld().setStorm(resolveBiome().isRaining());
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

    private ModeType resolveMode(){
        return new ArrayList<>(VoteMode.getVotes().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (acc, e) -> acc, LinkedHashMap::new))
                .keySet())
                .get(0);
    }

    private TimeType resolveTime(){
        return new ArrayList<>(VoteTime.getVotes().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (acc, e) -> acc, LinkedHashMap::new))
                .keySet())
                .get(0);
    }

    private BiomeType resolveBiome(){
        return new ArrayList<>(VoteBiome.getVotes().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (acc, e) -> acc, LinkedHashMap::new))
                .keySet())
                .get(0);
    }
}