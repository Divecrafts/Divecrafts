package io.clonalejandro.DivecraftsCore.utils;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Cooldown {

    @Getter private final int time;
    @Getter private final HashMap<String, Long> cooldowns;

    public Cooldown(int time) {
        this.time = time;
        this.cooldowns = new HashMap<>();
    }

    public void setOnCooldown(Player player) {
        getCooldowns().put(player.getName(), System.currentTimeMillis());
    }
    public void removeCooldown(Player player) {
        getCooldowns().remove(player.getName());
    }

    public void setOnCooldown(String abi) {
        getCooldowns().put(abi, System.currentTimeMillis());
    }
    public void removeCooldown(String abi) {
        getCooldowns().remove(abi);
    }

    public int getTimeLeft(String str) {
        if (!isCoolingDown(str)) return 0;
        return (int) (((getCooldowns().get(str) - (System.currentTimeMillis() - (getTime() * 1000))) / 1000) + 1);
    }

    public boolean isCoolingDown(Player player) {
        return isCoolingDown(player.getName());
    }

    public boolean isCoolingDown(String str) {
        if (!getCooldowns().containsKey(str)) return false;
        return getCooldowns().get(str) >= (System.currentTimeMillis() - (getTime() * 1000));
    }
}
