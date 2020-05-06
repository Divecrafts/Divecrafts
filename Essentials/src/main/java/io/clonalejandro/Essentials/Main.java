package io.clonalejandro.Essentials;

import io.clonalejandro.Essentials.commands.*;
import io.clonalejandro.Essentials.events.*;
import io.clonalejandro.Essentials.utils.MysqlManager;
import io.clonalejandro.Essentials.utils.SpawnYml;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(translate("&a&lEssentials> &fplugin enabled!"));
        try {
            instance = this;
            final PluginManager pluginManager = Bukkit.getPluginManager();

            database();
            config();
            events(pluginManager);
            commands();
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
            Bukkit.getPluginManager().disablePlugin(instance);
            Bukkit.getConsoleSender().sendMessage(translate("&c&lEssentials> &fplugin disabled!"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        instance = null;
    }

    private void events(final PluginManager pluginManager){
        pluginManager.registerEvents(new FlyHandler(), this);
        pluginManager.registerEvents(new GodHandler(), this);
        pluginManager.registerEvents(new SpawnHandler(), this);
        pluginManager.registerEvents(new ChatHandler(), this);
        pluginManager.registerEvents(new SignHandler(), this);
        pluginManager.registerEvents(new VanishHandler(), this);
        pluginManager.registerEvents(new BackHandler(), this);
        //pluginManager.registerEvents(new TpaHandlers(), this);

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
        getCommand("broadcast").setExecutor(new BroadcastCmd());
        getCommand("iteminfo").setExecutor(new ItemInfoCmd());

        Bukkit.getConsoleSender().sendMessage(translate("&9&lEssentials> &fregistrando comandos"));
    }

    private void database(){
        new MysqlManager("localhost", 3306, "survival", "root", "patata123");
        Bukkit.getConsoleSender().sendMessage(Main.translate("&9&lEssentials> &fdatabase connected"));
    }

    public static String translate(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
