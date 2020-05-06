package io.clonalejandro.DivecraftsBungee.managers;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeServer {
	
    private String serverName;
    private ServerInfo serverInfo;

    public BungeeServer(String serverName) {
        this.serverName = serverName;
        this.serverInfo = Main.getMB().getProxy().getServerInfo(this.serverName);
    }

    public String getColoredServerName() {
        return TextUtils.formatText(this.serverName);
    }

    public int getOnlineSize() {
        return this.serverInfo.getPlayers().size();
    }

    public boolean isPlayerOnline(ProxiedPlayer someone) {
        return this.serverInfo.getPlayers().contains(someone);
    }

    public ProxiedPlayer getPlayer(ProxiedPlayer someone) {
        return ProxyServer.getInstance().getPlayer(someone.getName());
    }
}

