package net.divecrafts.DivecraftsAuth;

import lombok.Getter;

import net.divecrafts.DivecraftsAuth.events.PlayerListeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Alex
 * On 03/06/2020
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
 * All rights reserved for clonalejandro ©DivecraftsAuth 2017/2020
 */

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    @Override
    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage(translate("&a&lDivecraftsAuth> &fplugin enabled!"));
        try {
            instance = this;
            final PluginManager pluginManager = Bukkit.getPluginManager();

            Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
            events(pluginManager);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(translate("&c&lDivecraftsAuth> &fseveral error"));
            onDisable();
        }
    }


    @Override
    public void onDisable() {
        try {
            Bukkit.getPluginManager().disablePlugin(instance);
            Bukkit.getConsoleSender().sendMessage(translate("&c&lDivecraftsAuth> &fplugin disabled!"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        instance = null;
    }


    private void events(final PluginManager pluginManager){
        pluginManager.registerEvents(new PlayerListeners(), this);
    }

    public static String translate(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
