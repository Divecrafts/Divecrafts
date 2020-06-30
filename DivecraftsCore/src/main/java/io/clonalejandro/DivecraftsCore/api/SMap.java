package io.clonalejandro.DivecraftsCore.api;

import com.google.common.io.Files;
import lombok.Getter;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.utils.Log;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SMap {

    private static final Main plugin = Main.getInstance();

    private String worldName;
    private String path = "/home/minecraft/GlobalConfig/maps/";
    @Getter private File world;

	/**
	 * Default constructor
	 *
	 * @param game The game name
	 * @param worldName The world name
	 */
	public SMap(String game, String worldName) {
        this.worldName = worldName;
        world = new File(path + game + "/" + worldName);
        Bukkit.getConsoleSender().sendMessage(path + game + File.pathSeparator + worldName);
    }

    /**
     * Changes the Map to a random
     */
    public void randomMap(){
        changeMap(getRandomMap(worldName));
    }

    /**
     * Changes the map
     *
     * @param map
     */
	public void changeMap(File map){
		if (new File("world").delete()){
			try {
				Files.copy(map, new File("world"));
			} catch(IOException e) {
				Log.log(Log.ERROR, "Error al copiar el mapa");
			}
		} else {
            Log.log(Log.ERROR, "Error al borrar el mapa");
		}
	}

	private File getRandomMap(String currentMap){
		File[] dir = new File(path).listFiles(File::isDirectory);
		File[] maps = {};
		for (int x = 0; x < dir.length; x++) if (!dir[x].getName().equalsIgnoreCase(currentMap)) maps[x] = dir[x];
		return maps[new Random().nextInt(dir.length)];
	}
}
