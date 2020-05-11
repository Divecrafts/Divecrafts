package io.clonalejandro.DivecraftsLobby;

import lombok.Getter;
import io.clonalejandro.DivecraftsCore.SCommands;
import io.clonalejandro.DivecraftsLobby.api.LobbyUser;
import io.clonalejandro.DivecraftsLobby.commands.CMD_ClearChat;
import io.clonalejandro.DivecraftsLobby.cosmetics.CosmeticManager;
import io.clonalejandro.DivecraftsLobby.events.PlayerEvents;
import io.clonalejandro.DivecraftsLobby.events.WorldEvents;
import io.clonalejandro.DivecraftsLobby.ex.CosmeticRegisteredException;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Getter static ArrayList<LobbyUser> users = new ArrayList<>();

    @Getter String holo1 = "&6Welcome to &5Stylus &dLite&6, We hope that";
    @Getter String holo1p2 = "&6you have the best experience ever on the server.";

    @Getter String holo2 = "&6Do you want to have rank and do not";
    @Getter String holo2p2 = "&6know where to buy it? Use &dshop.styluslite.net";

    @Getter String holo3 = "&6Do you want the server to be a better";
    @Getter String holo3p2 = "&6place to play? Apply to be &dstaff &6and &dhelp the server.";

    @Getter private CosmeticManager cosmeticManager;

    public void onEnable() {
        instance = this;

        registerClasses();
        registerEvents();

        Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/minecraft:kill @e");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelAllTasks();
    }

    private void registerClasses() {
        SCommands.register(new CMD_ClearChat());
        try {
            cosmeticManager = new CosmeticManager();
        } catch (CosmeticRegisteredException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerEvents(), this);
        pm.registerEvents(new WorldEvents(), this);
    }

    public static LobbyUser getUser(OfflinePlayer p) {
        for (LobbyUser pl : users) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(p.getUniqueId())) return pl;
        }
        LobbyUser us = new LobbyUser(p.getUniqueId());
        if (us.isOnline()) users.add(us);
        return us;
    }
}
