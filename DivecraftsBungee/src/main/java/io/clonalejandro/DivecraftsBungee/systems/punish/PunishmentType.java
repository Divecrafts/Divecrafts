package io.clonalejandro.DivecraftsBungee.systems.punish;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Alex
 * On 04/05/2020
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
 * All rights reserved for clonalejandro ©DivecraftsBungee 2017/2020
 */

@AllArgsConstructor
public enum PunishmentType {

    BAN("Ban", "punish.ban"),
    MUTE("Mute", "punish.mute"),
    WARNING("Warn", "punish.warn"),
    KICK("Kick", "punish.kick"),

    UNBAN("Unban", "punish.unban"),
    UNMUTE("Unmute", "punish.unmute");

    @Getter private String name;
    @Getter private String perms;

    public static PunishmentType getPunishType(String name) {
        switch (name.toLowerCase()) {
            case "ban":
                return BAN;
            case "mute":
                return MUTE;
            case "warn":
                return WARNING;
            case "kick":
                return KICK;
            case "unban":
                return UNBAN;
            case "unmute":
                return UNMUTE;
            default:
                return BAN;
        }
    }


    @AllArgsConstructor
    public enum PunishCode {
        HACKS("Hacks", 'h', BAN),
        SPAM("Spam", 's', MUTE),
        FLOOD("Flood", 'f', MUTE),
        LIBRE("Libre", 'l', null);

        @Getter private String name;
        @Getter private char code;
        @Getter private PunishmentType punish;

        public static PunishCode getPunish(char code) {
            switch (code) {
                case 'h':
                    return HACKS;
                case 's':
                    return SPAM;
                case 'f':
                    return FLOOD;
                case 'l':
                    return LIBRE;
                default:
                    return LIBRE;
            }
        }
    }
}