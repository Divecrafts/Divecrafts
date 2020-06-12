package net.divecrafts.UHC.minigame.arena.worlds;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

interface IWorld {


    /** INTERFACE **/

    void remove();

    org.bukkit.World getWorld();


    /** INHERITANCE **/

    Block getBlockAt(int var1, int var2, int var3);

    Block getBlockAt(Location var1);

    /** @deprecated */
    @Deprecated
    int getBlockTypeIdAt(int var1, int var2, int var3);

    /** @deprecated */
    @Deprecated
    int getBlockTypeIdAt(Location var1);

    int getHighestBlockYAt(int var1, int var2);

    int getHighestBlockYAt(Location var1);

    Block getHighestBlockAt(int var1, int var2);

    Block getHighestBlockAt(Location var1);

    Chunk getChunkAt(int var1, int var2);

    Chunk getChunkAt(Location var1);

    Chunk getChunkAt(Block var1);

    boolean isChunkLoaded(Chunk var1);

    Chunk[] getLoadedChunks();

    void loadChunk(Chunk var1);

    boolean isChunkLoaded(int var1, int var2);

    boolean isChunkInUse(int var1, int var2);

    void loadChunk(int var1, int var2);

    boolean loadChunk(int var1, int var2, boolean var3);

    boolean unloadChunk(Chunk var1);

    boolean unloadChunk(int var1, int var2);

    boolean unloadChunk(int var1, int var2, boolean var3);

    boolean unloadChunk(int var1, int var2, boolean var3, boolean var4);

    boolean unloadChunkRequest(int var1, int var2);

    boolean unloadChunkRequest(int var1, int var2, boolean var3);

    boolean regenerateChunk(int var1, int var2);

    /** @deprecated */
    @Deprecated
    boolean refreshChunk(int var1, int var2);

    Item dropItem(Location var1, ItemStack var2);

    Item dropItemNaturally(Location var1, ItemStack var2);

    Arrow spawnArrow(Location var1, Vector var2, float var3, float var4);

    boolean generateTree(Location var1, TreeType var2);

    boolean generateTree(Location var1, TreeType var2, BlockChangeDelegate var3);

    Entity spawnEntity(Location var1, EntityType var2);

    /** @deprecated */
    @Deprecated
    LivingEntity spawnCreature(Location var1, EntityType var2);

    /** @deprecated */
    @Deprecated
    LivingEntity spawnCreature(Location var1, CreatureType var2);

    LightningStrike strikeLightning(Location var1);

    LightningStrike strikeLightningEffect(Location var1);

    List<Entity> getEntities();

    List<LivingEntity> getLivingEntities();

    <T extends Entity> Collection<T> getEntitiesByClass(Class<T> var1);

    Collection<Entity> getEntitiesByClasses(Class<?>... classes);

    List<Player> getPlayers();

    Collection<Entity> getNearbyEntities(Location var1, double var2, double var4, double var6);

    String getName();

    UUID getUID();

    Location getSpawnLocation();

    boolean setSpawnLocation(int x, int y, int z);

    boolean setSpawnLocation(double x, double y, double z);

    boolean setSpawnLocation(Location location);

    long getTime();

    void setTime(long var1);

    long getFullTime();

    void setFullTime(long var1);

    boolean hasStorm();

    void setStorm(boolean var1);

    int getWeatherDuration();

    void setWeatherDuration(int var1);

    boolean isThundering();

    void setThundering(boolean var1);

    int getThunderDuration();

    void setThunderDuration(int var1);

    boolean createExplosion(double var1, double var3, double var5, float var7);

    boolean createExplosion(double var1, double var3, double var5, float var7, boolean var8);

    boolean createExplosion(double var1, double var3, double var5, float var7, boolean var8, boolean var9);

    boolean createExplosion(Location var1, float var2);

    boolean createExplosion(Location var1, float var2, boolean var3);

    org.bukkit.World.Environment getEnvironment();

    long getSeed();

    boolean getPVP();

    void setPVP(boolean var1);

    ChunkGenerator getGenerator();

    void save();

    List<BlockPopulator> getPopulators();

    <T extends Entity> T spawn(Location var1, Class<T> var2) throws IllegalArgumentException;

    /** @deprecated */
    @Deprecated
    FallingBlock spawnFallingBlock(Location var1, Material var2, byte var3) throws IllegalArgumentException;

    /** @deprecated */
    @Deprecated
    FallingBlock spawnFallingBlock(Location var1, int var2, byte var3) throws IllegalArgumentException;

    void playEffect(Location var1, Effect var2, int var3);

    void playEffect(Location var1, Effect var2, int var3, int var4);

    <T> void playEffect(Location var1, Effect var2, T var3);

    <T> void playEffect(Location var1, Effect var2, T var3, int var4);

    ChunkSnapshot getEmptyChunkSnapshot(int var1, int var2, boolean var3, boolean var4);

    void setSpawnFlags(boolean var1, boolean var2);

    boolean getAllowAnimals();

    boolean getAllowMonsters();

    Biome getBiome(int var1, int var2);

    void setBiome(int var1, int var2, Biome var3);

    double getTemperature(int var1, int var2);

    double getHumidity(int var1, int var2);

    int getMaxHeight();

    int getSeaLevel();

    boolean getKeepSpawnInMemory();

    void setKeepSpawnInMemory(boolean var1);

    boolean isAutoSave();

    void setAutoSave(boolean var1);

    void setDifficulty(Difficulty var1);

    Difficulty getDifficulty();

    File getWorldFolder();

    WorldType getWorldType();

    boolean canGenerateStructures();

    long getTicksPerAnimalSpawns();

    void setTicksPerAnimalSpawns(int var1);

    long getTicksPerMonsterSpawns();

    void setTicksPerMonsterSpawns(int var1);

    int getMonsterSpawnLimit();

    void setMonsterSpawnLimit(int var1);

    int getAnimalSpawnLimit();

    void setAnimalSpawnLimit(int var1);

    int getWaterAnimalSpawnLimit();

    void setWaterAnimalSpawnLimit(int var1);

    int getAmbientSpawnLimit();

    void setAmbientSpawnLimit(int var1);

    void playSound(Location var1, Sound var2, float var3, float var4);

    String[] getGameRules();

    String getGameRuleValue(String var1);

    boolean setGameRuleValue(String var1, String var2);

    boolean isGameRule(String var1);

    org.bukkit.World.Spigot spigot();

    WorldBorder getWorldBorder();
}
