package io.clonalejandro.Essentials;

import io.clonalejandro.Essentials.commands.*;
import io.clonalejandro.Essentials.events.*;
import io.clonalejandro.Essentials.hooks.VaultHook;
import io.clonalejandro.Essentials.objects.Warp;
import io.clonalejandro.Essentials.providers.EconomyProvider;
import io.clonalejandro.Essentials.tasks.AutoRestart;
import io.clonalejandro.Essentials.utils.Economy;
import io.clonalejandro.Essentials.utils.MysqlManager;
import io.clonalejandro.Essentials.utils.SpawnYml;
import io.clonalejandro.SQLAlive.SQLAlive;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex
 * On 30/04/2020
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

public class Main extends JavaPlugin {

    public static Main instance;
    public static SpawnYml spawnYml;
    public EconomyProvider economyProvider;
    public VaultHook vaultHook;
    public final static HashMap<Player, BukkitTask> awaitingPlayersToTeleport = new HashMap<>();
    public static final List<BukkitRunnable> tasks = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(translate("&a&lEssentials> &fplugin enabled!"));
        try {
            instance = this;
            final PluginManager pluginManager = Bukkit.getPluginManager();

            config();
            database();

            economyProvider = new EconomyProvider();
            vaultHook = new VaultHook();

            events(pluginManager);
            commands();
            initAutoRestart();
            vaultHook.hook();
            loadWarps();

            new SQLAlive(this, MysqlManager.getConnection());
        }
        catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(translate("&c&lEssentials> &fseveral error"));
            onDisable();
        }
    }


    @Override
    public void onDisable() {
        try {
            saveEconomy();
            MysqlManager.getConnection().close();
            vaultHook.unhook();
            tasks.forEach(BukkitRunnable::cancel);
            Bukkit.getPluginManager().disablePlugin(instance);
            Bukkit.getConsoleSender().sendMessage(translate("&c&lEssentials> &fplugin disabled!"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        instance = null;
    }

    private void events(final PluginManager pluginManager){
        pluginManager.registerEvents(new GodHandler(), this);
        pluginManager.registerEvents(new SpawnHandler(), this);
        pluginManager.registerEvents(new SignHandler(), this);
        pluginManager.registerEvents(new VanishHandler(), this);
        pluginManager.registerEvents(new BackHandler(), this);
        pluginManager.registerEvents(new TpaHandlers(), this);
        pluginManager.registerEvents(new ScoreboardHandler(), this);
        pluginManager.registerEvents(new CreeperHandler(), this);
        pluginManager.registerEvents(new FlyHandler(), this);
        pluginManager.registerEvents(new WarpHandler(), this);
        pluginManager.registerEvents(new HomeHandler(), this);
        pluginManager.registerEvents(new EconomyHandler(), this);
        pluginManager.registerEvents(new PlayerHandlers(), this);

        Bukkit.getConsoleSender().sendMessage(translate("&9&lEssentials> &fregistrando eventos"));
    }

    private void config(){
        saveDefaultConfig();
        spawnYml = new SpawnYml();
    }

    private void commands(){
        getCommand("exp").setExecutor(new ExpCmd());
        getCommand("heal").setExecutor(new HealCmd());
        getCommand("feed").setExecutor(new FeedCmd());
        getCommand("fly").setExecutor(new FlyCmd());
        getCommand("clear").setExecutor(new ClearCmd());
        getCommand("gamemode").setExecutor(new GamemodeCmd());
        getCommand("hat").setExecutor(new HatCmd());
        getCommand("god").setExecutor(new GodCmd());
        getCommand("speed").setExecutor(new SpeedCmd());
        getCommand("time").setExecutor(new TimeCmd());
        getCommand("vanish").setExecutor(new VanishCmd());
        getCommand("tpa").setExecutor(new TpaCmd(this));
        getCommand("tpaall").setExecutor(new TpaCmd(this));
        getCommand("tpaccept").setExecutor(new TpaCmd(this));
        getCommand("tpdeny").setExecutor(new TpaCmd(this));
        getCommand("tpahere").setExecutor(new TpaCmd(this));
        getCommand("tp").setExecutor(new TpCmd());
        getCommand("tphere").setExecutor(new TpCmd());
        getCommand("tpall").setExecutor(new TpCmd());
        getCommand("invsee").setExecutor(new InvseeCmd());
        getCommand("spawn").setExecutor(new SpawnCmd());
        getCommand("setspawn").setExecutor(new SpawnCmd());
        getCommand("sethome").setExecutor(new HomeCmd());
        getCommand("delhome").setExecutor(new HomeCmd());
        getCommand("home").setExecutor(new HomeCmd());
        getCommand("warp").setExecutor(new WarpCmd());
        getCommand("setwarp").setExecutor(new WarpCmd());
        getCommand("delwarp").setExecutor(new WarpCmd());
        getCommand("warps").setExecutor(new WarpCmd());
        getCommand("back").setExecutor(new BackCmd());
        getCommand("iteminfo").setExecutor(new ItemInfoCmd());
        getCommand("balance").setExecutor(new EconomyCmd());
        getCommand("balanceTop").setExecutor(new EconomyCmd());
        getCommand("deposit").setExecutor(new EconomyCmd());
        getCommand("withdraw").setExecutor(new EconomyCmd());
        getCommand("pay").setExecutor(new EconomyCmd());
        getCommand("individualpermission").setExecutor(new PermissionCmd());
        getCommand("fix").setExecutor(new RepairCmd());
        getCommand("givehead").setExecutor(new HeadCmd());
        getCommand("itemrename").setExecutor(new ItemRenameCmd());
        getCommand("enderchest").setExecutor(new EnderchestCmd());
        getCommand("stack").setExecutor(new StackCmd());

        Bukkit.getConsoleSender().sendMessage(translate("&9&lEssentials> &fregistrando comandos"));
    }

    private void database(){
        new MysqlManager(getConfig().getString("host"), 3306, getConfig().getString("database"), "root", "patata123");
        Bukkit.getConsoleSender().sendMessage(Main.translate("&9&lEssentials> &fdatabase connected"));
    }

    private void initAutoRestart(){
        final AutoRestart task = new AutoRestart();
        tasks.add(task);
        task.runTaskTimer(this, 0L, 20L);
    }

    private void loadWarps(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            final String query = "SELECT * FROM Warps";
            final List<Warp> warps = new ArrayList<>();

            try {
                final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);

                while (result.next())
                    warps.add(new Warp(result));
            }
            catch (SQLException ignored){

            }

            Warp.warpList.addAll(warps);
        }, 20L);
    }

    public static String translate(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private void saveEconomy(){
        Economy.economyPlayers.forEach((key, val) -> val.save());
    }
}
