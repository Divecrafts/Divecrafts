package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Sounds;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class DecirCMD extends SCmd {

    public DecirCMD() {
        super("decir", Rank.USUARIO, Arrays.asList("w", "m", "msg", "mensaje"));
    }

    private static void sendPrivateMessage(SUser target, SUser from, String mensaje) {
        target.getPlayer().sendMessage(Languaje.getLangMsg(from.getUserData().getLang(), "Msg.desde").replace("%player%", target.getName()) + mensaje);
        from.getPlayer().sendMessage(Languaje.getLangMsg(from.getUserData().getLang(), "Msg.para").replace("%player%", target.getName()) + mensaje);
        target.sendSound(Sounds.LEVEL_UP);
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        SUser target = SServer.getUser(Main.getInstance().getServer().getPlayer(args[0]));

        if (target.equals(user)) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Msg.noatimismo"));
            return;
        }
        if (!target.isOnline()) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.noconectado"));
            return;
        }
        args[0] = "";
        sendPrivateMessage(target, user, Utils.buildString(args));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
