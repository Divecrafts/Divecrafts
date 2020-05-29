package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Disguise;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import java.util.List;
import java.util.Random;

/**
 * Created by Alex
 * On 22/05/2020
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

public class DisguiseCMD extends SCmd {

    public DisguiseCMD() {
        super("fd", Rank.TMOD, "fdisguise");
    }

    public void run(SUser u, String label, String... args) {
        final List<String> names = plugin.getConfig().getStringList("disguises");
        final String randomName = getRandomName(names);
        final String targetName = args.length == 1 ? args[0] : randomName;

        if (targetName != null){
            new Disguise(u, targetName);
            u.getPlayer().sendMessage(Languaje.getLangMsg(u.getUserData().getLang(), "Ajustes.cambiado"));
        }
        else u.getPlayer().sendMessage(Utils.colorize("&c&lServer> &fThe target is null"));
    }

    private String getRandomName(List<String> names){
        if (names.size() == 0){
            return null;
        }

        final String randomName = names.get(new Random().nextInt(names.size()));

        if (Disguise.getDisguises().containsKey(randomName)){
            names.remove(randomName);
            return getRandomName(names);
        }
        return randomName;
    }
}
