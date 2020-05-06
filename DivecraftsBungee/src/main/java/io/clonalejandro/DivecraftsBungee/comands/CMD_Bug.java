package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_Bug extends Command {

	public CMD_Bug() {
		super("bug");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer playerSender = (ProxiedPlayer) sender;
			if (args.length < 1) {
				try {
					playerSender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(playerSender), "Bug.Uso"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
            	sb.append(args[i]);
            	if (i < args.length) {
            		sb.append(" ");
            	}
           	}
            String servidor = playerSender.getServer().getInfo().getName();
            Main.getMySQL().actualizarQuery("INSERT INTO `bugs`(`bug`, `servidor`, `reportador`) VALUES ('" + sb.toString() + "','" + servidor + "','" + playerSender.getName() + "')");
			try {
				playerSender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(playerSender), "Bug.Gracias"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
		}
	}

}
