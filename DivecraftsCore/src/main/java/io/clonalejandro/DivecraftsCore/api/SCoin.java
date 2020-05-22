package io.clonalejandro.DivecraftsCore.api;

import io.clonalejandro.DivecraftsCore.Main;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

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

public class SCoin {

    private static final Main plugin = Main.getInstance();
    @Getter SUser user;

    public SCoin(SUser user){
        this.user = user;
    }

    public SCoin(UUID id){
        this.user = SServer.getUser(id);
    }

    public SCoin(Player player){
        this.user = SServer.getUser(player.getUniqueId());
    }


    public static HashMap<SUser, SCoin> balanceTop(int limit){
        final HashMap<SUser, SCoin> top = new HashMap<>();

        try {
            final PreparedStatement statement = plugin.getMySQL().openConnection().prepareStatement(String.format("SELECT `uuid` FROM data order by coins desc limit %s", limit));
            final ResultSet rs = statement.executeQuery();

            while (rs.next()){
                final SUser user = SServer.getUser(UUID.fromString(rs.getString("uuid")));
                final SCoin coins = new SCoin(user);
                top.put(user, coins);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return top;
    }


    public int withdraw(int coins){
        final SUser.UserData data = user.getUserData();
        data.setCoins(data.getCoins() - coins);
        return data.getCoins();
    }

    public int deposit(int coins){
        final SUser.UserData data = user.getUserData();
        data.setCoins(data.getCoins() + coins);
        return data.getCoins();
    }

    public int balance(){
        return user.getUserData().getCoins();
    }
}
