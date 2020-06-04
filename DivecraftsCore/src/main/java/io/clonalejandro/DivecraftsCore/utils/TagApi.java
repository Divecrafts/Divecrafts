package io.clonalejandro.DivecraftsCore.utils;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by Alex
 * On 04/06/2020
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
 * All rights reserved for clonalejandro ©DivecraftsCore 2017/2020
 */

public class TagAPI {

    public TagAPI(){
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () ->
                Bukkit.getOnlinePlayers().forEach(players ->
                        Bukkit.getOnlinePlayers().forEach(target -> {
                            final String str = getColor(SServer.getUser(target));
                            final String tag = str.length() > 16 ? str.substring(0, 16) : str;
                            final Team team = players.getScoreboard().getTeam(target.getName()) == null ?
                                    players.getScoreboard().registerNewTeam(target.getName()) :
                                    players.getScoreboard().getTeam(target.getName());

                            team.setPrefix(tag);
                            team.addPlayer(target);
                        })
                ), 20L);
    }

    private String getColor(SUser user){
        final SCmd.Rank rank = user.getUserData().getRank();
        final String color = user.getUserData().getNickcolor().isEmpty() ? "&7" : "&" + user.getUserData().getNickcolor();
        final String prefix = rank.getRank() > 0 ? "&" + SCmd.Rank.groupColor(rank) + rank.getPrefix() + " " : "";
        final String name = prefix + color + user.getPlayer().getName();

        return Utils.colorize(name.replace(user.getPlayer().getName(), ""));
    }
}
