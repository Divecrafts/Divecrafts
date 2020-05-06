package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;
import java.util.Iterator;

public class CMD_Report extends Command {

   public CMD_Report() {
      super("report");
   }

   public void execute(CommandSender sender, String[] args) {
      if(sender instanceof ProxiedPlayer) {
         ProxiedPlayer p = (ProxiedPlayer)sender;
         p.getPendingConnection().isOnlineMode();
         if(args.length > 1) {
            ProxiedPlayer r = Main.getMB().getProxy().getPlayer(args[0]);
            if(r != null) {
               if(p.getServer().getInfo() == r.getServer().getInfo()) {
                  String razon = "";

                  for(int tiempo = 1; tiempo < args.length; ++tiempo) {
                     razon = razon + args[tiempo] + " ";
                  }

                  long var10 = System.currentTimeMillis() / 1000L;
                  Main.getMySQL().actualizarQuery("INSERT INTO reportes (jugador, razon, reportador, hora, server, cerrado, staff) VALUES (\'" + r.getName() + "\', \'" + razon + "\', \'" + p.getName() + "\', \'" + var10 + "\',\'" + p.getServer().getInfo().getName() + "\',0,\'nadie\')");
                  Iterator<ProxiedPlayer> var8 = Main.getMB().getProxy().getPlayers().iterator();

                  try {
                     p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Report.gracias"));
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
                  
                  while(var8.hasNext()) {
                     ProxiedPlayer staff = (ProxiedPlayer)var8.next();
                     if(staff.hasPermission("bungee.staff")) {
                        staff.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &6" + r.getDisplayName() + " &freportado por &e" + p.getDisplayName() + " &7(" + p.getServer().getInfo().getName() + ")")));
                        staff.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&eRazón: &f" + razon)));
                     }
                  }
               } else {
                  try {
                     p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Report.noestesv"));
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }
            } else {
               try {
                  p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Report.noonline"));
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }
         } else {
            try {
               p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Report.uso"));
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }

   }
}
