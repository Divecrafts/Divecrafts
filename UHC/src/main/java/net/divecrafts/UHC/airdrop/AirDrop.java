package net.divecrafts.UHC.airdrop;

import net.divecrafts.UHC.utils.clonadoc.Getter;
import net.divecrafts.UHC.utils.clonadoc.Setter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

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

public abstract class AirDrop {


    /** SMALL CONSTRUCTORS **/

    private final Location location;

    private ArrayList<ItemStack> drop = new ArrayList<>();

    public AirDrop(Location location){
        this.location = location;
        init();
    }


    /** REST **/

    /**
     * This function represent a abstract action on itemPut in the Chest
     * @param chest
     * @return
     */
    public abstract Chest itemPutter(Chest chest);


    /**
     * This function represent a abstract action on AirDrop create
     */
    public abstract void onCreate();


    /**
     * This function add drop to array
     * @param drop
     */
    public void addDrop(ItemStack drop){
        this.drop.add(drop);
    }


    /**
     * This function add drop to array
     * @param drop
     */
    public void addDrop(ItemStack... drop){
        this.drop.addAll(Arrays.asList(drop));
    }


    /** OTHERS **/

    /**
     * This function init the class
     * @deprecated
     */
    @Deprecated
    private void init(){
        final Chest chest = chestCreator();
        final Block block = chest.getBlock();
        final World world = location.getWorld();

        itemPutter(chest);
        world.spawnFallingBlock(location, block.getType(), block.getData());
        onCreate();
    }


    /**
     * This function create a chest
     * @return
     */
    private Chest chestCreator(){
        location.getBlock().setType(Material.CHEST);
        return (Chest) location.getBlock();
    }


    /** SETTERS **/

    @Setter public ArrayList<ItemStack> setDrop(ArrayList<ItemStack> drop){
        this.drop = drop;
        return this.drop;
    }


    @Setter public ArrayList<ItemStack> setDrop(ItemStack... drop){
        this.drop = new ArrayList<>(Arrays.asList(drop));
        return this.drop;
    }


    /** GETTERS **/

    @Getter public ArrayList<ItemStack> getDrop() {
        return drop;
    }
}
