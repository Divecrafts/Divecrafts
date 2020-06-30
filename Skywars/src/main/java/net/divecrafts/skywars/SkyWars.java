package net.divecrafts.skywars;

import lombok.Getter;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.game.GameState;

import net.divecrafts.skywars.api.SkyIsland;
import net.divecrafts.skywars.api.SkyUser;
import net.divecrafts.skywars.game.GameArena;
import net.divecrafts.skywars.utils.ChestItems;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class SkyWars extends JavaPlugin {

    @Getter private static SkyWars instance;

    public static ArrayList<SkyUser> players = new ArrayList<>();

    @Getter private GameArena gameArena;
    @Getter private ChestItems chestItems;

    @Override
    public void onEnable() {
        instance = this;

        Main.setPREFIX("§9§lSW> ");

        File fConf = new File(getDataFolder(), "config.yml");
        if (!fConf.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
            }
            catch (Exception ignored) {

            }
        }

        gameArena = new GameArena();
        chestItems = new ChestItems(instance);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(gameArena, instance);

        GameState.state = GameState.LOBBY;
    }

    @Override
    public void onDisable(){
        getGameArena().getIslands().forEach(SkyIsland::destroyCapsule);
    }

    public static SkyUser getUser(OfflinePlayer p) {
        for (SkyUser pl : players) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(p.getUniqueId())) return pl;
        }
        SkyUser us = new SkyUser(p.getUniqueId());
        if (us.isOnline()) players.add(us);
        return us;
    }
}
