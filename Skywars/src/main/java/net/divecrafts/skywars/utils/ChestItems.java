package net.divecrafts.skywars.utils;

import io.clonalejandro.DivecraftsCore.utils.ItemMaker;

import net.divecrafts.skywars.SkyWars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public final class ChestItems {

    private final SkyWars plugin;

    public ChestItems(SkyWars instance) {
        this.plugin = instance;

        addArmadura();
        addArmadurasBasic();
        addArmadurasOP();
        addArmas();
        addArmasBasic();
        addArmasOP();
        addComida();
        addComidaBasic();
        addComida();
        addComidaBasic();
        addComidaOP();
        addItemBasic();
        addItems();
        addItemsOP();
    }

    public void reload() {
        addArmadura();
        addArmadurasBasic();
        addArmadurasOP();
        addArmas();
        addArmasBasic();
        addArmasOP();
        addComida();
        addComidaBasic();
        addComida();
        addComidaBasic();
        addComidaOP();
        addItemBasic();
        addItems();
        addItemsOP();
    }

    public ArrayList<ItemStack> comida = new ArrayList<>();
    public ArrayList<ItemStack> armas = new ArrayList<>();
    public ArrayList<ItemStack> armaduras = new ArrayList<>();
    public ArrayList<ItemStack> items = new ArrayList<>();

    public ArrayList<ItemStack> comida_op = new ArrayList<>();
    public ArrayList<ItemStack> armas_op = new ArrayList<>();
    public ArrayList<ItemStack> armaduras_op = new ArrayList<>();
    public ArrayList<ItemStack> items_op = new ArrayList<>();

    public ArrayList<ItemStack> comida_basic = new ArrayList<>();
    public ArrayList<ItemStack> armas_basic = new ArrayList<>();
    public ArrayList<ItemStack> armaduras_basic = new ArrayList<>();
    public ArrayList<ItemStack> items_basic = new ArrayList<>();

    public void addComidaBasic() {
        comida_basic.clear();
        ItemStack COMIDA1 = new ItemMaker(Material.COOKED_CHICKEN,1).build();
        comida_basic.add(COMIDA1);
        ItemStack COMIDA2 = new ItemMaker(Material.COOKED_FISH,1).build();
        comida_basic.add(COMIDA2);
        ItemStack COMIDA3 = new ItemMaker(Material.COOKED_RABBIT,1).build();
        comida_basic.add(COMIDA3);
    }

    public void addArmadurasBasic() {
        armaduras_basic.clear();
        ItemStack ARMADURA1 = new ItemMaker(Material.LEATHER_HELMET,1).build();
        armaduras_basic.add(ARMADURA1);
        ItemStack ARMADURA2 = new ItemMaker(Material.IRON_LEGGINGS,1).build();
        armaduras_basic.add(ARMADURA2);
        ItemStack ARMADURA3 = new ItemMaker(Material.CHAINMAIL_CHESTPLATE,1).build();
        armaduras_basic.add(ARMADURA3);
        ItemStack ARMADURA4 = new ItemMaker(Material.GOLD_BOOTS,1).build();
        armaduras_basic.add(ARMADURA4);
    }

    public void addItemBasic() {
        items_basic.clear();
        ItemStack MADERA = new ItemMaker(Material.WOOD,1).build();
        items_basic.add(MADERA);
        ItemStack MADERA2 = new ItemMaker(Material.WORKBENCH,1).build();
        items_basic.add(MADERA2);
        ItemStack SNOWBALL = new ItemMaker(Material.SNOW_BALL,1).build();
        items_basic.add(SNOWBALL);
        ItemStack FLOR = new ItemMaker(Material.RED_ROSE).setName(ChatColor.RED + "Rosa").addEnchants(Enchantment.ARROW_DAMAGE).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
        items_basic.add(FLOR);
    }

    public void addArmasBasic() {
        armas_basic.clear();
        ItemStack ESPADA1 = new ItemMaker(Material.STONE_SWORD,1).build();
        armas_basic.add(ESPADA1);
        ItemStack ESPADA2 = new ItemMaker(Material.WOOD_SWORD).addEnchants(Enchantment.DAMAGE_ALL).build();
        armas_basic.add(ESPADA2);
        ItemStack ARCO = new ItemMaker(Material.BOW,1).build();
        armas_basic.add(ARCO);
        ItemStack CANA = new ItemMaker(Material.FISHING_ROD,1).build();
        armas_basic.add(CANA);
    }

    //OP
    public void addArmadurasOP() {
        armaduras_op.clear();
        ItemStack ARMADURA = new ItemMaker(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build();
        armaduras_op.add(ARMADURA);
        ItemStack ARMADURA2 = new ItemMaker(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();
        armaduras_op.add(ARMADURA2);
        ItemStack ARMADURA3 = new ItemMaker(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build();
        armaduras_op.add(ARMADURA3);
        ItemStack ARMADURA4 = new ItemMaker(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();
        armaduras_op.add(ARMADURA4);
        ItemStack ARMADURA5 = new ItemMaker(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build();
        armaduras_op.add(ARMADURA5);
        ItemStack ARMADURA6 = new ItemMaker(Material.GOLD_BOOTS).addEnchant(Enchantment.PROTECTION_FALL, 6).build();
        armaduras_op.add(ARMADURA6);

    }

    public void addComidaOP() {
        comida_op.clear();
        ItemStack MANZANA_OP = new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1);
        comida_op.add(MANZANA_OP);

        ItemStack BEEF_OP = new ItemMaker(Material.COOKED_BEEF, 16).build();
        comida_op.add(BEEF_OP);

        ItemStack MANZANA_OP2 = new ItemMaker(Material.GOLDEN_APPLE, 16).build();
        comida_op.add(MANZANA_OP2);

        ItemStack GALLETA = new ItemMaker(Material.COOKIE,1).addEnchant(Enchantment.DAMAGE_ALL, 6).build();
        comida_op.add(GALLETA);

    }

    public void addArmasOP() {
        armas_op.clear();
        ItemStack ESPADA = new ItemMaker(Material.WOOD_SWORD,1).addEnchant(Enchantment.KNOCKBACK, 2).build();
        armas_op.add(ESPADA);
        ItemStack ESPADA2 = new ItemMaker(Material.DIAMOND_SWORD,1).addEnchant(Enchantment.DAMAGE_ALL, 5).build();
        armas_op.add(ESPADA2);
        ItemStack ESPADA3 = new ItemMaker(Material.IRON_SWORD,1).addEnchant(Enchantment.FIRE_ASPECT, 2).build();
        armas_op.add(ESPADA3);
        ItemStack ARCO = new ItemMaker(Material.BOW,1).addEnchant(Enchantment.ARROW_DAMAGE, 3).build();
        armas_op.add(ARCO);
        ItemStack ARCO2 = new ItemMaker(Material.BOW,1).addEnchant(Enchantment.ARROW_FIRE, 1).build();
        armas_op.add(ARCO2);
        ItemStack ARCO3 = new ItemMaker(Material.BOW,1).addEnchant(Enchantment.ARROW_KNOCKBACK, 4).setName("§3Jodedor de Vídeos").build();
        armas_op.add(ARCO3);

    }

    public void addItemsOP() {
        items_op.clear();
        ItemStack HUEVOS = new ItemMaker(Material.EGG, 64).build();
        items_op.add(HUEVOS);
        ItemStack BOLAS = new ItemMaker(Material.SNOW_BALL, 64).build();
        items_op.add(BOLAS);
        ItemStack ARROWS = new ItemMaker(Material.ARROW, 32).build();
        items_op.add(ARROWS);
        ItemStack BLOQUES = new ItemMaker(Material.STONE, 64).build();
        items_op.add(BLOQUES);
        ItemStack BLOQUES2 = new ItemMaker(Material.WOOD, 64).build();
        items_op.add(BLOQUES2);
        ItemStack XP = new ItemMaker(Material.EXP_BOTTLE, 64).build();
        items_op.add(XP);
        ItemStack LAPIZ = new ItemStack(Material.INK_SACK, 64, (byte) 4);
        items_op.add(LAPIZ);
        ItemStack LAVA = new ItemMaker(Material.LAVA_BUCKET,1).build();
        items_op.add(LAVA);
        ItemStack AGUA = new ItemMaker(Material.WATER_BUCKET,1).build();
        items_op.add(AGUA);
        ItemStack VIDA = new ItemMaker(Material.BLAZE_ROD).setName("Vida").addEnchant(Enchantment.ARROW_DAMAGE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
        armas_basic.add(VIDA);
    }

    //NORMAL
    public void addComida() {
        comida.clear();
        ItemStack COMIDA1 = new ItemMaker(Material.COOKED_BEEF,1).build();
        comida.add(COMIDA1);
        ItemStack COMIDA2 = new ItemMaker(Material.APPLE,1).build();
        comida.add(COMIDA2);
        ItemStack COMIDA3 = new ItemMaker(Material.GOLDEN_CARROT,1).build();
        comida.add(COMIDA3);
        ItemStack COMIDA4 = new ItemMaker(Material.COOKIE,1).setName("§dGalleta de Felipe Fonseca").build();
        comida.add(COMIDA4);
        ItemStack GOLDENAPPLE = new ItemMaker(Material.GOLDEN_APPLE).build();
        comida.add(GOLDENAPPLE);

    }

    public void addArmas() {
        armas.clear();
        ItemStack ESPADA1 = new ItemMaker(Material.WOOD_SWORD,1).addEnchant(Enchantment.DAMAGE_ALL, 1).build();
        armas.add(ESPADA1);
        ItemStack ESPADA2 = new ItemMaker(Material.IRON_SWORD,1).addEnchant(Enchantment.DURABILITY, 3).build();
        armas.add(ESPADA2);
        ItemStack ESPADA3 = new ItemMaker(Material.DIAMOND_SWORD,1).build();
        armas.add(ESPADA3);
        ItemStack ESPADA4 = new ItemMaker(Material.STONE_AXE,1).addEnchant(Enchantment.DAMAGE_ALL, 1).build();
        armas.add(ESPADA4);
        ItemStack ARCO1 = new ItemMaker(Material.BOW,1).build();
        armas.add(ARCO1);
        ItemStack ARCO2 = new ItemMaker(Material.BOW,1).addEnchant(Enchantment.ARROW_DAMAGE, 1).build();
        armas.add(ARCO2);
        ItemStack CANA = new ItemMaker(Material.FISHING_ROD,1).build();
        armas.add(CANA);
        ItemStack PICO = new ItemMaker(Material.DIAMOND_PICKAXE,1).addEnchant(Enchantment.LUCK, 2).build();
        armas.add(PICO);
        ItemStack PICO2 = new ItemMaker(Material.IRON_PICKAXE,1).addEnchant(Enchantment.DAMAGE_ALL, 1).build();
        armas.add(PICO2);
    }

    public void addArmadura() {
        armaduras.clear();
        ItemStack ARMADURA1 = new ItemMaker(Material.IRON_HELMET,1).build();
        ItemStack ARMADURA2 = new ItemMaker(Material.IRON_CHESTPLATE,1).build();
        ItemStack ARMADURA3 = new ItemMaker(Material.IRON_LEGGINGS,1).build();
        ItemStack ARMADURA4 = new ItemMaker(Material.IRON_BOOTS,1).build();
        ItemStack ARMADURA5 = new ItemMaker(Material.GOLD_BOOTS,1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        ItemStack ARMADURA6 = new ItemMaker(Material.DIAMOND_CHESTPLATE,1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        ItemStack ARMADURA7 = new ItemMaker(Material.DIAMOND_BOOTS,1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        ItemStack ARMADURA8 = new ItemMaker(Material.LEATHER_CHESTPLATE,1).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).build();
        ItemStack ARMADURA9 = new ItemMaker(Material.CHAINMAIL_CHESTPLATE,1).build();
        armaduras.add(ARMADURA1);
        armaduras.add(ARMADURA2);
        armaduras.add(ARMADURA3);
        armaduras.add(ARMADURA4);
        armaduras.add(ARMADURA5);
        armaduras.add(ARMADURA6);
        armaduras.add(ARMADURA7);
        armaduras.add(ARMADURA8);
        armaduras.add(ARMADURA9);
    }

    public void addItems() {
        items.clear();
        ItemStack XP = new ItemMaker(Material.EXP_BOTTLE, 8).build();
        items.add(XP);
        ItemStack LAPIZ = new ItemStack(Material.INK_SACK, 3, (byte) 4);
        items.add(LAPIZ);
        ItemStack BLOQUES1 = new ItemMaker(Material.STONE, 32).build();
        items.add(BLOQUES1);
        ItemStack BLOQUES2 = new ItemMaker(Material.WOOD, 32).build();
        items.add(BLOQUES2);
        ItemStack BLOQUES3 = new ItemMaker(Material.LOG, 16).build();
        items.add(BLOQUES3);
        ItemStack FLECHAS = new ItemMaker(Material.ARROW, 16).build();
        items.add(FLECHAS);
        ItemStack SNOWBALL = new ItemMaker(Material.SNOW_BALL, 16).build();
        items.add(SNOWBALL);
        ItemStack LAVA = new ItemMaker(Material.LAVA_BUCKET,1).build();
        items.add(LAVA);
        ItemStack AGUA = new ItemMaker(Material.WATER_BUCKET,1).build();
        items.add(AGUA);
    }
}
