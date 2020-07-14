package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import java.util.Arrays;

public class CoreCMD extends SCmd {

    public CoreCMD() {
        super("Server", Rank.USUARIO, Arrays.asList("core", "dcore"));
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length == 1) {
            if (user.getUserData().getRank().getRank() < Rank.DEV.getRank()) {
                def(user);
                return;
            }

            if ("reload".equals(args[0].toLowerCase())) {
                reloadConfig(user);
            }
            return;
        }
        def(user);
    }

    private void def(SUser user) {
        user.getPlayer().sendMessage(Utils.colorize(Main.getPREFIX() + "&cServer " + "&7v" + plugin.getDescription().getVersion()));
    }

    private void reloadConfig(SUser user) {
        plugin.reloadConfig();
        user.getPlayer().sendMessage("§eConfiguración recargada");
    }
}
