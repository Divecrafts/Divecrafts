package io.clonalejandro.DivecraftsCore.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.clonalejandro.DivecraftsCore.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;
import java.util.Map;

public class BungeeMensager implements PluginMessageListener {



    /** SMALL CONSTRUCTORS **/

    public static Map<String, Integer> juegos = new HashMap<>();
    public static Map<String, Integer> lobbies = new HashMap<>();
    public static Map<String, HashMap<String, Integer>> lobbiesPlayers = new HashMap<>();

    public static void conectarA(Player p, String server){
        ByteArrayDataOutput f = ByteStreams.newDataOutput();
        f.writeUTF("Conectar");
        f.writeUTF(server);

        p.sendPluginMessage(Main.getInstance(),"DivecraftsBungee", f.toByteArray());
    }


    public static void conectarExacto(Player p, String server){
        ByteArrayDataOutput f = ByteStreams.newDataOutput();
        f.writeUTF("Exacto");
        f.writeUTF(server);

        p.sendPluginMessage(Main.getInstance(),"DivecraftsBungee", f.toByteArray());
    }


    /** REST **/

    @Override

    public synchronized void onPluginMessageReceived(String c, Player pr, byte[] msg){

        ByteArrayDataInput in = ByteStreams.newDataInput(msg);

        String subcanal = in.readUTF();

        if (subcanal.equalsIgnoreCase("Juegos")){
            juegos.clear();

            String[] servers = in.readUTF().split(",");

            for (String server : servers){
                juegos.put(server.split("-")[0],Integer.parseInt(server.split("-")[1].equals("null") ? "0" : server.split("-")[1]));
            }
        }
        if (subcanal.equalsIgnoreCase("Lobbies")){
            lobbies.clear();
            String[] l = in.readUTF().split(",");
            for (String lobby : l){
                String[] ldef = lobby.split("-");
                lobbies.put(ldef[0], Integer.parseInt(ldef[1].equals("null") ? "0" : ldef[1]));
            }
        }
        if (subcanal.equalsIgnoreCase("LobbiesCat")){
            lobbiesPlayers.clear();
            String[] categoria = in.readUTF().split(";");
            for (String catstring : categoria) {
                String catname = catstring.split("-")[1];
                HashMap<String, Integer> temp = null;
                String[] subserv = catname.split(",");
                for (String svi : subserv) {
                    String[] servidor = svi.split(":");
                    if (lobbiesPlayers.containsKey(catname)) {
                        temp = lobbiesPlayers.get(catname);
                        temp.put(servidor[0], Integer.valueOf(servidor[1]));
                        lobbiesPlayers.put(catname, temp);
                    } else {
                        temp = new HashMap<>();
                        temp.put(servidor[0], Integer.valueOf(servidor[1]));
                        lobbiesPlayers.put(catname, temp);
                    }
                }
                lobbiesPlayers.put(catstring.split("-")[0], temp);
            }
        }

    }

    public static Map<String, HashMap<String, Integer>> getLobbiesPlayers() {
        return lobbiesPlayers;
    }

    public static Map<String, Integer> getJuegos() {
        return juegos;
    }

    public static Map<String, Integer> getLobbies() {
        return lobbies;
    }
}
