package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Hologramas;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

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

            final List<String> holoNames = Main.getInstance().getConfig().getStringList("Holograms");
            final String prompt = "Holograms." + "hol" + Integer.parseInt(holoNames.get(holoNames.size() -1).replace("hol", "")) + 1 + ".";
            final Configuration con = Main.getInstance().getConfig();

            con.set(prompt + "msg", args[0]);
            con.set(prompt + "world", user.getPlayer().getLocation().getWorld().getName());
            con.set(prompt + "x", user.getPlayer().getLocation().getX());
            con.set(prompt + "y", user.getPlayer().getLocation().getY());
            con.set(prompt + "z", user.getPlayer().getLocation().getZ());
        }
        else user.getPlayer().sendMessage(Utils.colorize("&c&lServer> &fFormato incorrecto usa &b/holograms &e<mensaje>"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
