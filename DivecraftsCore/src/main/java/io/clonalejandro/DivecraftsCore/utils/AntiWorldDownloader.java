package io.clonalejandro.DivecraftsCore.utils;

import io.clonalejandro.DivecraftsCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class AntiWorldDownloader implements PluginMessageListener {

    private Main plugin;

    public AntiWorldDownloader(Main instance){
        this.plugin = instance;
    }

    public void enable(){
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(
                plugin, "WDL|INIT", this);

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(
                plugin, "WDL|CONTROL");
    }


    public void disable(){
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(
                plugin, "WDL|INIT", this);

        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(
                plugin, "WDL|CONTROL");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equalsIgnoreCase("WDL|INIT"))
            player.kickPlayer("NOT ALLOWED TO USE AWD");
    }


}
