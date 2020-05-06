package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_Ping extends Command {
	
    public CMD_Ping() {
    	super("ping");
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (args.length == 0) {
                int ping = ProxyServer.getInstance().getPlayer(p.getName()).getPing();
                try {
                    p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Ping.tuping").replaceAll("%ping%", String.valueOf(ping)));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (args.length == 1) {
                if (p.hasPermission("bungee.staff")) {
                    if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
	                    ProxiedPlayer obj = ProxyServer.getInstance().getPlayer(args[0]);
	                    int ping = ProxyServer.getInstance().getPlayer(obj.getName()).getPing();
                        try {
                            p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Ping.elpingde").replace("%ping%", String.valueOf(ping)).replace("%player%", obj.getName()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
	                } else {
                        try {
                            p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Global.noconectado"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
	    			}
                } else {
                    try {
                        p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Global.cmdnopuedes"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
				}
            }
        } else {
			TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
        }
    }
}

