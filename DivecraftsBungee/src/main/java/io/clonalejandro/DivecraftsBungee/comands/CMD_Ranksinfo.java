package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


public class CMD_Ranksinfo extends Command {

	public CMD_Ranksinfo() {
		super("ranksinfo", null, "rangosinf");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer playerSender = (ProxiedPlayer) sender;
			//TODO:
			/*
			playerSender.sendMessage(TextUtils.formatText("&d[&dIndra] &6$53 Usd Lifetime - $43 Usd 2 Months"));
			playerSender.sendMessage(TextUtils.formatText("&e[&eSirus] &6$43 Usd Lifetime - $38 Usd 2 Months"));
			playerSender.sendMessage(TextUtils.formatText("&7[&7Horus] &6$38 Usd Lifetime - $28 Usd 2 Months"));
			playerSender.sendMessage(TextUtils.formatText("&6[&6Xipe] &6$18 Usd Lifetime - $13 Usd 2 Months"));
			playerSender.sendMessage(TextUtils.formatText("&b[&bRa] &6$13 Usd Lifetime - $9 Usd 2 Months"));
			playerSender.sendMessage(TextUtils.formatText("&3[&3Famous&d+&3] &65k Yt Subs y 5k Twitch Followers"));
			playerSender.sendMessage(TextUtils.formatText("&e[&eYoutuber] &62k Subs, 2 videos in the server, y 500 views"));
			playerSender.sendMessage(TextUtils.formatText("&5[&5Streamer] &62k Follows, 2 directs in the server and 10 viewers"));*/
		}
		else TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
	}

}
