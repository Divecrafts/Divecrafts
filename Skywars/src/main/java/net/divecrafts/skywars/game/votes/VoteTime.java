package net.divecrafts.skywars.game.votes;

import lombok.Getter;

import java.util.HashMap;

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

public class VoteTime {

    @Getter private static final HashMap<TimeType, Integer> votes = new HashMap<>();
    @Getter private final TimeType type;

    public VoteTime(TimeType type){
        this.type = type;

        if (!votes.containsKey(type)) votes.put(type, 1);
        else votes.replace(type, votes.get(type) +1);
    }
}
