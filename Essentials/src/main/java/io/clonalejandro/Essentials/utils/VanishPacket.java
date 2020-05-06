package io.clonalejandro.Essentials.utils;

import me.clonalejandro.ReflectionAPI.ReflectionAPI;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

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


    public static void vanishForPlayer(final Player playerToVanish, final Player target){
        final CraftPlayer craftPlayer = ((CraftPlayer) playerToVanish);
        final Object packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle());

        target.hidePlayer(playerToVanish);
        sendPacket(target, packet);
    }

    public static void setVanish(final Player player){
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            final CraftPlayer craftPlayer = ((CraftPlayer) player);
            final Object packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle());

            onlinePlayer.hidePlayer(player);
            sendPacket(onlinePlayer, packet);
        });

        vanishPlayers.add(player.getName());
    }

    public static void removeVanish(final Player player){
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            final CraftPlayer craftPlayer = ((CraftPlayer) player);
            final Object packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle());

            onlinePlayer.showPlayer(player);
            sendPacket(onlinePlayer, packet);
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
