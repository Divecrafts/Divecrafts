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
                menu.setItem(1, new ItemMaker(Material.DIAMOND_SWORD).setName("&aDificultad").buildComplete());//TODO
                menu.setItem(4, new ItemMaker(Material.WATCH).setName("&aHorario").build());//TODO
                menu.setItem(7, new ItemMaker(Material.DOUBLE_PLANT).setName("&aClima").build());//TODO
                break;
            case DIFF:
                menu.setItem(1, new ItemMaker(Material.WOOD_SWORD).setName("&aFacil").buildComplete());//TODO
                menu.setItem(4, new ItemMaker(Material.GOLD_SWORD).setName("&eMedio").build());//TODO
                menu.setItem(7, new ItemMaker(Material.DIAMOND_SWORD).setName("&cDificil").build());//TODO
                break;
            case TIME:
                menu.setItem(1, new ItemMaker(Material.DAYLIGHT_DETECTOR).setName("&aMañana").buildComplete());//TODO
                menu.setItem(4, new ItemMaker(Material.DAYLIGHT_DETECTOR_INVERTED).setName("&eTarde").build());//TODO
                menu.setItem(7, new ItemMaker(Material.OBSIDIAN).setName("&bNoche").build());//TODO
                break;
            case WHEATER:
                menu.setItem(2, new ItemMaker(Material.DOUBLE_PLANT).setName("&eSoleado").buildComplete());//TODO
                menu.setItem(6, new ItemMaker(Material.WATER_BUCKET).setName("&bLluvioso").build());//TODO
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
