package io.clonalejandro.DivecraftsBungee.managers;

import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;

public class  ServerPerms {

	@Getter public ArrayList<String> svperms = new ArrayList<>();
	public ArrayList<String> svgames = new ArrayList<>();
	
	public void initPerms() {
		svperms.add("lsw");
		svperms.add("lew");
		svperms.add("lfc");
		svperms.add("lobby");
		svperms.add("survival");
		svperms.add("ffa");
		svgames.add("ew");
		svgames.add("sw");
		svgames.add("fc");
		svgames.add("uhc");
		svgames.add("mum");
		svgames.add("mb");
	}
	
	public ArrayList<ProxiedPlayer> inGame = new ArrayList<>(); 
	
}
