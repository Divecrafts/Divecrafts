package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 30/04/2020
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class GamemodeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
        if (args.length == 1){
            updateGamemode(Bukkit.getPlayer(sender.getName()), args[0]);
        }
        else if (args.length > 1) {
            final Player target = Bukkit.getPlayer(args[1]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[1])));
                return true;
            }

            updateGamemode(target, args[0]);
            sender.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha cambiado el modo de juego de &e%s &fa &e%s", target.getName(), target.getGameMode())));
        }
        else sender.sendMessage(Main.translate(String.format("&c&lServer> &fformato incorrecto usa &b/%s &e<modo> &e<jugador>", cmd.getName())));
        return true;
    }


    private void updateGamemode(final Player player, final String mode){
        switch (mode.toLowerCase()){
            case "survival":
            case "0":
            case "s":
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case "creative":
            case "1":
            case "c":
                player.setGameMode(GameMode.CREATIVE);
                break;
            case "adventure":
            case "2":
            case "a":
                player.setGameMode(GameMode.ADVENTURE);
                break;
            case "spectator":
            case "3":
            case "sp":
                player.setGameMode(GameMode.SPECTATOR);
                break;
        }

        player.sendMessage(Main.translate(String.format("&9&lServer> &fTu modo de juego ha cambiado a &e%s", player.getGameMode())));
    }
}
