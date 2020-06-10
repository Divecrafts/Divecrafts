package io.clonalejandro.Essentials.tasks;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.utils.BungeeMensager;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Alex
 * On 04/06/2020
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

public class AutoRestart extends BukkitRunnable {

    private int time = 60 * 60 * 12;

    @Override
    public void run(){
        if (time != 0){
            if (time == 60 * 5 || time == 60)
                Bukkit.broadcastMessage(Utils.colorize(String.format("&9&lServer> &fEl servidor se reiniciará en &e%smin", time / 60)));
            time--;
        }
        else {
            time = 60 * 60 * 12;
            Bukkit.broadcastMessage(Utils.colorize("&c&lServer> &fReiniciando..."));
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer(""));
                Bukkit.getServer().shutdown();
            }, 20L);
        }
    }
}
