package io.clonalejandro.Essentials.utils;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by Alex
 * On 02/05/2020
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
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class SpawnYml {


    /** SMALL CONSTRUCTORS **/

    public static SpawnYml instance;

    private final Main plugin = Main.instance;

    private File file;
    private FileConfiguration configuration;

    public SpawnYml(){
        SpawnYml.instance = this;
        manageDocument();
    }


    /** REST **/

    /**
     * This function add a value from path
     * @param key
     * @param value
     */
    public void set(String key, Object value){
        configuration.set(key, value);
        configSaver();
    }


    /**
     * This function remove a value from path
     * @param key
     */
    public void remove(String key){
        configuration.set(key, null);
        configSaver();
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public Object get(String key){
        return configuration.get(key);
    }

    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public String getString(String key){
        return configuration.getString(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public List<String> getStrings(String key){
        return configuration.getStringList(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public Integer getInt(String key){
        return configuration.getInt(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public Boolean getBoolean(String key){
        return configuration.getBoolean(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public List<Boolean> getBooleans(String key){
        return configuration.getBooleanList(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public Double getDouble(String key){
        return configuration.getDouble(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public List<Double> getDoubles(String key){
        return configuration.getDoubleList(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public Float getFloat(String key){
        return (float) configuration.getDouble(key);
    }


    /**
     * This function return a value from path
     * @param key
     * @return
     */
    public List<Float> getFloats(String key){
        return configuration.getFloatList(key);
    }


    /**
     * This function save this config instance
     */
    public void saveConfig(){
        configSaver();
    }


    /** OTHERS **/

    /**
     * This function manage data.yml
     */
    private void manageDocument(){
        file = new File(plugin.getDataFolder(), "spawn.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs(); //Create a folder
            plugin.saveResource("spawn.yml", true); //Create file
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }


    /**
     * This function manage exception and save the data.yml
     */
    private void configSaver() {
        try {
            configuration.save(file);
            Bukkit.getConsoleSender().sendMessage(Main.translate("&b&lclonalejandro> &fHey! &adata saved 😍"));
        }
        catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(Main.translate("&4&lclonalejandro> &fHey! &cdata error!"));
        }
    }


}
