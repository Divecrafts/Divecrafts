package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.objects.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 01/06/2020
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

public class PermissionCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (args.length > 2){
            final String mode = args[0];
            final Player target = Bukkit.getPlayer(args[1]);
            final String node = args[2];

            if (target != null){
                final Permission permission = new Permission(target.getUniqueId());

                if (mode.equalsIgnoreCase("add")) permission.add(node);
                else if (mode.equalsIgnoreCase("remove")) permission.add(node);
                else {
                    sender.sendMessage(Utils.colorize("&c&lServer> &fModo incorrecto"));
                    return true;
                }

                sender.sendMessage(Utils.colorize(String.format("&9&lServer> &fHas %s a &e%s &fel permiso &e%s",
                        mode.equalsIgnoreCase("add") ? "añadido" : "eliminado",
                        target.getName(),
                        node
                )));
            }
            else sender.sendMessage(Utils.colorize("&c&lServer> &fEl jugador ha de estar online"));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/indperm &e<add|remove> <jugador> &e<nodo>"));

        return true;
    }

}
