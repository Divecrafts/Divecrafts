package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Alex
 * On 02/05/2020
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

public class InvseeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (args.length > 0){
            final Player player = Bukkit.getPlayer(sender.getName());
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            if (player == target){
                sender.sendMessage(Main.translate("&c&lServer> &fNo puedes abrir tu propio inventario"));
            }

            final Inventory inventory = target.getInventory();
            player.openInventory(inventory);

            player.sendMessage(Main.translate(String.format("&9&lServer> &fAbriendo inventario de &e%s", target.getName())));
        }
        else sender.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/invsee &e<jugador>"));
        return true;
    }
}
