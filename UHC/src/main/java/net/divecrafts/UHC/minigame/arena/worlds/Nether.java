package net.divecrafts.UHC.minigame.arena.worlds;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.divecrafts.UHC.exceptions.WorldException;
import net.divecrafts.UHC.utils.Api;

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

final class Nether implements IWorld {


    /** SMALL CONSTRUCTORS **/

    private final String name;
    private final org.bukkit.World world;

    Nether(){
        name = "Nether_tmp";
        world = createWorld();
    }


    /** REST **/

    /**
     * This function remove this world
     */
    public synchronized void remove(){
        if (worldExists()){
            Bukkit.unloadWorld(world, false);
            Api.worldRemover(getWorldFolder());
        }
    }


    /**
     * This function return a parsed World
     * @return
     */
    public World getWorld() {
        return world;
    }


    /** OTHERS **/

    /**
     * This function create a world with name parameter
     * @return
     */
    private World createWorld(){
        try { checkIfWorldExists(); }
        catch (WorldException ex){ ex.printStackTrace(); }

        final WorldCreator worldCreator = new WorldCreator(name);

        worldCreator.environment(org.bukkit.World.Environment.NETHER);
        worldCreator.type(WorldType.NORMAL);

        return worldCreator.createWorld();
    }


    /**
     * This function check if world exists
     * @return
     */
    private boolean worldExists() {
        return world != null && Bukkit.getWorld(world.getName()) != null;
    }


    /**
     * This function check if world exists
     * @throws WorldException
     */
    private void checkIfWorldExists() throws WorldException {
        if (worldExists())
            throw new WorldException("The world" + world + Api.SPACE + "exists!");
    }


    /** INHERITANCE **/

    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public final Block getBlockAt(final int x, final int y, final int z) {
        return world.getBlockAt(x, y, z);
    }


    /**
     * @param location
     * @return
     */
    public final Block getBlockAt(final Location location) {
        return world.getBlockAt(location);
    }


