package io.clonalejandro.DivecraftsCore.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemMaker {

    private ItemStack itemStack;

    public ItemMaker(Material m) {
        this(m, 1);
    }
    public ItemMaker(Material m, int amount) {
        this(m, amount, (short)0);
    }
    public ItemMaker(Material m, int amount, short data) {
        itemStack = new ItemStack(m, amount, data);
    }

    public ItemMaker setName(String name) {
        ItemMeta im = getItemMeta();
        im.setDisplayName(Utils.colorize(name));
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);

        setItemMeta(im);
        return this;
    }

    public ItemMaker setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemMaker setLore(List<String> lore) {
        ItemMeta im = getItemMeta();
        List<String> tempLore = new ArrayList<>();

        lore.forEach(l -> tempLore.add(Utils.colorize(l)));

        im.setLore(tempLore);
        setItemMeta(im);
        return this;
    }

    public ItemMaker addEnchant(Enchantment ench, int level) {
        itemStack.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemMaker addEnchants(Enchantment... enchantments) {
        Map<Enchantment, Integer> enchants = new HashMap<>();
        Arrays.asList(enchantments).forEach(e -> enchants.put(e, 1));
        return addEnchants(enchants);
    }
    public ItemMaker addEnchants(Map<Enchantment, Integer> enchants) {
        itemStack.addUnsafeEnchantments(enchants);
        return this;
    }

    public ItemMaker addItemFlags(ItemFlag... itemFlags) {
        ItemMeta im = getItemMeta();
        im.addItemFlags(itemFlags);
        setItemMeta(im);
        return this;
    }

    public ItemMaker setEnchanted() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemStack buildComplete() {
        addItemFlags(ItemFlag.values());
        return itemStack;
    }

    private ItemMeta getItemMeta() {
        return itemStack.getItemMeta();
    }
    private void setItemMeta(ItemMeta im) {
        itemStack.setItemMeta(im);
    }
}
