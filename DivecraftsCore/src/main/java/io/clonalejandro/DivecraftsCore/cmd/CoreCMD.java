package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;

public class CoreCMD extends SCmd {

    public CoreCMD() {
        super("Server", Rank.USUARIO, "core");
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length == 1) {
            if (user.getUserData().getRank().getRank() < Rank.DEV.getRank()) {
                def(user);
                return;
            }

            switch (args[0].toLowerCase()) {
                case "reload":
                    reloadConfig(user);
                    break;
            }
            return;
        }
        def(user);
    }

    private void def(SUser user) {
        user.getPlayer().sendMessage(Main.getPREFIX() + "§cServer " + "§7v" + plugin.getDescription().getVersion());
    }

    private void reloadConfig(SUser user) {
        plugin.reloadConfig();
        user.getPlayer().sendMessage("§eConfiguración recargada");
    }
}
