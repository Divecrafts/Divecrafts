package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.objects.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alex
 * On 01/06/2020
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

public class PermissionHandler implements Listener {

    public HashMap<Player, PermissionAttachment> perms = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final List<String> permissions = new Permission(event.getPlayer().getUniqueId()).get();
        final PermissionAttachment attachment = player.addAttachment(Main.instance);

        permissions.forEach(permission -> attachment.setPermission(permission, true));
        perms.put(player, attachment);
    }
}
