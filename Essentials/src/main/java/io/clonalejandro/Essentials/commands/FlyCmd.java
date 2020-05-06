package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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

public class FlyCmd implements CommandExecutor {


    public static ArrayList<String> players = new ArrayList<>();


    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (args.length > 0){
            final Player player = Bukkit.getPlayer(args[0]);

            if (player == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[1])));
                return true;
            }

            goFly(player);
        }
        else goFly(Bukkit.getPlayer(sender.getName()));
        return true;
    }


    private void goFly(Player player){
        final boolean canFly = !players.contains(player.getName());

        player.setAllowFlight(canFly);
        player.setFlying(canFly);

        if (!canFly) players.remove(player.getName());
        else players.add(player.getName());

        player.sendMessage(Main.translate(String.format("&9&lServer> &fModo fly %s", players.contains(player.getName()) ? "&aactivado" : "&cdesactivado")));
    }
}
