package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_Alert extends Command {

    public CMD_Alert() {
        super("gritar", "bungee.alert", "alertar");
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 0) {
                try {
                    sender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "BungeeAlert.NoMsg"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                StringBuilder builder = new StringBuilder();
                String[] arrayOfString;
                int j = (arrayOfString = args).length;
                for (int i = 0; i < j; i++) {
                    String s = arrayOfString[i];
                    builder.append(ChatColor.translateAlternateColorCodes('&', s));
                    builder.append(" ");
                }
                String message = builder.substring(0, builder.length() - 1);

                ProxyServer.getInstance().getPlayers().forEach(p -> p.sendMessage(ChatColor.AQUA + sender.getName() + ChatColor.RESET + ": " + ChatColor.translateAlternateColorCodes('&', message)));
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Tienes que añadir un mensaje!");
            } else {
                StringBuilder builder = new StringBuilder();
                String[] arrayOfString;
                int j = (arrayOfString = args).length;
                for (int i = 0; i < j; i++) {
                    String s = arrayOfString[i];
                    builder.append(ChatColor.translateAlternateColorCodes('&', s));
                    builder.append(" ");
                }
                String message = builder.substring(0, builder.length() - 1);

                ProxyServer.getInstance().getPlayers().forEach(p -> p.sendMessage(ChatColor.AQUA + sender.getName() + ChatColor.RESET + ": " + ChatColor.translateAlternateColorCodes('&', message)));
            }
        }
    }
}
