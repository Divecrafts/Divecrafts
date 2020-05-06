package io.clonalejandro.DivecraftsBungee.managers.rangos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public enum Rangos {

    ADMIN(9, "ADM", ChatColor.RED),
    SMOD(8, "SMOD", ChatColor.GOLD),
    MOD(7, "MOD", ChatColor.GOLD),
    DEVELOPER(6, "DEV", ChatColor.DARK_AQUA),
    BUILDER(5, "BTM", ChatColor.BLUE),
    YOUTUBER(4, "YT", ChatColor.RED),
    ALPHA(3, "ALPHA", ChatColor.GREEN),
    DELTA(2, "DELTA", ChatColor.LIGHT_PURPLE),
    ECHO(1, "ECHO", ChatColor.AQUA),
    USUARIO(0, "USUARIO", ChatColor.GRAY);
    
    @Getter private final int id;
    @Getter private final String tag;
    @Getter private final ChatColor color;
}

