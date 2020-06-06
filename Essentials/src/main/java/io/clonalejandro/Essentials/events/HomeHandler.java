package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.objects.Home;
import io.clonalejandro.Essentials.utils.MysqlManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex
 * On 06/06/2020
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

public class HomeHandler implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();

        if (Home.homes.containsKey(player))
            Home.homes.remove(player);

        Home.homes.put(player, getHomes(player));
    }

    public List<Home> getHomes(Player player){
        final String query = String.format("SELECT * FROM Homes WHERE uuid=\"%s\"", player.getUniqueId().toString());

        try {
            final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);
            final List<Home> homes = new ArrayList<>();

            while (result.next())
                homes.add(new Home(
                        result.getString("name"),
                        result.getString("world"),
                        result.getDouble("x"),
                        result.getDouble("y"),
                        result.getDouble("z"),
                        result.getFloat("yaw"),
                        result.getFloat("pitch")
                ));
            return homes;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            player.sendMessage(Main.translate("&c&lServer> &falgo salió mal puede que no tengas homes guardados"));
        }

        return null;
    }
}
