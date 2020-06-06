package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.objects.Warp;
import io.clonalejandro.Essentials.utils.MysqlManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

            try {
                final PreparedStatement statement = MysqlManager.getConnection().prepareStatement("SELECT * FROM Warps WHERE name=?");
                statement.setString(1, ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                final ResultSet rs = statement.executeQuery();

                rs.next();
                event.getWhoClicked().teleport(new Warp(rs).getLocation());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            event.getWhoClicked().closeInventory();
        }
    }
}
