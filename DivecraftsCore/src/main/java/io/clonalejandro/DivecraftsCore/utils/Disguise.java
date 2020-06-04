package io.clonalejandro.DivecraftsCore.utils;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Alex
 * On 23/05/2020
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
 * All rights reserved for clonalejandro ©DivecraftsCore 2017/2020
 */

public class Disguise {

    private final Main plugin = Main.getInstance();
    @Getter private static final HashMap<String, String> disguises = new HashMap<>();

    public Disguise(SUser user, String username){
        disguises.put(username, user.getName());

        final SCmd.Rank rank = SCmd.Rank.USUARIO;
        final String color = "&7";
        final String prefix = rank.getRank() > 0 ? "&" + SCmd.Rank.groupColor(rank) + rank.getPrefix() + " " : "";
        final String name = prefix + color + username;

        user.getPlayer().setPlayerListName(Utils.colorize(name));
        user.getPlayer().setDisplayName(Utils.colorize(name));
        user.getPlayer().setCustomName(Utils.colorize(name));
        user.getPlayer().setCustomNameVisible(true);

        new TagAPI();

        applySkin(user, username);

        user.getUserData().setDisguise(username);
    }


    private void applySkin(SUser u, String skinName){
        try {
            final Class enumPlayerInfoAction = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
            final Class entityHuman = ReflectionAPI.getNmsClass("EntityHuman");

            final Object handle = ReflectionAPI.getHandle(u.getPlayer());
            final Object craftPlayer = ReflectionAPI.getMethod(handle.getClass(), "getBukkitEntity").invoke(handle);
            final Object entityIds = Array.newInstance(int.class, 1);
            final Object entities = Array.newInstance(handle.getClass(), 1);

            final GameProfile profile = (GameProfile) ReflectionAPI.getMethod(craftPlayer.getClass(), "getProfile").invoke(craftPlayer);

            final int entityId = (int) u.getPlayer().getClass().getMethod("getEntityId").invoke(u.getPlayer());

            Array.set(entityIds, 0, entityId);
            Array.set(entities, 0, handle);

            final Object packetPlayOutEntityDestroy = ReflectionAPI.getNmsClass("PacketPlayOutEntityDestroy")
                    .getConstructor(entityIds.getClass())
                    .newInstance(entityIds);
            final Object packetPlayOutPlayerInfo0 = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo")
                    .getConstructor(enumPlayerInfoAction, entities.getClass())
                    .newInstance(Enum.valueOf(enumPlayerInfoAction, "REMOVE_PLAYER"), entities);
            final Object packetPlayOutPlayerInfo1 = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo")
                    .getConstructor(enumPlayerInfoAction, entities.getClass())
                    .newInstance(Enum.valueOf(enumPlayerInfoAction, "ADD_PLAYER"), entities);
            final Object packetPlayOutNamedEntitySpawn = ReflectionAPI.getNmsClass("PacketPlayOutNamedEntitySpawn")
                    .getConstructor(entityHuman)
                    .newInstance(handle);

            setSkin(profile, resolverUuid(skinName));

            Bukkit.getOnlinePlayers().forEach(p -> {
                ReflectionAPI.sendPacket(p, packetPlayOutEntityDestroy);
                ReflectionAPI.sendPacket(p, packetPlayOutPlayerInfo0);
            });

            u.getPlayer().setHealth(0);
            u.getPlayer().spigot().respawn();

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> Bukkit.getOnlinePlayers().forEach(p -> {
                ReflectionAPI.sendPacket(p, packetPlayOutPlayerInfo1);
                if (!p.getName().equals(u.getPlayer().getName()))
                    ReflectionAPI.sendPacket(p, packetPlayOutNamedEntitySpawn);
            }), 4L);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void setSkin(GameProfile profile, UUID uuid) {
        final JsonObject json = Utils.getJson(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromUUID(uuid)));

        if (json != null) {
            final JsonObject properties = json.get("properties").getAsJsonArray().get(0).getAsJsonObject();
            final String skin = properties.get("value").getAsString();
            final String signature = properties.get("signature").getAsString();

            profile.getProperties().removeAll("textures");
            profile.getProperties().put("textures", new Property("textures", skin, signature));
        }
    }


    private UUID resolverUuid(String name){
        final JsonObject json = Utils.getJson(String.format("https://api.mojang.com/users/profiles/minecraft/%s", name));

        if (json != null){
            String uuid = json.get("id").getAsString();
            uuid = uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");

            return UUID.fromString(uuid);
        }
        return null;
    }
}
