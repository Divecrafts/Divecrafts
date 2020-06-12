package net.divecrafts.UHC.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

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

public class Scoreboard {


    /** REST **/

    /**
     * This function set a scoreboard for game lobby
     * @param player
     */
    public static void lobbyScoreboard(Player player){
        final Map<Integer, String> scores = new HashMap<>();

        final org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective objective = scoreboard.registerNewObjective("UHCL", "dummy");

        final Team host = scoreboard.registerNewTeam("host");
        final Team players = scoreboard.registerNewTeam("onlineplayers");

        final String ip = Api.getConfigManager().getIp(),
                     title = Api.getConfigManager().getTitleScore(),
                     hostS = Api.getConfigManager().getHostScore().replace("{HOST}", ""),
                     teamS = Api.getConfigManager().getTeamScore().replace("{MODE}", Api.getConfigManager().getTeamMode()),
                     gameS = Api.getConfigManager().getGameScore().replace("{GAME}", ""),
                     onlineS = Api.getConfigManager().getOnlineScore().replace("{ONLINE}", "");


        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        scores.put(12, "&5");
        scores.put(11, hostS);
        scores.put(10, teamS);
        scores.put(9, "&1");
        scores.put(8, gameS);
        scores.put(7, "&r- OP UHC");
        scores.put(6, "&r- Time Bomb");
        scores.put(5, "&r- CutClean");
        scores.put(4, "&r- Vanilla+");
        scores.put(3, "&2");
        scores.put(2, onlineS);
        scores.put(1, "&3");
        scores.put(0, "&b" + ip);

        for (int score : scores.keySet()){
            Score skore = objective.getScore(Api.translator(scores.get(score)));
            skore.setScore(score);
        }

        //Register teams
        players.addEntry(Api.translator(onlineS));
        host.addEntry(Api.translator(hostS));

        //Set values of the teams
        host.setSuffix(Api.getHost() != null ? Api.getHost() : "");
        players.setSuffix(String.valueOf( Api.getOnline() ));

        //Set title
        scoreboard.getObjective(DisplaySlot.SIDEBAR).setDisplayName(Api.translator(title));

        player.setScoreboard(scoreboard);
    }


    /**
     * This function set a scoreboard for a game
     * @param player
     */
    public static void gameScoreboard(Player player){
        final Map<Integer, String> scores = new HashMap<>();

        final org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective objective = scoreboard.registerNewObjective("UHC", "dummy");

        final Team time = scoreboard.registerNewTeam("time"),
                   players = scoreboard.registerNewTeam("aliveplayers"),
                   host = scoreboard.registerNewTeam("host"),
                   distance = scoreboard.registerNewTeam("borderdistance"),
                   borders = scoreboard.registerNewTeam("border"),
                   spectator = scoreboard.registerNewTeam("spectators");

        final String ip = Api.getConfigManager().getIp(),
                     title = Api.getConfigManager().getTitleScore(),
                     hostS = Api.getConfigManager().getHostScore().replace("{HOST}", ""),
                     killS = Api.getConfigManager().getKillScore().replace("{KILLS}", ""),
                     distanceS = "&dBorder distance: &f",
                     playerS = Api.getConfigManager().getAliveScore().replace("{ALIVE}", ""),
                     borderS = Api.getConfigManager().getBorderScore().replace("{BORDER}", ""),
                     timeS = Api.getConfigManager().getTimeScore().replace("{TIME}", ""),
                     spectatorS = Api.getConfigManager().getSpectatorScore().replace("{SPECTATORS}", "");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        scores.put(12, "&5");
        scores.put(11, hostS);
        scores.put(10, timeS);//Time
        scores.put(9, "&1");
        scores.put(8, distanceS);
        scores.put(7, "&2");
        scores.put(6, playerS);//Alive players
        scores.put(5, spectatorS);
        scores.put(4, "&3");
        scores.put(3, borderS);
        scores.put(2, "&4");
        scores.put(1, ip);

        for (int score : scores.keySet()){
            Score skore = objective.getScore(Api.translator(scores.get(score)));
            skore.setScore(score);
        }

        //Register teams
        host.addEntry(Api.translator(hostS));
        time.addEntry(Api.translator(timeS));
        players.addEntry(Api.translator(playerS));
        distance.addEntry(Api.translator(borderS));
        //kill.addEntry(Api.translator(killS));
        borders.addEntry(Api.translator(borderS));
        spectator.addEntry(Api.translator(spectatorS));

        //Set values of the teams
        host.setSuffix(Api.getHost() != null ? Api.getHost() : "");
        players.setSuffix(String.valueOf(Api.ALIVE_PLAYERS.size()));
        time.setSuffix("0");
        //kill.setSuffix("0");
        distance.setSuffix(String.valueOf(Api.getArena().getBorder().resolveDistanceBetweenPlayer(player)));
        borders.setSuffix("750");
        spectator.setSuffix(String.valueOf(Api.getOnline() - Api.ALIVE_PLAYERS.size()));

        //Set title
        scoreboard.getObjective(DisplaySlot.SIDEBAR).setDisplayName(Api.translator(title));

        player.setScoreboard(scoreboard);
    }


    /**
     * This function update a value for scoreboard
     * @param team
     * @param data
     */
    public static void updateScoreboard(String team, String data){
        for (Player player : Api.getOnlinePlayers())
            player.getScoreboard().getTeam(team).setSuffix(data);
    }


    public static void updateScoreboard(Player player, String team, String data){
        player.getScoreboard().getTeam(team).setSuffix(data);
    }
}
