package io.clonalejandro.DivecraftsCore.cmd;


import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Sounds;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;

public class HelpOPCMD extends SCmd {

    public HelpOPCMD() {
        super("helpop", Rank.USUARIO, "hp");
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length == 0){
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "HelpOP.noespecificado"));
            return;
        }

        String message = Utils.buildString(args);
        hp(user, message);
        user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "HelpOP.astaff"));
    }

    private void hp(SUser user, String msg) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            SUser u = SServer.getUser(p);
            if (u.isOnRank(Rank.TMOD)) {
                u.getPlayer().sendMessage(Utils.colorize("&c&lHELPOP> &e" + user.getName() + "&r: " + msg));
                u.sendSound(Sounds.LEVEL_UP);
            }
        });
    }
}
