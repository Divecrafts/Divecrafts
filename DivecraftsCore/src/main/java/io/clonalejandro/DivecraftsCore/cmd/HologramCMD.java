package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Hologramas;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex
 * On 22/06/2020
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

public class HologramCMD extends SCmd {

    public HologramCMD() {
        super("holograms", Rank.TMOD, Arrays.asList("holo", "hologramas", "hologram", "holograma"));
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        if (args.length > 0){
            Hologramas.crearHolo(Utils.colorize(args[0]), user.getPlayer().getLocation(), user.getPlayer().getLocation().getWorld().getName());

            final Configuration con = Main.getInstance().getConfig();
            String pprompt = "Holograms.hol0.msg";
            String result = con.getString(pprompt);
            String id = "hol0";

            while (result != null){
                int val = Integer.parseInt(id.replace("hol", "")) + 1;
                id = "hol" + val;
                pprompt = "Holograms." + id + ".msg";
                result = con.getString(pprompt);
            }

            int val = Integer.parseInt(id.replace("hol", "")) + 1 - 1;//This shit fucking works
            final String prompt = "Holograms.hol" + val + ".";

            con.set(prompt.substring(prompt.length() -1), null);
            con.set(prompt + "msg", args[0]);
            con.set(prompt + "world", user.getPlayer().getLocation().getWorld().getName());
            con.set(prompt + "x", user.getPlayer().getLocation().getX());
            con.set(prompt + "y", user.getPlayer().getLocation().getY());
            con.set(prompt + "z", user.getPlayer().getLocation().getZ());

            Main.getInstance().saveConfig();
        }
        else user.getPlayer().sendMessage(Utils.colorize("&c&lServer> &fFormato incorrecto usa &b/holograms &e<mensaje>"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
