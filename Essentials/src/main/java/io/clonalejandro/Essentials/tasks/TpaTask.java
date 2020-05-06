package io.clonalejandro.Essentials.tasks;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.commands.TpaCmd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

public class TpaTask extends BukkitRunnable {
    private int time;
    private final Player target;

    public TpaTask(final int time, final Player target){
        this.time = time;
        this.target = target;
    }

    @Override
    public void run(){
        final Player player = TpaCmd.tpaUsers.get(target);

        if (player == null || target == null){
            super.cancel();
            return;
        }
        if (!target.isOnline() || !player.isOnline()){
            cancel();
            return;
        }
        if (time != 0) time--;
        else cancel();
    }

    @Override
    public void cancel(){
        final Player player = TpaCmd.tpaUsers.get(target);
        if (player != null) {
            if (player.isOnline())
                player.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha rechazado tu peticion", target.getName())));
            if (target.isOnline())
                target.sendMessage(Main.translate("&c&lServer> &fPetición de teletransporte cancelada"));

            TpaCmd.tpaType.remove(target);
            TpaCmd.tpaUsers.remove(target);
        }
        super.cancel();
    }
}
