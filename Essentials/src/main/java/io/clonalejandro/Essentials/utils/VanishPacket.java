package io.clonalejandro.Essentials.utils;

import io.clonalejandro.DivecraftsCore.utils.ReflectionAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alex
 * On 01/05/2020
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

public class VanishPacket extends ReflectionAPI {

    private static ArrayList<String> vanishPlayers = new ArrayList<>();
    private static final Class enumPlayerInfoAction = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");


    public static void vanishForPlayer(final Player playerToVanish, final Player target){
        try {
            final Object handle = ReflectionAPI.getHandle(playerToVanish);
            final Object entities = Array.newInstance(handle.getClass(), 1);

            Array.set(entities, 0, handle);

            final Object packet = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo")
                    .getConstructor(enumPlayerInfoAction, entities.getClass())
                    .newInstance(Enum.valueOf(enumPlayerInfoAction, "REMOVE_PLAYER"), entities);

            target.hidePlayer(playerToVanish);
            sendPacket(target, packet);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void setVanish(final Player player){
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            try {
                final Object handle = ReflectionAPI.getHandle(player);
                final Object entities = Array.newInstance(handle.getClass(), 1);

                Array.set(entities, 0, handle);

                final Object packet = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo")
                        .getConstructor(enumPlayerInfoAction, entities.getClass())
                        .newInstance(Enum.valueOf(enumPlayerInfoAction, "REMOVE_PLAYER"), entities);

                onlinePlayer.hidePlayer(player);
                sendPacket(onlinePlayer, packet);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });

        vanishPlayers.add(player.getName());
    }

    public static void removeVanish(final Player player){
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
           try {
               final Object handle = ReflectionAPI.getHandle(player);
               final Object entities = Array.newInstance(handle.getClass(), 1);

               Array.set(entities, 0, handle);

               final Object packet = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo")
                       .getConstructor(enumPlayerInfoAction, entities.getClass())
                       .newInstance(Enum.valueOf(enumPlayerInfoAction, "ADD_PLAYER"), entities);

               onlinePlayer.showPlayer(player);
               sendPacket(onlinePlayer, packet);
           }
           catch (Exception ex){
               ex.printStackTrace();
           }
        });

        vanishPlayers.remove(player.getName());
    }

    public static ArrayList<String> getVanishPlayers() {
        return vanishPlayers;
    }

    public static Boolean isVanish(final Player player){
        return vanishPlayers.contains(player.getName());
    }
}
