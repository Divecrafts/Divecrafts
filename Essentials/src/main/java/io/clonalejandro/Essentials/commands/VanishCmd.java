package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.VanishPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 01/05/2020
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

public class VanishCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        player.sendMessage(Main.translate(String.format("&a&lServer> &fModo vanish %s", VanishPacket.isVanish(player) ? "&cdesactivado" : "&aactivado")));

        if (VanishPacket.isVanish(player)) VanishPacket.removeVanish(player);
        else VanishPacket.setVanish(player);

        return true;
    }
}
