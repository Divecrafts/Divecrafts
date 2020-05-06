package io.clonalejandro.DivecraftsCore.utils;

import io.clonalejandro.DivecraftsCore.api.SUser;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSound(SUser u, Sound sonido, float volumen, float pitch){
        u.getPlayer().playSound(u.getLoc(), sonido, volumen, pitch);
    }

    public static void sendPlayerSound(Player p, Sound sonido, int volumen, int pitch){
        p.playSound(p.getLocation(),sonido,volumen,pitch);
    }

    public static void sendBroadcastSound(Sound sonido, int volumen, int pitch){
        for (Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), sonido, volumen, pitch);
        }
    }
}
