package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.managers.party.utils.Party;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CMD_Party extends Command {

    private final Main plugin;

    public CMD_Party(Main plugin) {
        super("party", null, "fiesta");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (!(cs instanceof ProxiedPlayer)) {
			TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) cs;
        ResultSet rsparty = Main.getMySQL().query("SELECT * FROM `settings` WHERE uuid = '" + player.getUniqueId() + "'");
        boolean partyActived = false;
        try {
			while (rsparty.next())
			    partyActived = rsparty.getBoolean("party");
		}
        catch (SQLException e) {
			e.printStackTrace();
		}
        if (!partyActived) {
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.NoActivados"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        	return;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("chat")) {
            Party party = plugin.getPartyManager().getParty(player);
            if (party != null) {
                StringBuilder sb = new StringBuilder();
                for (int amount = 1; amount < args.length; amount++)
                    sb.append(args[amount]).append(" ");
                party.broadcastMessage(player.getName() + " &8& &7" + sb.toString());
            }
            else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.NoPerteneces"));
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                Party party = plugin.getPartyManager().getParty(player);
                if (party != null) {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.infotitulo"));
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.infolider") + party.getLeader().getName());
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.infoplayers"));
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (ProxiedPlayer others : party.getPlayers())
                        player.sendMessage(new TextComponent("- " + others.getName()));
                }
                else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.NoPerteneces"));
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            else if (args[0].equalsIgnoreCase("leave")) {
                Party party = plugin.getPartyManager().getParty(player);
                if (party != null) {
                    if (party.getLeader().equals(player)) party.removeLeader();
                    else party.removePlayer(player);
                }
                else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.NoPerteneces"));
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }
        else if (args.length == 2) {
            ProxiedPlayer other = plugin.getProxy().getPlayer(args[1]);
            if (other != null) {
                if (args[0].equalsIgnoreCase("invite")) {
                    plugin.getPartyManager().invitePlayer(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("accept")) {
                    plugin.getPartyManager().acceptInvitation(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("deny")) {
                    plugin.getPartyManager().denyInvitation(player, other);
                    return;
                } else if (args[0].equalsIgnoreCase("kick")) {
                    plugin.getPartyManager().kickPlayer(player, other);
                    return;
                }
                else if (args[0].equalsIgnoreCase("warp")) {
                    plugin.getPartyManager().getParty(player).getPlayers().forEach(partyPlayer -> partyPlayer.connect(player.getServer().getInfo()));
                    return;
                }
            }
            else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.noconectado"));
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        player.sendMessage(new TextComponent("- §d§lPARTYS §f-"));
        player.sendMessage(new TextComponent("§b/party §elist"));
        player.sendMessage(new TextComponent("§b/party §eleave"));
        player.sendMessage(new TextComponent("§b/party §warp"));
        player.sendMessage(new TextComponent("§b/party §einvite <Player>"));
        player.sendMessage(new TextComponent("§b/party §eaccept <Player>"));
        player.sendMessage(new TextComponent("§b/party §edeny <Player>"));
        player.sendMessage(new TextComponent("§b/party §ekick <Player>"));
        player.sendMessage(new TextComponent("§b/party §echat <Message>"));
    }
}
