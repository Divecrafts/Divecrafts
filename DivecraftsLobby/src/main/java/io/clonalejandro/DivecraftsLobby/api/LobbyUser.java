package io.clonalejandro.DivecraftsLobby.api;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.ItemMaker;
import io.clonalejandro.DivecraftsCore.utils.ScoreboardUtil;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.DivecraftsLobby.Main;
import io.clonalejandro.DivecraftsLobby.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.UUID;

public class LobbyUser extends SUser {

    private String sbName = "&f&lDivecrafts";
    private int sbColor = 1;

    public LobbyUser(UUID uuid) {
        super(uuid);
    }

    public void onQuit() {
        Main.getUsers().removeIf(u -> Main.getUsers().contains(u));
    }

    public void onJoin() {
        PlayerInventory i = getPlayer().getInventory();
        i.clear();
        getPlayer().updateInventory();
        i.setItem(0, new ItemMaker(Material.COMPASS).setName(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.brujula")).setLore(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.brujula")).build());
        i.setItem(1, ItemUtil.createHeadPlayer(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.perfil"), getName(), Arrays.asList(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.perfil"))));
        i.setItem(8, new ItemMaker(Material.NETHER_STAR).setName(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.lobby")).setLore(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.lobby")).build());

        switch (getUserData().getVisible()) {
            case 0:
                i.setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 8).setName(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.visibilidad")).setLore(Arrays.asList(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.visibilidad").split("\n"))).build());
                break;
            case 1:
                i.setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 5).setName(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.visibilidad")).setLore(Arrays.asList(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.visibilidad").split("\n"))).build());
                break;
            case 2:
                i.setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 10).setName(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.visibilidad")).setLore(Arrays.asList(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.visibilidad").split("\n"))).build());
                break;
        }

        getPlayer().setLevel(0);
        getPlayer().setExp(0);

        PotionEffect nospeed = PotionEffectType.SPEED.createEffect(99999999, 1);
        getPlayer().addPotionEffect(nospeed);

        getPlayer().setGameMode(GameMode.ADVENTURE);

        getPlayer().updateInventory();
        setScoreBoard();

        getPlayer().teleport(getPlayer().getWorld().getSpawnLocation());
    }


    public void setScoreBoard() {

        ScoreboardUtil board = new ScoreboardUtil(sbName, "lobby");

        board.setName(Utils.colorize(sbName));
        board.text(10, Utils.colorize("&1"));
        board.text(9, Utils.colorize(Languaje.getLangMsg(getUserData().getLang(), "Scoreboardlobby.rango") + "&" + SCmd.Rank.groupColor(getUserData().getRank()) + getUserData().getRank().getPrefix()));
        board.text(8, Utils.colorize("&fBoosters: &a0"));
        board.text(7, Utils.colorize("&fKeys: &a0"));
        board.text(6, Utils.colorize(Languaje.getLangMsg(getUserData().getLang(), "Scoreboardlobby.monedas") + getUserData().getCoins()));
        board.text(5, Utils.colorize("&2"));
        board.text(4, Utils.colorize("&fLobby: &a#" + Bukkit.getServerId()));
        board.text(3, Utils.colorize("&f"));
        board.text(2, Utils.colorize("&3"));
        board.text(1, Utils.colorize("&ewww.divecrafts.net"));

        board.team("players", Languaje.getLangMsg(getUserData().getLang(), "Scoreboardlobby.jugadores"));
        board.getTeam("players").addEntry(Utils.colorize("&f"));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();

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
                board.getTeam("players").setSuffix(String.valueOf(Bukkit.getOnlinePlayers().size()));
                if (getPlayer() != null) board.build(getPlayer());
            }
        }.runTaskTimer(Main.getInstance(), 0, 5);
    }
}
