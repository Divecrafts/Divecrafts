package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandLobby extends Command {

    public CommandLobby() {
        super("lobby", "", "hub");
    }

    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = Main.getMB().getProxy().getPlayer(sender.getName());

        if (p.getServer().getInfo().getName().equalsIgnoreCase("login")) return;

        if (args.length == 0) {
            p.connect(Main.getMB().getLobby());
        } else {
            sender.sendMessage(ChatColor.RED + "Este comando no necesita argumentos");
        }
    }
}
