package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class KillCMD extends SCmd {

    public KillCMD() {
        super("kill", Rank.ADMIN, "matar");
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length == 0) {
            user.getPlayer().damage(user.getPlayer().getHealth());
        }
        if (args.length == 1) {
            SUser target = SServer.getUser(plugin.getServer().getPlayer(args[0]));

            if (target == null || !target.isOnline()) {
                userNotOnline(user);
                return;
            }
            target.getPlayer().damage(target.getPlayer().getHealth());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
