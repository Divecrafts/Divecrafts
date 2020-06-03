package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex
 * On 30/05/2020
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
 * All rights reserved for clonalejandro ©DivecraftsCore 2017/2020
 */

public class BroadcastCMD extends SCmd {

    public BroadcastCMD() {
        super("broadcast", Rank.TMOD, Arrays.asList("br", "difundir"));
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        if (args.length > 0) Bukkit.broadcastMessage(Utils.colorize(String.format("&a&lServer> &f%s", String.join(" ", args))));
        else user.getPlayer().sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/broadcast &e<mensaje>"));
    }

    @Override
    public void run(ConsoleCommandSender sender, String lbl, String[] args) {
        if (args.length > 0) Bukkit.broadcastMessage(Utils.colorize(String.format("&a&lServer> &f%s", String.join(" ", args))));
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/broadcast &e<mensaje>"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
