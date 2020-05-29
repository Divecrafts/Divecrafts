package io.clonalejandro.DivecraftsCore.utils;

import io.clonalejandro.DivecraftsCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class AntiWorldDownloader implements PluginMessageListener {

    private final Main plugin;
    private final String version;

    public AntiWorldDownloader(Main instance){
        this.plugin = instance;
        this.version = plugin.getServer().getVersion();
    }

    public void enable(){
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(
                plugin, version.contains("1.8") ? "WDL|INIT" : "divecrafts:wdl|init", this);

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(
                plugin, version.contains("1.8") ? "WDL|CONTROL" : "divecrafts:wdl|control");
    }


    public void disable(){
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(
                plugin, version.contains("1.8") ? "WDL|INIT" : "divecrafts:wdl|init", this);

        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(
                plugin, version.contains("1.8") ? "WDL|CONTROL" : "divecrafts:wdl|control");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equalsIgnoreCase(version.contains("1.8") ? "WDL|INIT" : "divecrafts:wdl|init"))
            player.kickPlayer("NOT ALLOWED TO USE AWD");
    }
}
