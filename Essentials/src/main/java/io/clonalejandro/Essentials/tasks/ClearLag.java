package io.clonalejandro.Essentials.tasks;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
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

public class ClearLag extends BukkitRunnable {

    private int countdown = 60 * 15;

    @Override
    public void run() {
        if (countdown != 0){
            if (countdown == 60)
                Bukkit.broadcastMessage(Utils.colorize("&9&lServer> &fEn &e1min &fserán borradas todas las entidades flotantes"));
            countdown--;
        }
        else {
            Bukkit.getWorlds().forEach(world ->
                    world.getEntities()
                            .stream()
                            .filter(entity -> entity instanceof Item)
                            .forEach(Entity::remove)
            );

            Bukkit.broadcastMessage(Utils.colorize("&9&lServer> &fSe han borrado todas las entidades flotantes"));
            countdown = 60 * 15;//reset
        }
    }

}
