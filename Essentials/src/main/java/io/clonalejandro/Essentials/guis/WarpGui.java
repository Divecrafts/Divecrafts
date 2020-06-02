package io.clonalejandro.Essentials.guis;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

public class WarpGui {

    private final List<Warp> warps;
    private final Inventory inventory;
    private final List<ItemStack> items = new ArrayList<>();

    public WarpGui(List<Warp> warps, Player player){
        this.warps = warps;
        this.inventory = Bukkit.createInventory(player, 27, Utils.colorize("&a&lWarps"));
        this.init();

        items.forEach(inventory::addItem);

        player.openInventory(this.inventory);
    }

    public void init(){
        this.warps.forEach(warp -> {
            switch (warp.getName().toLowerCase()){
                default:
                    items.add(itemMaker(warp, Material.STONE));
                    break;
                case "eventos":
                    items.add(itemMaker(warp, Material.CAKE));
                    break;
                case "biblioteca":
                    items.add(itemMaker(warp, Material.BOOKSHELF));
                    break;
                case "mina":
                    items.add(itemMaker(warp, Material.DIAMOND_PICKAXE));
                    break;
                case "carrera":
                    items.add(itemMaker(warp, Material.SADDLE));
                    break;
                case "survival":
                    items.add(itemMaker(warp, Material.GRASS_BLOCK));
                    break;
                case "towny":
                    items.add(itemMaker(warp, Material.BRICKS));
                    break;
                case "tienda":
                    items.add(itemMaker(warp, Material.EMERALD));
                    break;
                case "bosque":
                    items.add(itemMaker(warp, Material.OAK_SAPLING));
                    break;
                case "tutoriales":
                    items.add(itemMaker(warp, Material.BOOK));
                    break;
                case "parkour":
                    items.add(itemMaker(warp, Material.IRON_BOOTS));
                    break;
                case "parkour2":
                    items.add(itemMaker(warp, Material.DIAMOND_BOOTS));
                    break;
                case "mercado":
                    items.add(itemMaker(warp, Material.OAK_SIGN));
                    break;
                case "crates":
                    items.add(itemMaker(warp, Material.TRIPWIRE_HOOK));
                    break;
                case "pvp":
                    items.add(itemMaker(warp, Material.IRON_SWORD));
                    break;
                case "pvp2":
                    items.add(itemMaker(warp, Material.DIAMOND_SWORD));
                    break;
            }
        });
    }

    private ItemStack itemMaker(Warp warp, Material material){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.colorize("&a" + warp.getName()));
        item.setItemMeta(meta);

        return item;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
