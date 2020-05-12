package io.clonalejandro.DivecraftsCore.utils;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;

public class Hologramas {

    public static ArrayList<org.bukkit.entity.Entity> hologramas = new ArrayList<>();

    public static void crearHolo(String msg, Location l, String worldName){
        org.bukkit.entity.Entity e = Bukkit.getWorld(worldName).spawn(l, ArmorStand.class);
        ((ArmorStand) e).setVisible(false);
        Entity h = ((CraftEntity)e).getHandle();
        NBTTagCompound tag = h.getNBTTag();
        if (tag==null){
            tag = new NBTTagCompound();
        }
        h.c(tag);
        tag.setInt("Invisible", 1);
        tag.setString("CustomName",Utils.colorize(msg));
        tag.setInt("NoGravity",1);
        tag.setInt("Marker",1);
        tag.setInt("CustomNameVisible",1);
        h.f(tag);
        hologramas.add(e);
    }

}
