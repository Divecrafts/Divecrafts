package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
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

public class GodCmd extends Cmd implements CommandExecutor {

    public static ArrayList<String> players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
        if (checkPermissions(sender, SCmd.Rank.MEDUSA)) return true;

        if (args.length > 0){
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            goGod(target);
        }
        else {
            goGod(Bukkit.getPlayer(sender.getName()));
        }
        return true;
    }

    private void goGod(Player player){

        final boolean isPlayerInGod = players.contains(player.getName());

        if (isPlayerInGod)
            players.remove(player.getName());
        else {
            players.add(player.getName());
            player.setFoodLevel(20);
            player.setHealth(20);
        }

        player.sendMessage(Main.translate(String.format("&9&lServer> &fModo dios %s", isPlayerInGod ? "&cdesactivado" : "&aactivado")));
    }
}
