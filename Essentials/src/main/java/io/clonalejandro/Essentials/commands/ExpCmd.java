package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
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

public class ExpCmd extends Cmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.SMOD)) return true;

        if (args.length > 2){
            final Player target = Bukkit.getPlayer(args[1]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[1])));
                return true;
            }

            if (Double.parseDouble(args[2]) > 2 * Math.pow(10, 8)){
                sender.sendMessage(Main.translate("&c&lServer> &fEl nivel de experiencia a aumentar/asignar no puede ser tan elevado."));
                return true;
            }

            if (args[0].equalsIgnoreCase("give")){
                sender.sendMessage(Main.translate(String.format("&9&lServer> &fHas aumentado &e%s &fpuntos de exp al jugador &e%s", args[2], target.getName())));
                target.giveExp(Integer.parseInt(args[2]));
            }
            else if (args[0].equalsIgnoreCase("set")){
                sender.sendMessage(Main.translate(String.format("&9&lServer> &fHas establecido &e%s &fpuntos de exp al jugador &e%s", args[2], target.getName())));
                target.setLevel(0);
                target.giveExp(Integer.parseInt(args[2]));
            }
            return true;
        }

        sender.sendMessage(Main.translate("&9&lServer> &fEl formato correcto es &b/exp give|set &e<jugador> &e<cantidad>"));
        return true;
    }
}
