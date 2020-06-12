package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.airdrop.AirDrop;
import net.divecrafts.UHC.minigame.Game;
import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.task.AirDropsTask;
import net.divecrafts.UHC.utils.Api;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Chest;

/**
 * Created by alejandrorioscalera
 * On 20/2/18
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

class AirDrops {


    /** SMALL CONSTRUCTORS **/

    private final AirDropsTask task;

    AirDrops(Main plugin){
        task = new AirDropsTask() {
            public void onEnd() { airDropGenerator(); }
        };
        task.runTaskTimer(plugin, 1L, 20L);
    }


    /** REST **/

    /**
     * This function return a process task
     * @return
     */
    public AirDropsTask getTask() {
        return task;
    }


    /** OTHERS **/

    /**
     * This class help to instance the AirDrops
     */
    private class AirDropper extends AirDrop {
        AirDropper(Location location){
            super(location);
        }

        @Override
        public void onCreate() {
            Api.broadCast(Api.translator(
                Api.getConfigManager().getAirDropAlert()
            ));

            Api.playSound(Bukkit.getWorld("Normal_tmp"), Sound.BLAZE_DEATH, 3.0F, 0.533F);
        }

        @Override
        public Chest itemPutter(Chest chest) {
            //chest.getBlockInventory().addItem(.randomDrops(Drops.DropType.ALL).toArray(new ItemStack[Drops.randomDrops(Drops.DropType.ALL).size()]));
            return chest;
        }
    }


    /**
     * This function generates a AirDrop
     */
    private void airDropGenerator(){
        final Location location = Game.game != null ?
                Arena.genRandomSpawn(256 / 2) : null;

        if (location != null)
            new AirDropper(location);
    }


}
