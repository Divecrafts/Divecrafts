package io.clonalejandro.DivecraftsBungee.systems.punish;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.utils.MySQL;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import io.clonalejandro.DivecraftsBungee.utils.TimeUtils;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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

public class Punishment {
    @Getter private ProxiedPlayer sancionado;
    @Getter private ProxiedPlayer operator;
    @Getter private char code;
    @Getter private String time;
    @Getter private String reason;
    @Getter private PunishmentType punishmentType;

    private MySQL mySQL = Main.getMySQL();

    public Punishment(ProxiedPlayer sancionado){ //para actualizar()
        this.sancionado = sancionado;
    }

    public Punishment(ProxiedPlayer sancionado, ProxiedPlayer operator, String... args){ //Para crear()
        this.sancionado = sancionado;
        this.operator = operator;
        this.code = args[0].charAt(0);

        if (args[1] == null) return;
        this.reason = ChatColor.translateAlternateColorCodes('&', args[1]);
        this.time = args[2];
        this.punishmentType = PunishmentType.getPunishType(args[3]);
    }

    public void create(){
        int active = 0;
        int timeInMilliSeconds = parseTime();
        PunishmentType.PunishCode why = PunishmentType.PunishCode.getPunish(code);
        PunishmentType pt = punishmentType;
        if (why != PunishmentType.PunishCode.LIBRE) pt = why.getPunish();

        if (!operator.hasPermission(pt.getPerms())){
            TextUtils.playerMSG(operator, "No tienes permisos para hacer esto", MessageType.ERROR);
            return;
        }

        if (pt == PunishmentType.BAN || pt == PunishmentType.MUTE) active = 1;  //Estados: 0 acabado, 1 activo

        try {
            mySQL.actualizarQueryErr("INSERT INTO punish (sancionado, operador, motivo, hora, tiempo, razon, activo) VALUES ('" + sancionado.getUniqueId() + "','" + operator.getName() + "','" + pt.getName() + "','" + System.currentTimeMillis() + "','" + timeInMilliSeconds + "','" + reason + "','" + active + "')");
            TextUtils.playerMSG(operator, sancionado.getName() + " " + pt.getName() + " " + time + " " + reason, MessageType.SUCCESS);
            sancionado.disconnect(new TextComponent(formatText(why)));
        } catch (SQLException e){
            TextUtils.playerMSG(operator, "Ha ocurrido un error al castigar a este usuario", MessageType.ERROR);
        }
    }

    public void actualizar(int id) {
        mySQL.actualizarQuery("UPDATE `punish` SET `activo`=" + 0 + " WHERE `id`='" + id + "'");
    }





    /*
     * Utils
     * */
    private String formatText(PunishmentType.PunishCode why){
        String text = "&c&lMinley> &e" + punishmentType.getName() + " &fpor &e" +  time + " &frazón: &e" + why.getName() + " \n&a&lPuedes apelar tu sanción en nuestra página Web &a&lhttps://minley.es";
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private int parseTime(){
        int n = Integer.parseInt(time.replaceAll("\\D+", ""));

        return TimeUtils.getTimeInMilliSeconds(n, TimeUtils.parseData(time.charAt(1)));
    }
}
