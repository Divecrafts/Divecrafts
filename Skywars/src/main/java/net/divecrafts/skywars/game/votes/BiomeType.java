package net.divecrafts.skywars.game.votes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Alex
 * On 01/07/2020
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
 * All rights reserved for clonalejandro ©Skywars 2017/2020
 */

@AllArgsConstructor
public enum  BiomeType {
    RAINING(true), CLEAR(false);
    @Getter private final boolean raining;
}
