package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Alex
 * On 02/05/2020
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
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class SignHandler implements Listener {

    @EventHandler
    public void onSign(SignChangeEvent event){
        if (checkPermissions(event.getPlayer(), SCmd.Rank.NEMO)) return;

        for(int i = 0; i < 4; i++){
            event.setLine(i, Main.translate(event.getLine(i)));
        }
    }


    public boolean checkPermissions(Player player, SCmd.Rank rank){
        final SUser user = SServer.getUser(player.getUniqueId());

        if (user.getUserData().getRank().getRank() < rank.getRank())
            return sendErrMessage(player);

        return false;
    }

    private boolean sendErrMessage(Player player){
        player.sendMessage(Utils.colorize("&c&lServer> &fNo tienes permisos para hacer eso!"));
        return true;
    }
}
