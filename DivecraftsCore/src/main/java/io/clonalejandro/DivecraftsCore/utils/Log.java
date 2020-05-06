package io.clonalejandro.DivecraftsCore.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.clonalejandro.DivecraftsCore.Main;

@AllArgsConstructor
public enum Log {

    NORMAL("", 'r'),
    ERROR("[ERROR]", 'c'),
    SUCCESS("[SUCCESS]", '2'),
    DEBUG("[DEBUG]", 'd');

    @Getter private final String prefix;
    @Getter private final char color;


    public static void debugLog(String msg) {
        log(Log.DEBUG, msg);
    }
    public static void log(Log log, String msg) {
        Main.getInstance().getServer().getConsoleSender().sendMessage(Utils.colorize("&" + log.getColor() + log.getPrefix() + " " + msg));
    }
}
