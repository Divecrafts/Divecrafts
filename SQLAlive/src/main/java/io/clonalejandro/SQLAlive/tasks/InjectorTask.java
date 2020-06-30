package io.clonalejandro.SQLAlive.tasks;

import io.clonalejandro.SQLAlive.SQLAlive;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;

/**
 * Created by Alex
 * On 24/06/2020
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
 * All rights reserved for clonalejandro ©SQLAlive 2017/2020
 */

public class InjectorTask extends BukkitRunnable {

    private int time = 30;

    @Override
    public void run() {
        if (time != 0){
            time--;
        }
        else {
            time = 30;//Reset counter
            try {
                final PreparedStatement statement = SQLAlive.getConnection().prepareStatement("SELECT 1=1");
                statement.executeQuery();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Bukkit.getScheduler().runTask(SQLAlive.getPlugin(), () -> Bukkit.getServer().shutdown());
            }
        }
    }

}
