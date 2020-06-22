package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Sounds;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex
 * On 03/06/2020
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

public class ResponderCMD extends SCmd {

    public ResponderCMD() {
        super("responder", Rank.USUARIO, Arrays.asList("r"));
    }

    private static void sendPrivateMessage(SUser target, SUser from, String mensaje) {
        target.getPlayer().sendMessage(Languaje.getLangMsg(target.getUserData().getLang(), "Msg.desde").replace("%player%", from.getName()) + mensaje);
        from.getPlayer().sendMessage(Languaje.getLangMsg(from.getUserData().getLang(), "Msg.para").replace("%player%", target.getName()) + mensaje);
        target.sendSound(Sounds.LEVEL_UP);
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        if (args.length == 0){
            return;
        }

        if (DecirCMD.getLastPlayerMsg().get(user.getPlayer()) == null){
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.noconectado"));
            return;
        }

        final SUser target = SServer.getUser(DecirCMD.getLastPlayerMsg().get(user.getPlayer()));

        if (user == target) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Msg.noatimismo"));
            return;
        }
        if (target == null || !target.isOnline()) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.noconectado"));
            return;
        }

        if (DecirCMD.getLastPlayerMsg().get(target.getPlayer()) == null)
            DecirCMD.getLastPlayerMsg().remove(target.getPlayer());
        DecirCMD.getLastPlayerMsg().put(target.getPlayer(), user.getPlayer());

        sendPrivateMessage(target, user, user.getUserData().getRank().getRank() > 0 ? Utils.colorize(Utils.buildString(args)) : Utils.buildString(args));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
