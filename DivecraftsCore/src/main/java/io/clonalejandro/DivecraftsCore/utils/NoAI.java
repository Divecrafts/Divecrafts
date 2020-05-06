package io.clonalejandro.DivecraftsCore.utils;

import io.clonalejandro.DivecraftsCore.Main;
import org.bukkit.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NoAI {

    private static String serverVersion;
    private static Method getHandle;
    private static Method getNBTTag;
    private static Class<?> nmsEntityClass;
    private static Class<?> nbtTagClass;
    private static Method c;
    private static Method setInt;
    private static Method f;

    public static void setAIEnabled(Entity entity, boolean enabled) {
        try {
            if (serverVersion == null) {
                String name = Main.getInstance().getServer().getClass().getName();
                String[] parts = name.split("\\.");
                serverVersion = parts[3];
            }
            if (getHandle == null) {
                Class<?> craftEntity = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftEntity");
                getHandle = craftEntity.getDeclaredMethod("getHandle");
                getHandle.setAccessible(true);
            }
            Object nmsEntity = getHandle.invoke(entity);
            if (nmsEntityClass == null) {
                nmsEntityClass = Class.forName("net.minecraft.server." + serverVersion + ".Entity");
            }
            if (getNBTTag == null) {
                getNBTTag = nmsEntityClass.getDeclaredMethod("getNBTTag");
                getNBTTag.setAccessible(true);
            }
            Object tag = getNBTTag.invoke(nmsEntity);
            if (nbtTagClass == null) {
                nbtTagClass = Class.forName("net.minecraft.server." + serverVersion + ".NBTTagCompound");
            }
            if (tag == null) {
                tag = nbtTagClass.newInstance();
            }
            if (c == null) {
                c = nmsEntityClass.getDeclaredMethod("c", nbtTagClass);
                c.setAccessible(true);
            }
            c.invoke(nmsEntity, tag);
            if (setInt == null) {
                setInt = nbtTagClass.getDeclaredMethod("setInt", String.class, Integer.TYPE);
                setInt.setAccessible(true);
            }
            int value = enabled ? 0 : 1;
            setInt.invoke(tag, "NoAI", value);
            if (f == null) {
                f = nmsEntityClass.getDeclaredMethod("f", nbtTagClass);
                f.setAccessible(true);
            }
            f.invoke(nmsEntity, tag);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
