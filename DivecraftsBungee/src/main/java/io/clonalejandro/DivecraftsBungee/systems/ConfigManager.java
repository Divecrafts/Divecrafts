package io.clonalejandro.DivecraftsBungee.systems;

import io.clonalejandro.DivecraftsBungee.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigManager {

	public File dataFolder;
	public File config;
	public File clanesFolder;
    public File clanConf;
	
	public void generateConfig(Main mb) throws IOException {
		dataFolder = mb.getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
			config = new File(mb.getDataFolder() + File.separator + "config.yml");
			if (!config.exists()) {
				config.createNewFile();
				Configuration configuration = YamlConfiguration.getProvider(YamlConfiguration.class).load(config);
				configuration.set("motd", "    &b&l&m--&8&l&m[-&a  Nameless Network &c[1.8-1.15]  &8&l&m-]&b&l&m--&r                              &d&l*");
				ArrayList<String> list1 = new ArrayList<>();
				list1.add("lobby");
				list1.add("lsw");
				list1.add("lew");
				list1.add("lfc");
				configuration.set("lobbies", list1); 
				ArrayList<String> list2 = new ArrayList<>();
				list2.add("sw");
				list2.add("ew");
				list2.add("ffa");
				list2.add("fc");
				configuration.set("juegos", list2);

				YamlConfiguration.getProvider(YamlConfiguration.class).save(configuration, config);
			}
		} else {
			dataFolder = mb.getDataFolder();
			config = new File(mb.getDataFolder() + File.separator + "config.yml");
		}
		clanesFolder = new File(dataFolder + File.separator + "Clanes");
		if (!clanesFolder.exists()) {
		    clanesFolder.mkdir();
        }
	}
	
	public Configuration getConfig() throws IOException {
		return YamlConfiguration.getProvider(YamlConfiguration.class).load(config);
	}

    public File getClanFile(String clanName) throws IOException {
        clanConf = new File(clanesFolder + File.separator + clanName);
        return clanConf;
    }

    public Configuration getClanConfig(String name) throws IOException {
        return YamlConfiguration.getProvider(YamlConfiguration.class).load(getClanFile(name));
    }

}
