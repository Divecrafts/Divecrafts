package io.clonalejandro.DivecraftsCore.cmd;


import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Utils;

public class HelpOPCMD extends SCmd {


    public HelpOPCMD() {
        super("helpop", Rank.USUARIO, "hp");
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        String message = Utils.buildString(args);
        hp(user, message);
        user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "HelpOP.astaff"));
    }

    private void hp(SUser user, String msg) {
        plugin.getServer().getOnlinePlayers().forEach(p -> {
            SUser u = SServer.getUser(p);
            if (u.isOnRank(Rank.TMOD)) {
                u.getPlayer().sendMessage(Utils.colorize("&c&lHELPOP> &e" + user.getName() + "&r: " + msg));
            }
        });
    }
}
