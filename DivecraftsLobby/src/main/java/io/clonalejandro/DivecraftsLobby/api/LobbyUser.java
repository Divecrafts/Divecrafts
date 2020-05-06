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

import java.util.Arrays;
import java.util.UUID;

public class LobbyUser extends SUser {

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
        i.setItem(4, new ItemMaker(Material.DIAMOND_SWORD).setName(Languaje.getLangMsg(getUserData().getLang(), "Itemnames.clicktest")).setLore(Languaje.getLangMsg(getUserData().getLang(), "Itemlores.clicktest")).build());
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
        setScoreBoard(Utils.colorize("&5STYLUS &dNETWORK"));

        getPlayer().teleport(getPlayer().getWorld().getSpawnLocation());

        getPlayer().sendMessage("§7§m---------------------------------------------");
        getPlayer().sendMessage(Languaje.getLangMsg(getUserData().getLang(), "Bienvenida.welcome").replace("%player%", getName()));
        getPlayer().sendMessage(Languaje.getLangMsg(getUserData().getLang(), "Bienvenida.tienda"));
        getPlayer().sendMessage(Languaje.getLangMsg(getUserData().getLang(), "Bienvenida.web"));
        getPlayer().sendMessage(Languaje.getLangMsg(getUserData().getLang(), "Bienvenida.twitter"));
        getPlayer().sendMessage(Languaje.getLangMsg(getUserData().getLang(), "Bienvenida.discord"));
        getPlayer().sendMessage("§7§m---------------------------------------------");

    }

    public void setScoreBoard(String displayname) {
        ScoreboardUtil board = new ScoreboardUtil(displayname, "lobby");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPlayer() == null) cancel();
                board.setName(Utils.colorize(displayname));
                board.text(11, Utils.colorize("&6&8&m--------------------"));
                board.text(10, Utils.colorize(Languaje.getLangMsg(getUserData().getLang(), "Scoreboardlobby.rango") + "§" + SCmd.Rank.groupColor(getUserData().getRank()) + "[" + getUserData().getRank() + "]"));
                board.text(9, Utils.colorize(Languaje.getLangMsg(getUserData().getLang(), "Scoreboardlobby.jugadores") + Bukkit.getServer().getOnlinePlayers().size()));
                board.text(8, Utils.colorize("&8 "));
                board.text(7, Utils.colorize("&5News:"));
                board.text(6, Utils.colorize("&6@StylusLite"));
                board.text(5, Utils.colorize("&5 "));
                board.text(4, Utils.colorize(Languaje.getLangMsg(getUserData().getLang(), "Scoreboardlobby.monedas") + getUserData().getCoins()));
                board.text(3, Utils.colorize("&5&8&m--------------------"));
                board.text(2, Utils.colorize("&5Lobby: &f" + Bukkit.getServerId().substring(Bukkit.getServerId().length() - 1)));
                board.text(1, Utils.colorize("&7&8&m--------------------"));
                if (getPlayer() != null) board.build(getPlayer());
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
}
