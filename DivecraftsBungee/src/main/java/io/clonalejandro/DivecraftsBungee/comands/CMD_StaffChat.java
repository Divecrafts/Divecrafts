package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_StaffChat extends Command {

	public CMD_StaffChat() {
		super("staffchat", null, "sc");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer playerSender = (ProxiedPlayer) sender;
			if (playerSender.hasPermission("bungee.staff")) {
				if (args.length > 0) {
	            	StringBuilder sb = new StringBuilder();
	                for (int i = 0; i < args.length; i++) {
	                    sb.append(args[i]);
	                    if (i < args.length) {
	                        sb.append(" ");
	                    }
	                }
	                for (ProxiedPlayer receiver : Main.getMB().getProxy().getPlayers()) {
	                	if (receiver.hasPermission("bungee.staff")) {
	                		receiver.sendMessage(TextUtils.formatText("&b&lSC> &e" + playerSender.getName() + ": &f" + sb.toString()));
	                	}
	                }
				} else {
					TextUtils.playerMSG(playerSender, "&fUsa: &b/sc &e<mensaje>", MessageType.SUCCESS);
				}
			} else {
				try {
					playerSender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(playerSender), "Global.cmdnopuedes"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
		}
	}

}
