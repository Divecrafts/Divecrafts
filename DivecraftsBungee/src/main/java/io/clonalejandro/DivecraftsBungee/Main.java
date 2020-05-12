package io.clonalejandro.DivecraftsBungee;

import io.clonalejandro.DivecraftsBungee.comands.*;
import io.clonalejandro.DivecraftsBungee.listeners.PlayerListener;
import io.clonalejandro.DivecraftsBungee.listeners.ProxyListener;
import io.clonalejandro.DivecraftsBungee.listeners.SvListener;
import io.clonalejandro.DivecraftsBungee.managers.ServerPerms;
import io.clonalejandro.DivecraftsBungee.managers.clan.ClanManager;
import io.clonalejandro.DivecraftsBungee.managers.party.PartyManager;
import io.clonalejandro.DivecraftsBungee.systems.ConfigManager;
import io.clonalejandro.DivecraftsBungee.systems.ServerManager;
import io.clonalejandro.DivecraftsBungee.utils.MySQL;
import io.clonalejandro.DivecraftsBungee.utils.Payloads;
import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;


import java.io.IOException;

public class Main extends Plugin implements Listener {

    @Getter private static MySQL mySQL;
	@Getter private static Main MB;
	@Getter private static ServerManager svManager = new ServerManager();
	@Getter public static ConfigManager configManager = new ConfigManager();
	@Getter private static ServerPerms svPerms = new ServerPerms();
    @Getter private PartyManager partyManager;
	@Getter private ClanManager clanManager;
	
	@Override
	public void onEnable() {
		MB = this;
		setupDB();

	    getProxy().registerChannel("DivecraftsBungee");
	    
		try {
			getConfigManager().generateConfig(MB);
			getSvManager().iniciarSvManager(MB);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		partyManager = new PartyManager(this);
		clanManager = new ClanManager();
		try {
			clanManager.loadClanes();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		getSvPerms().initPerms();
		
	    getProxy().getPluginManager().registerListener(this, new PlayerListener());
	    getProxy().getPluginManager().registerListener(this, new ProxyListener());
	  	getProxy().getPluginManager().registerListener(this, new SvListener());
		getProxy().getPluginManager().registerListener(this, new Payloads());
		getProxy().getPluginManager().registerListener(this, this);

        getProxy().getPluginManager().registerCommand(this, new CMD_Clan(this));
		getProxy().getPluginManager().registerCommand(this, new CMD_Network());
		getProxy().getPluginManager().registerCommand(this, new CMD_StaffChat());
		getProxy().getPluginManager().registerCommand(this, new CMD_Goto());
		getProxy().getPluginManager().registerCommand(this, new CMD_find());
		getProxy().getPluginManager().registerCommand(this, new CMD_Bug());
		getProxy().getPluginManager().registerCommand(this, new CMD_Report());
		getProxy().getPluginManager().registerCommand(this, new CMD_ReportStaff());
		getProxy().getPluginManager().registerCommand(this, new CMD_Ping());
		getProxy().getPluginManager().registerCommand(this, new CMD_Party(this));
		getProxy().getPluginManager().registerCommand(this, new CMD_Alert());
		getProxy().getPluginManager().registerCommand(this, new CMD_Ranksinfo());
		getProxy().getPluginManager().registerCommand(this, new CommandLobby());
		getProxy().getPluginManager().registerCommand(this, new CMD_Request());
		getProxy().getPluginManager().registerCommand(this, new CMD_Punish());
	}
	
	private void setupDB() {
        mySQL = new MySQL("localhost", "divecrafts", "root", "patata123");
        getMySQL().conectarDB();
    }

    public ServerInfo getLobby() {
        return getProxy().getServerInfo("lobby");
    }

    @EventHandler
    public void onServerKickEvent(ServerKickEvent e) {
        e.setCancelled(true);
        e.setCancelServer(getLobby());
    }
}
