package io.clonalejandro.DivecraftsLobby.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemUtil {

    public static ItemStack createItem(String nombre, Material material, int d, String... lore){
        return createItem(nombre, material, d, Arrays.asList(lore));
    }

    public static ItemStack createClay(String displayname, DyeColor dye) {
        return createClay(displayname, null, dye);
    }

    public static ItemStack createClay(String displayname, List<String> lore, DyeColor dye) {
        ItemStack item = new ItemStack(Material.STAINED_CLAY, 1, dye.getWoolData());
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
        meta.setDisplayName(Utils.colorize(displayname));
        ArrayList<String> colorLore = new ArrayList<>();
        if (lore != null) {
            lore.forEach(str -> colorLore.add(Utils.colorize(str)));
            meta.setLore(colorLore);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createHeadPlayer(String displayname, String username, List<String> lore) {
        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta)playerHead.getItemMeta();
        sm.setOwner(username);
        ArrayList<String> colorLore = new ArrayList<>();
        if (lore != null) {
            lore.forEach(str -> colorLore.add(Utils.colorize(str)));
            sm.setLore(colorLore);
        }

        sm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);
        sm.setDisplayName(Utils.colorize(displayname));
        playerHead.setItemMeta(sm);
        return playerHead;
    }

    public static ItemStack createItem(String nombre, Material material, int d, List<String> lore){
        ItemStack i = new ItemStack(material, 1 ,(byte)d);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.colorize(nombre));
        im.setLore(lore);
        im.spigot().setUnbreakable(true);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        return i;
    }

    public ItemStack createEnchantedItem(String nombre, Material material, int d, List<String> lore){
        ItemStack i = new ItemStack(material, 1 ,(byte)d);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(nombre);
        im.setLore(lore);
        im.spigot().setUnbreakable(true);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack createSkull(String displayName, String owner, List<String> lores){
        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1 ,(byte)3);
        SkullMeta im = (SkullMeta) i.getItemMeta();
        im.setDisplayName(Utils.colorize(displayName));
        im.setOwner(owner);
        im.setLore(lores);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack createCustomSkull(String url, String displayname, List<String> lore) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if(url.isEmpty())return head;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setLore(lore);
        headMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack createItem(Material material, String displayname) {
        return createItem(material, 1, displayname, (List<String>) null);
    }

    public static ItemStack createItem(Material material, String displayname, String lore) {
        return createItem(material, 1, displayname, Arrays.asList(lore));
    }

    public static ItemStack createItem(Material material, int amount, String displayname, String lore) {
        return createItem(material, amount, displayname, Arrays.asList(lore));
    }

    public static ItemStack createItem(Material material, String displayname, List<String> lore) {
        return createItem(material, 1, displayname, lore);
    }

    public static ItemStack createItem(Material material, int amount, String displayname, List<String> lore) {
        return createItem(material, amount, (short)0, displayname, lore);
    }

    public static ItemStack createItem(Material material, int amount, short data, String displayname, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorize(displayname));
        ArrayList<String> colorLore = new ArrayList<>();
        if (lore != null) {
            lore.forEach(str -> colorLore.add(Utils.colorize(str)));
            meta.setLore(colorLore);
        }

        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        return item;
    }

}
