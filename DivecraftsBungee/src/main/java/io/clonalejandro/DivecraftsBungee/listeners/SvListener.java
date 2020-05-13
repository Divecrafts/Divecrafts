package io.clonalejandro.DivecraftsBungee.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.sql.SQLException;

public class SvListener implements Listener {

   @EventHandler
   public void onPluginMessage(PluginMessageEvent e) throws IOException {
      if(e.getTag().equalsIgnoreCase("DivecraftsBungee")) {
         ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
         String subchannel = in.readUTF();
         if (subchannel.equalsIgnoreCase("Conectar")) {
             String s = in.readUTF();
             ProxiedPlayer p = (ProxiedPlayer)e.getReceiver();
             ServerInfo server = Main.getSvManager().elegirServer(s);
             if(server != null) p.connect(server);
             else {
                 try {
                     p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Global.portalnodispo"));
                 }
                 catch (SQLException ex) {
                     ex.printStackTrace();
                 }
             }
         }
         if (subchannel.equalsIgnoreCase("Exacto")) {
             String s = in.readUTF();
             ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
             ServerInfo server = Main.getSvManager().connectExact(s);

             if (server != null) p.connect(server);
             else {
                 try {
                     p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Global.portalnodispo"));
                 }
                 catch (SQLException ex) {
                     ex.printStackTrace();
                 }
             }
         }
      }
   }
}
