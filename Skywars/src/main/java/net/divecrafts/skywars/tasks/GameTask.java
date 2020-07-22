package net.divecrafts.skywars.tasks;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.game.GameState;
import io.clonalejandro.DivecraftsCore.game.GamesTask;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Title;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import net.divecrafts.skywars.SkyWars;
import net.divecrafts.skywars.api.SkyIsland;
import net.divecrafts.skywars.api.SkyUser;
import net.divecrafts.skywars.game.GameArena;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameTask extends GamesTask {

    private final SkyWars plugin;
    private final GameArena game;

    public GameTask(SkyWars instance) {
        this.plugin = instance;
        game = plugin.getGameArena();
    }

    private static int count = 0;
    private boolean timePlayed = false;

    @Override
    public void run() {
        noPlayers();
        checkWinner();

        if (timePlayed) game.getPlayersInGame().forEach(p -> p.sendActionBar(Languaje.getLangMsg(p.getUserData().getLang(), "timeplayed").replace("%time%", String.valueOf(count))));

        switch (count) {
            case 0:
                GameState.state = GameState.GAME;
                game.getPlayersInGame().forEach(player -> {
                    final Player p = player.getPlayer();

                    Title.sendTitle(p, 1, 7, 1, "", Languaje.getLangMsg(player.getUserData().getLang(), "SW.titlestart"));
                    p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
                    p.setScoreboard(plugin.getServer().getScoreboardManager().getNewScoreboard());

                    final SkyUser user = SkyWars.getUser(p);
                    user.setCleanPlayer(GameMode.SURVIVAL);
                    user.setGameScoreboard();
                    user.getUserData().addPlay(SServer.GameID.SKYWARS);
                    user.save();
                });
                game.getIslands().forEach(SkyIsland::destroyCapsule);
                GameArena.setCanMoves(true);
                timePlayed = true;
                break;

            case 10:
                game.setDamageOnFall(true);
                break;
        }
        ++count;
    }

    @Override
    protected void end() {
        GameState.state = GameState.ENDING;
        new ShutdownTask(plugin).runTaskTimer(plugin, 0, 20);
    }

    public void checkWinner() {
        if (game.checkWinner() == null) return;
        Bukkit.broadcastMessage(String.valueOf(game.getPlayersInGame().size()));
        SUser winner = game.getPlayersInGame().get(0);

        Bukkit.getOnlinePlayers().forEach(player -> {
            final SkyUser p = SkyWars.getUser(player);
            p.getPlayer().playSound(p.getLoc(), Sound.LEVEL_UP, 1F, 1F);
            String[] titleMsg = Languaje.getLangMsg(p.getUserData().getLang(), "SW.titlewinner").replace("%player%", winner.getName()).split("\n");
            Title.sendTitle(p.getPlayer(), 1, 7, 1, titleMsg[0], titleMsg[1]);
            player.sendMessage(Utils.colorize(Languaje.getLangMsg(p.getUserData().getLang(), "SW.brwinner")));
        });

        winner.getUserData().addWin(SServer.GameID.SKYWARS);
        winner.save();
        end();
        cancel();
    }

    public static int getTimeLeft() {
        return count;
    }

    private void noPlayers(){
        if (game.getPlayersInGame().isEmpty()) plugin.getServer().shutdown();
    }
}
