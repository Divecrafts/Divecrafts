package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_find extends Command {

	public CMD_find() {
		super("buscar", null, "find", "search");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(sender.hasPermission("bungee.staff")) {
				if (args.length < 1) {
					try {
						player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Find.Uso"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
                    ProxiedPlayer buscado = ProxyServer.getInstance().getPlayer(args[0]);
                    if (buscado != null) {
                    	String serverName = buscado.getServer().getInfo().getName();
                        int ping = buscado.getPing();
						TextComponent goTo = null;
						try {
							goTo = new TextComponent(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Find.ir"));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						goTo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goto " + buscado.getName()));
                        goTo.setColor(ChatColor.LIGHT_PURPLE);
                        goTo.setBold(true);
						try {
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Find.usuario") + buscado.getName());
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Find.servidor") + serverName);
							player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Find.ping").replaceAll("%ping%", String.valueOf(ping)));
						} catch (SQLException e) {
							e.printStackTrace();
						}
                        player.sendMessage(goTo);
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
