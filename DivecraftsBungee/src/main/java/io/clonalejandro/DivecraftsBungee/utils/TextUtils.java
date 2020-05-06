package io.clonalejandro.DivecraftsBungee.utils;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TextUtils {
	
	@Getter private static String PREFIX = formatText("&a&lServer> &f");
	@Getter private static String PREFIXE = formatText("&c&lServer> &f");

	public static String formatText(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	@SuppressWarnings("deprecation")
	public static void consoleMSG(String text, MessageType msgType) {
		CommandSender sender = Main.getMB().getProxy().getConsole();
		switch (msgType) {
		case ERROR:
			sender.sendMessage(PREFIXE + formatText(text));
			break;
		case SUCCESS:
			sender.sendMessage(PREFIX + formatText(text));
			break;
		default:
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void playerMSG(ProxiedPlayer player, String text, MessageType msgType) {
		switch (msgType) {
		case ERROR:
			player.sendMessage(PREFIXE + formatText(text));
			break;
		case SUCCESS:
			player.sendMessage(PREFIX + formatText(text));
			break;
		default:
			break;
		}
	}
	
}
