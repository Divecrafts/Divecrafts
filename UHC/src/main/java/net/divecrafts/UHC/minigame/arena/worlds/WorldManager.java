package net.divecrafts.UHC.minigame.arena.worlds;

import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.clonadoc.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by alejandrorioscalera
 * On 24/1/18
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

public class WorldManager {


    /** SMALL CONSTRUCTORS **/

    protected static final int WIDTH = Api.getConfigManager().getWidth(),
                             CENTERX = Api.getConfigManager().getCenterX().intValue(),
                             CENTERZ = Api.getConfigManager().getCenterZ().intValue();// The width and centers is date for generate map

    private final GameWorld gameWorld;
    private final IWorld iWorld;
    private final World world;
    private final int mobLimit;

    public WorldManager(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        this.iWorld = worldCreator();
        this.world = iWorld.getWorld();
        this.mobLimit = mobLimitPlacer();

        configureWorlds();
    }


    /** REST **/

    public enum GameWorld {NORMAL, NETHER}


    /**
     * This function remove a World instanced
     */
    public void remove(){
        Api.worldRemover(getWorld().getWorldFolder());
    }


    /** STATICS **/

    /**
     * This function return a randomLocation in range of coords
     * @return
     */
    public static Location genRandomSpawn() {
        final Random r = new Random();

        final World world = Bukkit.getWorld("Normal_tmp");

        final int half = WIDTH / 2,
                x = randomBetween(r, -half + CENTERX, half + CENTERX),
                z = randomBetween(r, -half + CENTERZ, half + CENTERZ);

        return permutateLocation(new Location(world, x, 0, z));
    }


    /**
     * This function return a randomLocation in range of coords
     * @param y
     * @return
     */
    public static Location genRandomSpawn(int y) {
        final Random r = new Random();

        final World world = Bukkit.getWorld("Normal_tmp");

        final int half = WIDTH / 2,
                x = randomBetween(r, -half + CENTERX, half + CENTERX),
                z = randomBetween(r, -half + CENTERZ, half + CENTERZ);

        return permutateLocation(new Location(world, x, y, z));
    }


    /** OTHERS **/

    /**
     * This function set properties for world instanced
     */
    private void configureWorlds(){
        switch (gameWorld){
            case NORMAL:
                world.setPVP(true);
                world.setAutoSave(false);
                world.setStorm(false);
                world.setThundering(false);
                world.setAnimalSpawnLimit((int) (mobLimit * 0.45));
                world.setMonsterSpawnLimit((int) (mobLimit * 0.45));
                world.setWaterAnimalSpawnLimit((int) (mobLimit * 0.1));
                world.setDifficulty(Difficulty.HARD);
                break;
            case NETHER:
                world.setPVP(true);
                world.setAutoSave(false);
                world.setStorm(false);
                world.setThundering(false);
                world.setMonsterSpawnLimit((int) (mobLimit * 0.45));
                world.setAnimalSpawnLimit((int) (mobLimit * 0.45));
                world.setWaterAnimalSpawnLimit((int) (mobLimit * 0.1));
                world.setDifficulty(Difficulty.HARD);
                break;
        }
    }


    /**
     * This function create a World and return a WorldInterface
     * @return
     */
    private IWorld worldCreator(){
        if (gameWorld == GameWorld.NORMAL) return
                new Normal();
        else return
                new Nether();
    }


    /**
     * This function return a mobLimit for instance
     * @return
     */
    private int mobLimitPlacer(){
        if (gameWorld == GameWorld.NORMAL) return
            Api.getConfigManager().getWorldMobLimit();
        else return
            Api.getConfigManager().getNetherMobLimit();
    }


    /** STATICS **/

    /**
     * This function return a random coord between in min number and max number
     * @param r
     * @param min
     * @param max
     * @return
     */
    private static int randomBetween(Random r, int min, int max) {
        return r.nextInt(max - min) + min;
    }


    /**
     * This function get Y from secure zone likes a travelAgent
     * @param location
     * @return
     */
    private static Location permutateLocation(Location location){
        while (isMaterialInBlackList(location))
            location = locationYIncrementer(location, 2);
        return location;
    }


    /**
     * This function return if material has the blackList for the FLocation permutator
     * @param location
     * @return
     */
    private static boolean isMaterialInBlackList(final Location location){
        final List<Material> blackList = new ArrayList<>(Arrays.asList(
                Material.LONG_GRASS, Material.STATIONARY_WATER, Material.WATER,
                Material.WATER_LILY, Material.LAVA, Material.STATIONARY_LAVA,
                Material.STONE
        ));

        final Location upY = new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch());

        return blackList.contains(location.getBlock().getType()) && upY.getBlock().getType() != Material.AIR;
    }


    /**
     * This function increment the Location
     * @param location
     * @param y
     * @return
     */
    private static Location locationYIncrementer(final Location location, final double y){
        return new Location(
                location.getWorld(),
                location.getX(),
                location.getY() + y,
                location.getZ()
        );
    }


    /** GETTERS **/

    @Getter
    public GameWorld getGameWorld(){
        return gameWorld;
    }

    @Getter public World getWorld(){
        return world;
    }


}
