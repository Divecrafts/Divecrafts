package net.divecrafts.skywars.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import io.clonalejandro.DivecraftsCore.utils.ItemMaker;

import net.divecrafts.skywars.SkyWars;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class Menus {

    private final SkyWars plugin;

    public Inventory createMenu(MenuType type) {
        Inventory menu = plugin.getServer().createInventory(null, 9, type.getInvName());

        switch (type) {
            case MENU:
                menu.setItem(1, new ItemMaker(Material.DIAMOND_SWORD).setName("&aDificultad").buildComplete());
                menu.setItem(4, new ItemMaker(Material.WATCH).setName("&aHorario").build());
                menu.setItem(7, new ItemMaker(Material.DOUBLE_PLANT).setName("&aClima").build());
                break;
            case DIFF:
                menu.setItem(1, new ItemMaker(Material.WOOD_SWORD).setName("&aFacil").buildComplete());
                menu.setItem(4, new ItemMaker(Material.GOLD_SWORD).setName("&eMedio").build());
                menu.setItem(7, new ItemMaker(Material.DIAMOND_SWORD).setName("&cDificil").build());
                break;
            case TIME:
                menu.setItem(1, new ItemMaker(Material.DAYLIGHT_DETECTOR).setName("&aMañana").buildComplete());
                menu.setItem(4, new ItemMaker(Material.DAYLIGHT_DETECTOR_INVERTED).setName("&eTarde").build());
                menu.setItem(7, new ItemMaker(Material.OBSIDIAN).setName("&bNoche").build());
                break;
            case WHEATER:
                menu.setItem(2, new ItemMaker(Material.DOUBLE_PLANT).setName("&eSoleado").buildComplete());
                menu.setItem(6, new ItemMaker(Material.WATER_BUCKET).setName("&bLluvioso").build());
                break;
        }

        return menu;
    }

    @AllArgsConstructor
    public enum MenuType {
        MENU("Menu de Skywars"),
        DIFF("Dificultad"),
        TIME("Horario"),
        WHEATER("Clima");


        @Getter private final String invName;
    }
}
