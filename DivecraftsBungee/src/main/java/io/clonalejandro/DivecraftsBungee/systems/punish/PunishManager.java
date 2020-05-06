package io.clonalejandro.DivecraftsBungee.systems.punish;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.utils.MySQL;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex
 * On 04/05/2020
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
 * All rights reserved for clonalejandro ©DivecraftsBungee 2017/2020
 */

public class PunishManager {

    private MySQL mySQL = Main.getMySQL();

    public void seeHistory(ProxiedPlayer p, ProxiedPlayer asker) throws SQLException {
        if (!hasHistory(p)){
            TextUtils.playerMSG(asker, "Este usuario no tiene historial de sanciones", MessageType.SUCCESS);
            return;
        }
        ResultSet rs = mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'");

        if (rs.next()){
            TextUtils.playerMSG(asker, rs.getInt("id") + " " + p.getName(), MessageType.SUCCESS);
        }
    }

    public void delete(int id){
        mySQL.actualizarQuery("DELETE FROM `punish` WHERE `id`='" + id + "'");
    }

    public void unSomething(ProxiedPlayer p, PunishmentType pt) throws SQLException {
        ResultSet rs = mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'");

        if (rs.next()){

            switch (rs.getString("motivo").toLowerCase()){
                case "ban":
                    if (pt == PunishmentType.BAN) new Punishment(p).actualizar(rs.getInt("id"));
                    break;
                case "mute":
                    if (pt == PunishmentType.MUTE) new Punishment(p).actualizar(rs.getInt("id"));
            }
        }
    }

    public boolean isBanned(ProxiedPlayer p) throws SQLException {
        ResultSet rs = mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'");

        if (!rs.getString("motivo").equalsIgnoreCase(PunishmentType.BAN.getName())) return false;

        shouldBeActive(p);
        return rs.getInt("activo") == 1;
    }

    public boolean isMuted(ProxiedPlayer p) throws SQLException {
        ResultSet rs = mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'");

        if (!rs.getString("motivo").equalsIgnoreCase(PunishmentType.MUTE.getName())) return false;

        shouldBeActive(p);
        return rs.getInt("activo") == 1;
    }

    public String getReason(ProxiedPlayer p) throws SQLException {
        ResultSet rs = mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'");
        return ChatColor.translateAlternateColorCodes('&', rs.getString("razon"));
    }




    private boolean hasHistory(ProxiedPlayer p){
        return mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'") != null; //True = tiene sanción(es)
    }

    private void shouldBeActive(ProxiedPlayer p) throws SQLException {
        ResultSet rs = mySQL.query("SELECT `*` FROM `punish` WHERE `sancionado` = '" + p.getUniqueId() + "'");

        long init = rs.getLong("hora");
        long time = rs.getLong("tiempo");
        long currentTime = System.currentTimeMillis();

        if (init + time >= currentTime) new Punishment(p).actualizar(rs.getInt("id"));
    }
}
