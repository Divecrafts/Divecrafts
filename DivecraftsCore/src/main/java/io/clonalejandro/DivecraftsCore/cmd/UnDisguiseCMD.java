package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Disguise;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;

/**
 * Created by Alex
 * On 23/05/2020
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

public class UnDisguiseCMD extends SCmd {

    public UnDisguiseCMD() {
        super("uf", Rank.TMOD, "ufd");
    }

    public void run(SUser u, String label, String... args) {
        final String targetName = Disguise.getDisguises().get(u.getName());

        if (targetName == null){
            u.getPlayer().sendMessage(Utils.colorize("&9&lServer> &fNo tienes antiguos disguises"));
            return;
        }

        //Clear disguise
        Disguise.getDisguises().remove(u.getName());
        new Disguise(u, targetName);
        u.getUserData().setDisguise("");

        Bukkit.getScheduler().runTaskLater(plugin, () -> Utils.updateUserColor(u), 4L);

        u.getPlayer().sendMessage(Languaje.getLangMsg(u.getUserData().getLang(), "Ajustes.cambiado"));
    }
}