    /**
     * @deprecated
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Deprecated public final int getBlockTypeIdAt(final int x, final int y, final int z) {
        return world.getBlockTypeIdAt(x, y, z);
    }


    /**
     * @deprecated
     * @param location
     * @return
     */
    @Deprecated public final int getBlockTypeIdAt(final Location location) {
        return world.getBlockTypeIdAt(location);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final int getHighestBlockYAt(final int x, final int z) {
        return world.getHighestBlockYAt(x, z);
    }


    /**
     * @param location
     * @return
     */
    public final int getHighestBlockYAt(final Location location) {
        return world.getHighestBlockYAt(location);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final Block getHighestBlockAt(final int x, final int z) {
        return world.getHighestBlockAt(x, z);
    }


    /**
     * @param location
     * @return
     */
    public final Block getHighestBlockAt(final Location location) {
        return world.getHighestBlockAt(location);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final Chunk getChunkAt(final int x, final int z) {
        return world.getChunkAt(x, z);
    }


    /**
     * @param location
     * @return
     */
    public final Chunk getChunkAt(final Location location) {
        return location.getChunk();
    }


    /**
     * @param block
     * @return
     */
    public final Chunk getChunkAt(final Block block) {
        return block.getChunk();
    }


    /**
     * @param chunk
     * @return
     */
    public final boolean isChunkLoaded(final Chunk chunk) {
        return chunk.isLoaded();
    }


    /**
     * @return
     */
    public final Chunk[] getLoadedChunks() {
        return world.getLoadedChunks();
    }


    /**
     * @param chunk
     */
    public final void loadChunk(final Chunk chunk) {
        world.loadChunk(chunk);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final boolean isChunkLoaded(final int x, final int z) {
        return world.isChunkLoaded(x, z);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final boolean isChunkInUse(final int x, final int z) {
        return world.isChunkInUse(x, z);
    }


    /**
     * @param x
     * @param z
     */
    public final void loadChunk(final int x, final int z) {
        world.loadChunk(x, z);
    }


    /**
     * @param x
     * @param z
     * @param b
     * @return
     */
    public final boolean loadChunk(final int x, final int z, final boolean b) {
        return world.loadChunk(x, z, b);
    }


    /**
     * @param chunk
     * @return
     */
    public final boolean unloadChunk(final Chunk chunk) {
        return world.unloadChunk(chunk);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final boolean unloadChunk(final int x, final int z) {
        return world.unloadChunk(x, z);
    }


    /**
     * @param x
     * @param z
     * @param b
     * @return
     */
    public final boolean unloadChunk(final int x, final int z, final boolean b) {
        return world.unloadChunk(x, z, b);
    }


    /**
     * @param x
     * @param z
     * @param b
     * @param b1
     * @return
     */
    public final boolean unloadChunk(final int x, final int z, final boolean b, final boolean b1) {
        return world.unloadChunk(x, z, b, b1);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final boolean unloadChunkRequest(final int x, final int z) {
        return world.unloadChunkRequest(x, z);
    }


    /**
     * @param x
     * @param z
     * @param b
     * @return
     */
    public final boolean unloadChunkRequest(final int x, final int z, final boolean b) {
        return world.unloadChunkRequest(x, z, b);
    }


    /**
     * @param x
     * @param z
     * @return
     */
    public final boolean regenerateChunk(final int x, final int z) {
        return world.regenerateChunk(x, z);
    }


    /**
     * @deprecated
     * @param x
     * @param z
     * @return
     */
    @Deprecated public final boolean refreshChunk(final int x, final int z) {
        return world.refreshChunk(x, z);
    }


    /**
     * @param location
     * @param item
     * @return
     */
    public final Item dropItem(final Location location, final ItemStack item) {
        return world.dropItem(location, item);
    }


    /**
     * @param location
     * @param item
     * @return
     */
    public final Item dropItemNaturally(final Location location, final ItemStack item) {
        return world.dropItemNaturally(location, item);
    }


    /**
     * @param location
     * @param vector
     * @param yaw
     * @param pitch
     * @return
     */
    public final Arrow spawnArrow(final Location location, final Vector vector, final float yaw, final float pitch) {
        return world.spawnArrow(location, vector, yaw, pitch);
    }


    /**
     * @param location
     * @param treeType
     * @return
     */
    public final boolean generateTree(final Location location, final TreeType treeType) {
        return world.generateTree(location, treeType);
    }


    /**
     * @param location
     * @param treeType
     * @param blockChangeDelegate
     * @return
     */
    public final boolean generateTree(final Location location, final TreeType treeType, final BlockChangeDelegate blockChangeDelegate) {
        return world.generateTree(location, treeType, blockChangeDelegate);
    }


    /**
     * @param location
     * @param entityType
     * @return
     */
    public final Entity spawnEntity(final Location location, final EntityType entityType) {
        return world.spawnEntity(location, entityType);
    }


    /**
     * @deprecated
     * @param location
     * @param entityType
     * @return
     */
    @Deprecated public final LivingEntity spawnCreature(final Location location, final EntityType entityType) {
        return world.spawnCreature(location, entityType);
    }


    /**
     * @deprecated
     * @param location
     * @param creatureType
     * @return
     */
    @Deprecated public final LivingEntity spawnCreature(final Location location, final CreatureType creatureType) {
        return world.spawnCreature(location, creatureType);
    }


    /**
     * @param location
     * @return
     */
    public final LightningStrike strikeLightning(final Location location) {
        return world.strikeLightning(location);
    }


    /**
     * @param location
     * @return
     */
    public final LightningStrike strikeLightningEffect(final Location location) {
        return world.strikeLightningEffect(location);
    }


    /**
     * @return
     */
    public final List<Entity> getEntities() {
        return world.getEntities();
    }


    /**
     * @return
     */
    public final List<LivingEntity> getLivingEntities() {
        return world.getLivingEntities();
    }


    /**
     * @deprecated
     * @param classes
     * @param <T>
     * @return
     */
    @SafeVarargs @Deprecated public final <T extends Entity> Collection<T> getEntitiesByClass(final Class<T>... classes) {
        return world.getEntitiesByClass(classes);
    }


    /**
     * @param aClass
     * @param <T>
     * @return
     */
    public final <T extends Entity> Collection<T> getEntitiesByClass(final Class<T> aClass) {
        return world.getEntitiesByClass(aClass);
    }


    /**
     * @param classes
     * @return
     */
    public final Collection<Entity> getEntitiesByClasses(final Class<?>... classes) {
        return world.getEntitiesByClasses(classes);
    }


    /**
     * @return
     */
    public final List<Player> getPlayers() {
        return world.getPlayers();
    }


    /**
     * @param location
     * @param radiusX
     * @param radiusY
     * @param radiusZ
     * @return
     */
    public final Collection<Entity> getNearbyEntities(final Location location, final double radiusX, final double radiusY, final double radiusZ) {
        return world.getNearbyEntities(location, radiusX, radiusY, radiusZ);
    }


    /**
     * @return
     */
    public final String getName(){
        return world.getName();
    }


    /**
     * @return
     */
    public final UUID getUID() {
        return world.getUID();
    }


    /**
     * @return
     */
    public final Location getSpawnLocation() {
        return world.getSpawnLocation();
    }


    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public final boolean setSpawnLocation(final int x, final int y, final int z) {
        return world.setSpawnLocation(x, y, z);
    }


    /**
     * @param x
     * @param y
     * @param z
     * @return
     */
    public final boolean setSpawnLocation(final double x, final double y, final double z) {
        return world.setSpawnLocation((int) x, (int) y, (int) z);
    }


    /**
     * @param location
     * @return
     */
    public final boolean setSpawnLocation(final Location location) {
        return world.setSpawnLocation((int) location.getX(), (int) location.getY(), (int) location.getZ());
    }


    /**
     * @return
     */
    public final long getTime() {
        return world.getTime();
    }


    /**
     * @param time
     */
    public final void setTime(final long time) {
        world.setTime(time);
    }


    /**
     * @return
     */
    public final long getFullTime() {
        return world.getFullTime();
    }


    /**
     * @param time
     */
    public final void setFullTime(final long time) {
        world.setTime(time);
    }


    /**
     * @return
     */
    public final boolean hasStorm() {
        return world.hasStorm();
    }


    /**
     * @param storm
     */
    public final void setStorm(final boolean storm) {
        world.setStorm(storm);
    }


    /**
     * @return
     */
    public final int getWeatherDuration() {
        return world.getWeatherDuration();
    }


    /**
     * @param duration
     */
    public final void setWeatherDuration(int duration) {
        world.setWeatherDuration(duration);
    }


    /**
     * @return
     */
    public final boolean isThundering() {
        return world.isThundering();
    }


    /**
     * @param thundering
     */
    public final void setThundering(boolean thundering) {
        world.setThundering(thundering);
    }


    /**
     * @return
     */
    public final int getThunderDuration() {
        return world.getThunderDuration();
    }


    /**
     * @param duration
     */
    public final void setThunderDuration(final int duration) {
        world.setThunderDuration(duration);
    }


    /**
     * @param z
     * @param y
     * @param z
     * @param power
     * @return
     */
    public final boolean createExplosion(final double x, final double y, final double z, final float power) {
        return world.createExplosion(x, y, z, power);
    }


    /**
     * @param x
     * @param y
     * @param z
     * @param power
     * @param b
     * @return
     */
    public final boolean createExplosion(final double x, final double y, final double z, final float power, final boolean b) {
        return world.createExplosion(x, y, z, power, b);
    }


    /**
     * @param x
     * @param y
     * @param z
     * @param power
     * @param b
     * @param b1
     * @return
     */
    public final boolean createExplosion(final double x, final double y, final double z, final float power, final boolean b, final boolean b1) {
        return world.createExplosion(x, y, z, power, b, b1);
    }


    /**
     * @param location
     * @param power
     * @return
     */
    public final boolean createExplosion(final Location location, final float power) {
        return world.createExplosion(location, power);
    }


    /**
     * @param location
     * @param power
     * @param b
     * @return
     */
    public final boolean createExplosion(final Location location, final float power, final boolean b) {
        return world.createExplosion(location, power, b);
    }


    /**
     * @return
     */
    public final org.bukkit.World.Environment getEnvironment() {
        return world.getEnvironment();
    }


    /**
     * @return
     */
    public final long getSeed() {
        return world.getSeed();
    }


    public final boolean getPVP() {
        return world.getPVP();
    }


    /**
     * @param pvp
     */
    public final void setPVP(final boolean pvp) {
        world.setPVP(pvp);
    }


    /**
     * @return
     */
    public final ChunkGenerator getGenerator() {
        return world.getGenerator();
    }


    /**
     * void
     */
    public final void save() {
        world.save();
    }


    /**
     * @return
     */
    public final List<BlockPopulator> getPopulators() {
        return world.getPopulators();
    }


    /**
     * @param location
     * @param aClass
     * @param <T>
     * @return
     * @throws IllegalArgumentException
     */
    public final <T extends Entity> T spawn(final Location location, final Class<T> aClass) throws IllegalArgumentException {
        return world.spawn(location, aClass);
    }


    /**
     * @deprecated
     * @param location
     * @param material
     * @param b
     * @return
     * @throws IllegalArgumentException
     */
    @Deprecated public final FallingBlock spawnFallingBlock(final Location location, final Material material, final byte b) throws IllegalArgumentException {
        return world.spawnFallingBlock(location, material, b);
    }


    /**
     * @deprecated
     * @param location
     * @param i
     * @param b
     * @return
     * @throws IllegalArgumentException
     */
    @Deprecated public final FallingBlock spawnFallingBlock(final Location location, final int i, final byte b) throws IllegalArgumentException {
        return world.spawnFallingBlock(location, i, b);
    }


    /**
     * @param location
     * @param effect
     * @param i
     */
    public final void playEffect(final Location location, final Effect effect, final int i) {
        world.playEffect(location, effect, i);
    }


    /**
     * @param location
     * @param effect
     * @param i
     * @param i1
     */
    public final void playEffect(final Location location, final Effect effect, final int i, final int i1) {
        world.playEffect(location, effect, i, i1);
    }


    /**
     * @param location
     * @param effect
     * @param t
     * @param <T>
     */
    public final <T> void playEffect(final Location location, final Effect effect, final T t) {
        world.playEffect(location, effect, t);
    }


    /**
     * @param location
     * @param effect
     * @param t
     * @param i
     * @param <T>
     */
    public final <T> void playEffect(final Location location, final Effect effect, final T t, final int i) {
        world.playEffect(location, effect, t, i);
    }


    /**
     * @param i
     * @param i1
     * @param b
     * @param b1
     * @return
     */
    public final ChunkSnapshot getEmptyChunkSnapshot(final int i, final int i1, final boolean b, final boolean b1) {
        return world.getEmptyChunkSnapshot(i, i1, b, b1);
    }


    /**
     * @param b
     * @param b1
     */
    public final void setSpawnFlags(final boolean b, final boolean b1) {
        world.setSpawnFlags(b, b1);
    }


    /**
     * @return
     */
    public final boolean getAllowAnimals() {
        return world.getAllowAnimals();
    }


    /**
     * @return
     */
    public final boolean getAllowMonsters() {
        return world.getAllowMonsters();
    }


    /**
     * @param i
     * @param i1
     * @return
     */
    public final Biome getBiome(final int i, final int i1) {
        return world.getBiome(i, i1);
    }


    /**
     * @param i
     * @param i1
     * @param biome
     */
    public final void setBiome(final int i, final int i1, final Biome biome) {
        world.setBiome(i, i1, biome);
    }


    /**
     * @param i
     * @param i1
     * @return
     */
    public final double getTemperature(final int i, final int i1) {
        return world.getTemperature(i, i1);
    }


    /**
     * @param i
     * @param i1
     * @return
     */
    public final double getHumidity(final int i, final int i1) {
        return world.getHumidity(i, i1);
    }


    /**
     * @return
     */
    public final int getMaxHeight() {
        return world.getMaxHeight();
    }


    /**
     * @return
     */
    public final int getSeaLevel() {
        return world.getSeaLevel();
    }


    /**
     * @return
     */
    public final boolean getKeepSpawnInMemory() {
        return world.getKeepSpawnInMemory();
    }


    /**
     * @param keepSpawnInMemory
     */
    public final void setKeepSpawnInMemory(final boolean keepSpawnInMemory) {
        world.setKeepSpawnInMemory(keepSpawnInMemory);
    }


    /**
     * @return
     */
    public final boolean isAutoSave() {
        return world.isAutoSave();
    }


    /**
     * @param autoSave
     */
    public final void setAutoSave(boolean autoSave) {
        world.setAutoSave(autoSave);
    }


    /**
     * @param difficulty
     */
    public final void setDifficulty(Difficulty difficulty) {
        world.setDifficulty(difficulty);
    }


    /**
     * @return
     */
    public final Difficulty getDifficulty() {
        return world.getDifficulty();
    }


    /**
     * @return
     */
    public final File getWorldFolder() {
        return world.getWorldFolder();
    }


    /**
     * @return
     */
    public final WorldType getWorldType() {
        return world.getWorldType();
    }


    /**
     * @return
     */
    public final boolean canGenerateStructures() {
        return world.canGenerateStructures();
    }


    /**
     * @return
     */
    public final long getTicksPerAnimalSpawns() {
        return world.getTicksPerAnimalSpawns();
    }


    /**
     * @param ticks
     */
    public final void setTicksPerAnimalSpawns(final int ticks) {
        world.setTicksPerAnimalSpawns(ticks);
    }


    /**
     * @return
     */
    public final long getTicksPerMonsterSpawns() {
        return world.getTicksPerMonsterSpawns();
    }


    /**
     * @param ticks
     */
    public final void setTicksPerMonsterSpawns(final int ticks) {
        world.setTicksPerMonsterSpawns(ticks);
    }


    /**
     * @return
     */
    public final int getMonsterSpawnLimit() {
        return world.getMonsterSpawnLimit();
    }


    /**
     * @param monsters
     */
    public final void setMonsterSpawnLimit(final int monsters) {
        world.setMonsterSpawnLimit(monsters);
    }


    /**
     * @return
     */
    public int getAnimalSpawnLimit() {
        return world.getAnimalSpawnLimit();
    }


    /**
     * @param animals
     */
    public final void setAnimalSpawnLimit(final int animals) {
        world.setAnimalSpawnLimit(animals);
    }


    /**
     * @return
     */
    public final int getWaterAnimalSpawnLimit() {
        return world.getWaterAnimalSpawnLimit();
    }


    /**
     *
     * @param animals
     */
    public final void setWaterAnimalSpawnLimit(final int animals) {
        world.setWaterAnimalSpawnLimit(animals);
    }


    /**
     * @return
     */
    public final int getAmbientSpawnLimit() {
        return world.getAmbientSpawnLimit();
    }


    /**
     * @param limit
     */
    public final void setAmbientSpawnLimit(final int limit) {
        world.setAmbientSpawnLimit(limit);
    }


    /**
     * @param location
     * @param sound
     * @param power
     * @param pitch
     */
    public final void playSound(final Location location, final Sound sound, final float power, final float pitch) {
        world.playSound(location, sound, power, pitch);
    }


    /**
     * @return
     */
    public final String[] getGameRules() {
        return world.getGameRules();
    }


    /**
     * @param s
     * @return
     */
    public final String getGameRuleValue(final String s) {
        return world.getGameRuleValue(s);
    }


    /**
     * @param s
     * @param s1
     * @return
     */
    public final boolean setGameRuleValue(final String s, final String s1) {
        return world.setGameRuleValue(s, s1);
    }


    /**
     * @param s
     * @return
     */
    public final boolean isGameRule(final String s) {
        return world.isGameRule(s);
    }


    /**
     * @return
     */
    public final org.bukkit.World.Spigot spigot() {
        return null;
    }


    /**
     * @return
     */
    public final WorldBorder getWorldBorder() {
        return world.getWorldBorder();
    }


}
