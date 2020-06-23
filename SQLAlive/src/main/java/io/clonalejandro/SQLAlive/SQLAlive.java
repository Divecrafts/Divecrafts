package io.clonalejandro.SQLAlive;

import io.clonalejandro.SQLAlive.events.PlayerEvents;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Connection;

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

public class SQLAlive {


    /** SMALL CONSTRUCTORS **/

    @Getter private static Plugin plugin;
    @Getter private static Connection connection;
    @Getter @Setter private static BukkitTask task = null;

    public SQLAlive(Plugin plugin, Connection connection){
        SQLAlive.plugin = plugin;
        SQLAlive.connection = connection;

        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
    }


}
