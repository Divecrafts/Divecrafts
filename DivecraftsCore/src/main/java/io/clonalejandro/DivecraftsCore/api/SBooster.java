package io.clonalejandro.DivecraftsCore.api;

import io.clonalejandro.DivecraftsCore.Main;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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

public class SBooster {

    private static final Main plugin = Main.getInstance();

    @Getter private final int id, multiplier;
    @Getter private final SServer.GameID gameID;
    @Getter private final Timestamp expires;
    @Getter private final UUID uuid;

    public SBooster(int id, int multiplier, SServer.GameID gameID, Timestamp expires, UUID uuid){
        this.id = id;
        this.multiplier = multiplier;
        this.gameID = gameID;
        this.expires = expires;
        this.uuid = uuid;
    }

    public boolean isExpired(){
        final boolean isExpired = expires.compareTo(new Date()) < 0;
        if (isExpired) destroy();
        return isExpired;
    }

    private void destroy(){
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                final PreparedStatement statement = plugin.getMySQL().openConnection().prepareStatement("DELETE FROM booster where `id` = ?");

                statement.setInt(1, id);
                statement.executeUpdate();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
