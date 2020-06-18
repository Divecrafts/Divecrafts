package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.ScoreboardUtil;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.MysqlManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Alex
 * On 30/05/2020
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
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class ScoreboardHandler implements Listener {

    private String sbName = "&f&lDivecrafts";
    private int sbColor = 1;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            try {
                final PreparedStatement statement1 = MysqlManager.getConnection().prepareStatement("SELECT * FROM economy WHERE uuid=?");
                statement1.setString(1, event.getPlayer().getUniqueId().toString());
                final ResultSet rs = statement1.executeQuery();

                if (!rs.next()){
                    final PreparedStatement statement2 = MysqlManager.getConnection().prepareStatement("INSERT INTO economy VALUES(?, ?, ?)");
                    statement2.setString(1, event.getPlayer().getUniqueId().toString());
                    statement2.setDouble(2, 1300D);
                    statement2.setString(3, event.getPlayer().getName());
                    statement2.executeUpdate();
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });

        setScoreBoard(SServer.getUser(event.getPlayer()));
    }

    public void setScoreBoard(SUser user) {
        final ScoreboardUtil board = new ScoreboardUtil(sbName, "survival");
        final String rankColored = SCmd.Rank.groupColor(user.getUserData().getRank()) + user.getUserData().getRank().getPrefix();
        final String rank = user.getUserData().getRank() == SCmd.Rank.USUARIO ? rankColored + "&lUSER" : rankColored;

        final String sBoosters = Utils.colorize("&fBoosters: ");//TODO: Add lang support
        final String sPlayers = Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardlobby.jugadoresPrefix");
        final String sWorld = Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardsurvival.mundoPrefix");
        final String sCoins = Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardsurvival.monedasPrefix");

        final String serverName = Main.instance.getConfig().getString("server");

        board.setName(Utils.colorize(sbName));
        board.text(10, Utils.colorize("&1"));
        board.text(9, Utils.colorize(Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardlobby.rango") + "&" + rank));
        board.text(8, sBoosters);
        board.text(7, sWorld);
        board.text(6, sCoins);
        board.text(5, Utils.colorize("&2"));
        board.text(4, Utils.colorize("&fServer: &a" + serverName));
        board.text(3, sPlayers);
        board.text(2, Utils.colorize("&3"));
        board.text(1, Utils.colorize("&ewww.divecrafts.net"));

        board.team("boosters", "");
        board.team("players", "");
        board.team("world", "");
        board.team("coins", "");

        board.getTeam("boosters").addEntry(sBoosters);
        board.getTeam("players").addEntry(sPlayers);
        board.getTeam("world").addEntry(sWorld);
        board.getTeam("coins").addEntry(sCoins);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.getPlayer() == null || !user.getPlayer().isOnline()) {
                    cancel();
                    return;
                }

                switch (sbColor){
                    case 1:
                        sbName = "&a&lDivecrafts";
                        sbColor++;
                        break;
                    case 2:
                        sbName = "&b&lDivecrafts";
                        sbColor++;
                        break;
                    case 3:
                        sbName = "&c&lDivecrafts";
                        sbColor++;
                        break;
                    case 4:
                        sbName = "&d&lDivecrafts";
                        sbColor++;
                        break;
                    case 5:
                        sbName = "&e&lDivecrafts";
                        sbColor++;
                        break;
                    case 6:
                        sbName = "&6&lDivecrafts";
                        sbColor++;
                        break;
                    default:
                        sbName = "&f&lDivecrafts";
                        sbColor = 1;
                        break;
                }

                board.setName(sbName);

                board.getTeam("boosters").setSuffix(
                        Utils.colorize("&a") + user.getUserData().getBoosters().size()
                );
                board.getTeam("players").setSuffix(
                        Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardlobby.jugadoresSuffix") + Bukkit.getOnlinePlayers().size()
                );
                board.getTeam("world").setSuffix(
                        Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardsurvival.mundoSuffix") + user.getPlayer().getWorld().getName()
                );
                board.getTeam("coins").setSuffix(
                        Languaje.getLangMsg(user.getUserData().getLang(), "Scoreboardsurvival.monedasSuffix") + Main.instance.economyProvider.getBalance(user.getPlayer())
                );

                board.build(user.getPlayer());
            }
        }.runTaskTimer(Main.instance, 0, 5);
    }

}
