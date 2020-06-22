package net.divecrafts.UHC.minigame.arena;

import net.divecrafts.UHC.utils.Api;
import org.bukkit.Location;

import net.divecrafts.UHC.minigame.arena.worlds.Border;
import net.divecrafts.UHC.minigame.arena.worlds.WorldManager;
import net.divecrafts.UHC.utils.clonadoc.Getter;

/**
 * Created by alejandrorioscalera
 * On 1/3/18
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
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2018
 */

public class Arena extends WorldManager {


    /** SMALL CONSTRUCTORS **/

    private final WorldManager netherManager = new WorldManager(GameWorld.NETHER);
    private final Border border;

    public Arena(){
        super(GameWorld.NORMAL);
        this.border = new Border(getWorld());

        initBorder();
    }


    /** REST **/

    /**
     * This function removes a worlds
     */
    public void remove(){
        super.remove();
        netherManager.remove();
    }


    /** OTHERS **/

    /**
     * This function init the border
     */
    private void initBorder(){
        final Location center = new Location(getWorld(), CENTERX, 0, CENTERZ);

        border.setSize(Api.getConfigManager().getWidth());
        border.setCenter(center);
    }


    /** GETTERS **/

    @Getter public WorldManager getNetherManager() {
        return netherManager;
    }

    @Getter public Border getBorder(){
        return border;
    }


}
