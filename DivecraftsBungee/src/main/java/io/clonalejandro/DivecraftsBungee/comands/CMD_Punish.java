package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.systems.punish.PunishManager;
import io.clonalejandro.DivecraftsBungee.systems.punish.Punishment;
import io.clonalejandro.DivecraftsBungee.systems.punish.PunishmentType;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class CMD_Punish extends Command {

    public CMD_Punish() {
        super("mp", null, "punish", "castigo");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            TextUtils.consoleMSG("Solo los jugadores pueden ejecutar este comando.", MessageType.ERROR);
            return;
        }

        ProxiedPlayer playerSender = (ProxiedPlayer) sender;
        PunishManager pm = new PunishManager();

        if (args.length == 0) {
            if (!playerSender.hasPermission("bungee.staff")){
                TextUtils.playerMSG(playerSender, "No tienes permisos para este comando", MessageType.ERROR);
                return;
            }
            TextUtils.playerMSG(playerSender, "Uso: /mp", MessageType.SUCCESS);
            return;
        }

        if (args.length == 2){
            if (args[0].equalsIgnoreCase("clear")){
                try {
                    int id = Integer.parseInt(args[1]);

                    pm.delete(id);
                } catch (NumberFormatException e){
                    return;
                }
            }

            ProxiedPlayer sancionado = Main.getMB().getProxy().getPlayer(args[0]);
            if (args[1].equalsIgnoreCase("ver")){
                try {
                    pm.seeHistory(sancionado, playerSender);
                } catch (SQLException e){
                    TextUtils.playerMSG(playerSender, "Ha habido un error al mirar el historial de " + sancionado.getName(), MessageType.ERROR);
                }
                return;
            }
            if (args[1].equalsIgnoreCase("unban")){
                try {
                    pm.unSomething(sancionado, PunishmentType.BAN);
                } catch (SQLException e){
                    TextUtils.playerMSG(playerSender, "Ha habido un error al borrar el ban de " + sancionado.getName(), MessageType.ERROR);
                }
                return;
            }
            if (args[1].equalsIgnoreCase("unmute")){
                try {
                    pm.unSomething(sancionado, PunishmentType.MUTE);
                } catch (SQLException e){
                    TextUtils.playerMSG(playerSender, "Ha habido un error al borrar el mute de " + sancionado.getName(), MessageType.ERROR);
                }
                return;
            }
            Punishment p = new Punishment(sancionado, playerSender, args);

            p.create();
        }

        if (args.length >= 3){
            ProxiedPlayer sancionado = Main.getMB().getProxy().getPlayer(args[0]);
            if (args[1].equalsIgnoreCase("warn")){
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]);
                    if (i < args.length) {
                        sb.append(" ");
                    }
                }
                new Punishment(sancionado, playerSender, new String[]{sb.toString(), "0s", PunishmentType.WARNING.getName()}).create();
            }
        }
    }
}
