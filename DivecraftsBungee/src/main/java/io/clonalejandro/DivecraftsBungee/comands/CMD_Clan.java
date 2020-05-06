package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.clan.Clan;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CMD_Clan extends Command {

    private final Main plugin;

    public CMD_Clan(Main plugin) {
        super("clan", null, "clanes");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (!(cs instanceof ProxiedPlayer)) {
			TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) cs;
        ResultSet rsclanes = Main.getMySQL().query("SELECT * FROM `settings` WHERE uuid = '" + player.getUniqueId() + "'");
        boolean clanActived = false;
        try {
			while (rsclanes.next()) {
				clanActived = rsclanes.getBoolean("clanes");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        if (!clanActived) {
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.NoActivados"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("chat")) {
            Clan clan = plugin.getClanManager().getClan(player);
            if (clan != null) {
                StringBuilder sb = new StringBuilder();
                for (int amount = 1; amount < args.length; amount++) {
                    sb.append(args[amount]).append(" ");
                }
                try {
                    clan.broadcastMessage(player.getName() + " §8§l>> §b" + sb.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.NoPerteneces"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return;
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("create")) {
            plugin.getClanManager().createClan(args[1], player);
            return;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {
                Clan clan = plugin.getClanManager().getClan(player);
                if (clan != null) {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.infotitulo"));
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.infolider") + clan.getLider());
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.infoplayers"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (ProxiedPlayer others : clan.getMiembros()) {
                        String textOffline;
                        if (others.isConnected()) {
                            textOffline = " §a(Online)";
                        } else {
                            textOffline = " §c(Offline)";
                        }
                        player.sendMessage(new TextComponent("- " + others.getName() + textOffline));
                    }
                } else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.NoPerteneces"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            } else if (args[0].equalsIgnoreCase("leave")) {
                Clan clan = plugin.getClanManager().getClan(player);
                if (clan != null) {
                        clan.removePlayer(player);
                        Main.getMySQL().actualizarQuery("UPDATE `data` SET `clan`=null WHERE `name` = '" + player.getName() +"'");
                } else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.NoPerteneces"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            } else if (args[0].equalsIgnoreCase("delete")) {
                Clan clan = plugin.getClanManager().getClan(player);
                if (clan != null) {
                    if (clan.getLider().equals(player.getName())) {
                        clan.removeClan();
                    } else {
                        try {
                            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.nolider"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.NoPerteneces"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        } else if (args.length == 2) {
            ProxiedPlayer other = plugin.getProxy().getPlayer(args[1]);
            if (other != null) {
                if (args[0].equalsIgnoreCase("invite")) {
                    plugin.getClanManager().invitePlayer(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    plugin.getClanManager().acceptInvitation(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("deny")) {
                    plugin.getClanManager().denyInvitation(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    plugin.getClanManager().kickPlayer(player, other);
                    return;
                }
            } else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.noconectado"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        player.sendMessage(new TextComponent("- §d§lSTYLUS CLANES §e-"));
        player.sendMessage(new TextComponent("§6/clan info"));
        player.sendMessage(new TextComponent("§6/clan leave"));
        player.sendMessage(new TextComponent("§6/clan create <Name>"));
        player.sendMessage(new TextComponent("§6/clan invite <Player>"));
        player.sendMessage(new TextComponent("§6/clan accept <Player>"));
        player.sendMessage(new TextComponent("§6/clan deny <Player>"));
        player.sendMessage(new TextComponent("§6/clan kick <Player>"));
        player.sendMessage(new TextComponent("§6/clan chat <Message>"));
        player.sendMessage(new TextComponent("§6/clan delete"));
    }
}
