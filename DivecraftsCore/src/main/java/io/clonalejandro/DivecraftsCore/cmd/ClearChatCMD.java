package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Alex
 * On 15/07/2020
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

public class ClearChatCMD extends SCmd {

    public ClearChatCMD() {
        super("clearchat", Rank.TMOD, "cc");
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            for (int i = 0; i < 100; i++)
                p.sendMessage(" ");
        });
        user.getPlayer().sendMessage("§cChat eliminado para todos");//TODO translate
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
