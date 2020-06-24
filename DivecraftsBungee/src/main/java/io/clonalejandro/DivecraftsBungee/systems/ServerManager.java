package io.clonalejandro.DivecraftsBungee.systems;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.listeners.SvListener;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServerManager {
	
	public ArrayList<String> todosv = new ArrayList<>();
	
	public ArrayList<String> lobbies = new ArrayList<>();
	public ArrayList<String> lobbiesgames = new ArrayList<>();
	@Getter public ArrayList<String> games = new ArrayList<>();
	
	public HashMap<String, ArrayList<ServerInfo>> lobbiesListCat = new HashMap<>();
	
	public ScheduledTask lobbiestask;

	public void iniciarSvManager(Main mb){
		for (ServerInfo svinfo : mb.getProxy().getServers().values()) {
			if (svinfo.getName().contains("lobby")) {
				lobbies.add(svinfo.getName());
			} else if (svinfo.getName().contains("lsw") || svinfo.getName().contains("lew") || svinfo.getName().contains("lfc")) {
				lobbiesgames.add(svinfo.getName());
			} else {
				games.add(svinfo.getName());
			}
			todosv.add(svinfo.getName());
		}
		
		TextUtils.consoleMSG("&6&l&m------------------------------", MessageType.SUCCESS);
		TextUtils.consoleMSG("&eLobbies: &a" + lobbies.size(), MessageType.SUCCESS);
		TextUtils.consoleMSG("&eLobbies Juegos: &a" + lobbiesgames.size(), MessageType.SUCCESS);
		TextUtils.consoleMSG("&eJuegos: &a" + games.size(), MessageType.SUCCESS);
		TextUtils.consoleMSG("&eTotal Servidores: &a" + todosv.size(), MessageType.SUCCESS);
		mandarDatos(mb);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void mandarDatos(Main mb) {
		TextUtils.consoleMSG("&eTask de juegos iniciadas.", MessageType.SUCCESS);
		TextUtils.consoleMSG("&6&l&m------------------------------", MessageType.SUCCESS);
		lobbiestask = mb.getProxy().getScheduler().schedule(mb, () -> {
			try {

				mandarLobbies(mb);
				mandarJuegos(mb);

				lobbiesListCat.clear();
				for (String categorie : Main.getConfigManager().getConfig().getStringList("lobbies")) {
					for (ServerInfo svi : mb.getProxy().getServers().values()) {
						if(svi.getName().contains(categorie)) {
							List serversEw;
							if(lobbiesListCat.containsKey(categorie)) {
								serversEw = lobbiesListCat.get(categorie);
								serversEw.add(svi);
								lobbiesListCat.put(categorie, new ArrayList(serversEw));
							} else {
								ArrayList<ServerInfo> svlist = new ArrayList<>();
								svlist.add(svi);
								lobbiesListCat.put(categorie, svlist);
							}
						}
					}
				}
				for (String categorie : Main.getConfigManager().getConfig().getStringList("juegos")) {
					for (ServerInfo svi : mb.getProxy().getServers().values()) {
						if(svi.getName().contains(categorie)) {
							List serversEw;
							if(lobbiesListCat.containsKey(categorie)) {
								serversEw = lobbiesListCat.get(categorie);
								serversEw.add(svi);
								lobbiesListCat.put(categorie, new ArrayList(serversEw));
							} else {
								ArrayList<ServerInfo> svlist = new ArrayList<>();
								svlist.add(svi);
								lobbiesListCat.put(categorie, svlist);
							}
						}
					}
				}

				mandarLobbiesPlayers(mb);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}, 0L, 8L, TimeUnit.SECONDS);
	}
	
	public String lobbiesCatMsg() {
		StringBuilder totalmsg = new StringBuilder();
		for (String categorie : lobbiesListCat.keySet()) {
			StringBuilder msg = new StringBuilder(categorie + "-");
			for (ServerInfo svi : lobbiesListCat.get(categorie)) {
				msg.append(svi.getName()).append(":").append(svi.getPlayers().size()).append(",");
			}
			totalmsg.append(msg.substring(0, msg.length() - 1)).append(";");
		}
		return totalmsg.substring(0, totalmsg.length() - 1);
	}
	
	public void mandarLobbiesPlayers(Main mb){
		for (String namesend : todosv) {
			ServerInfo s = mb.getProxy().getServerInfo(namesend);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(stream);

			try {
	            out.writeUTF("LobbiesCat");
	            out.writeUTF(lobbiesCatMsg());
			} catch (IOException var6) {
	            var6.printStackTrace();
			}

			s.sendData("DivecraftsBungee", stream.toByteArray());
		}
	}

	HashMap<String, Integer> datosgames = new HashMap<>();
	
	public void mandarLobbies(Main mb) throws IOException {
		datosgames.clear();
		for (String categorie : Main.getConfigManager().getConfig().getStringList("lobbies")) {
			for (String namesv : todosv) {
				ServerInfo svinfo = mb.getProxy().getServerInfo(namesv);
				int players;
				if (namesv.contains(categorie)) {
					if (datosgames.containsKey(categorie)) {
						players = datosgames.get(categorie);
						players += svinfo.getPlayers().size();
						datosgames.put(categorie, players);
					} else {
						players = svinfo.getPlayers().size();
						datosgames.put(categorie, players);
					}
				}
			}
		}
		
		for (String namesend : todosv) {
			ServerInfo s = mb.getProxy().getServerInfo(namesend);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(stream);

			try {
				out.writeUTF("Lobbies");
	            out.writeUTF(getLobbiesMsg());
			} catch (IOException var6) {
	            var6.printStackTrace();
			}

			s.sendData("DivecraftsBungee", stream.toByteArray());
		}
	}
	
	public String getLobbiesMsg() {
		List<String> res = new ArrayList<>();

		try {
			Main.getConfigManager().getConfig().getStringList("lobbies").forEach(lobby -> res.add(String.format("%s-%s", lobby, datosgames.get(lobby))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return String.join(",", res);
	}
	
	public String getGamesMsg() {//TODO:
		List<String> res = new ArrayList<>();

		try {
			Main.getConfigManager().getConfig().getStringList("juegos").forEach(game -> res.add(String.format("%s-%s", game, datosgames.get(game))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return String.join(",", res);
	}
	
	@SuppressWarnings("static-access")
	public void mandarJuegos(Main mb) throws IOException {
		for (String categorie : mb.getConfigManager().getConfig().getStringList("juegos")) {
			for (String namesv : games) {
				ServerInfo svinfo = mb.getProxy().getServerInfo(namesv);
				int players;
				if (namesv.contains(categorie)) {
					if (datosgames.containsKey(categorie)) {
						players = datosgames.get(categorie);
						players += svinfo.getPlayers().size();
						datosgames.put(categorie, players);
					} else {
						players = svinfo.getPlayers().size();
						datosgames.put(categorie, players);
					}
				}
			}
		}
		
		for (String namesend : todosv) {
			ServerInfo s = mb.getProxy().getServerInfo(namesend);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(stream);

			try {
	            out.writeUTF("Juegos");
	            out.writeUTF(getGamesMsg());
			} catch (IOException var6) {
	            var6.printStackTrace();
			}

			s.sendData("DivecraftsBungee", stream.toByteArray());
		}
	}
	
	public ServerInfo elegirServer(String server) throws IOException {
		ServerInfo sF = null;
		for (String categorie : Main.getConfigManager().getConfig().getStringList("lobbies")) {
			if(server.equalsIgnoreCase(categorie)) {
				sF = lobbiesListCat.get(categorie).get(new Random().nextInt(lobbiesListCat.get(categorie).size()));
			}
		}
		for (String categorie : Main.getConfigManager().getConfig().getStringList("juegos")) {
			if(server.equalsIgnoreCase(categorie)) {
				sF = lobbiesListCat.get(categorie).get(new Random().nextInt(lobbiesListCat.get(categorie).size()));

				final HashMap<ServerInfo, String> motds = buildMotds(categorie);

				if (motds.values().stream().anyMatch(motd -> motd.contains("Running")))
					while (motds.get(sF).contains("Running"))
						sF = lobbiesListCat.get(categorie).get(new Random().nextInt(lobbiesListCat.get(categorie).size()));
			}
		}
		return sF;
	}
	
	public ServerInfo connectExact(String server) {
		ServerInfo sF = null;
		for (ServerInfo svinfo : Main.getMB().getProxy().getServers().values()) {
			if (svinfo.getName().equalsIgnoreCase(server)) {
				sF = svinfo;
			}
		}
		return sF;
	}

	private HashMap<ServerInfo, String> buildMotds(String categorie){
		final HashMap<ServerInfo, String> data = new HashMap<>();

		lobbiesListCat.get(categorie).forEach(sv ->
				SvListener.getMotd(sv, (res, err) ->
						data.put(sv, TextComponent.toLegacyText(res.getDescriptionComponent()))
				)
		);

		return data;
	}
}
