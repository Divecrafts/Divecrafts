package io.clonalejandro.DivecraftsCore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Hologramas {

    public static ArrayList<org.bukkit.entity.Entity> hologramas = new ArrayList<>();

    public static void crearHolo(String msg, Location l, String worldName){
        try {
            ArmorStand e = Bukkit.getWorld(worldName).spawn(l, ArmorStand.class);
            e.setVisible(false);

            if (ReflectionAPI.getVersion().replaceAll("_", ".").contains("1.15")){
                e.setCustomNameVisible(true);
                e.setCustomName(Utils.colorize(msg));
                hologramas.add(e);
                return;
            }

            final Object entity = ReflectionAPI.getHandle(e);
            Object tag = entity.getClass().getMethod("getNBTTag").invoke(entity);

            if (tag == null){
                tag = ReflectionAPI.getNmsClass("NBTTagCompound").newInstance();
            }

            entity.getClass().getMethod("c", tag.getClass()).invoke(entity, tag);

            final Method setInt = tag.getClass().getMethod("setInt", String.class, int.class);
            final Method setString = tag.getClass().getMethod("setString", String.class, String.class);

            setString.invoke(tag, "CustomName", Utils.colorize(msg));

            setInt.invoke(tag, "Invisible", 1);
            setInt.invoke(tag, "NoGravity", 1);
            setInt.invoke(tag, "Marker", 1);
            setInt.invoke(tag, "CustomNameVisible", 1);

            entity.getClass().getMethod("f", tag.getClass()).invoke(entity, tag);
            hologramas.add(e);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
