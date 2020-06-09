package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.objects.Warp;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex
 * On 02/06/2020
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

public class WarpHandler implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(Utils.colorize("&a&lWarps"))) {
            ItemStack item = event.getCurrentItem();

             final List<Warp> warps = Warp.warpList.stream()
                     .filter(warp -> warp.getName().equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName())))
                     .collect(Collectors.toList());

             if (warps.size() > 0) {
                 final Player player = (Player) event.getWhoClicked();
                 final Warp warp = warps.get(0);
                 final SUser user = SServer.getUser(player);
                 final Location location = warp.getLocation();

                 player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al warp &e%s%s", warp.getName(), user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : "  &fespere &e5seg")));
                 new TeleportWithDelay(player, location);
             }
            event.getWhoClicked().closeInventory();
        }
    }
}
