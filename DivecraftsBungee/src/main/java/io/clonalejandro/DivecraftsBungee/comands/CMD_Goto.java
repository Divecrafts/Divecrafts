package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_Goto extends Command {

	
	public CMD_Goto() {
		super("ira", null, "goto");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(sender.hasPermission("bungee.staff")) {
				if (args.length < 1) {
					try {
						player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Goto.Uso"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					ProxiedPlayer a = Main.getMB().getProxy().getPlayer(args[0]);
					if(a != null) {
						ServerInfo irA = a.getServer().getInfo();
			            player.connect(irA);
						try {
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Goto.ConectandoA") + irA.getName());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						try {
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.noconectado"));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				try {
					player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.cmdnopuedes"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
