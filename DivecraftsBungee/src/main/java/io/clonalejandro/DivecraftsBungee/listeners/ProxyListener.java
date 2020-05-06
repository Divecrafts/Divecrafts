package io.clonalejandro.DivecraftsBungee.listeners;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.managers.party.utils.Party;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ProxyListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProxyPing(ProxyPingEvent event) throws IOException {
		ServerPing ping = event.getResponse();
		ping.setDescription(TextUtils.formatText(Main.getConfigManager().getConfig().getString("motd")));
	}
	
	@EventHandler
    public void onSwitch(ServerSwitchEvent e) throws IOException {
        ProxiedPlayer player = e.getPlayer();
    	for (String name : Main.getSvPerms().svgames) {
	        if (player.getServer().getInfo().getName().startsWith(name)) {
	        	Main.getSvPerms().inGame.add(player);
	        } else {
	        	Main.getSvPerms().inGame.remove(player);
	        }
    	}
        
        Party party = Main.getMB().getPartyManager().getParty(player);
        if (party != null) {
            if (party.getLeader().equals(player)) {
            	for (String lobbyname : Main.getConfigManager().getConfig().getStringList("Lobbies")) {
	                if (!party.getLeader().getServer().getInfo().getName().contains(lobbyname)) {
	                    Main.getMB().getProxy().getScheduler().schedule(Main.getMB(), () -> {
	                        for (ProxiedPlayer other : party.getPlayers()) {
	                            if (!other.getServer().getInfo().equals(player.getServer().getInfo()) && !Main.getSvPerms().inGame.contains(other)) {
	                                other.connect(player.getServer().getInfo());
									try {
										other.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(other), "Party.cambiandosv"));
									} catch (SQLException ex) {
										ex.printStackTrace();
									}
	                            }
	                        }
	                    }, 1, TimeUnit.SECONDS);
	                }
            	}
            }
        }
    }

}
