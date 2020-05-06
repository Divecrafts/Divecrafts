package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.BungeeServer;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_Network extends Command {

	public CMD_Network() {
		super("network");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if (player.hasPermission("bungee.admin")) {
				if (args.length < 2) {
					try {
						player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Network.Uso"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (args[0].equalsIgnoreCase("info") ) {
					if (ProxyServer.getInstance().getServerInfo(args[1]) != null) {
						BungeeServer servidor = new BungeeServer(args[1]);
	                    int servsize = servidor.getOnlineSize();
	                    int total = Main.getMB().getProxy().getPlayers().size();
	                    int porcentaje = servsize * 100 / total;
						try {
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Network.nombre") + args[1]);
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Network.online") + servsize);
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Network.porcentaje") + porcentaje);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return;
					}
					try {
						player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Network.svnoexist"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.cmdnopuedes"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
		}
	}
}
