package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.utils.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMD_ReportStaff extends Command {

   public CMD_ReportStaff() {
      super("reportstaff");
   }

   public void execute(CommandSender sender, String[] args) {
      if(sender instanceof ProxiedPlayer) {
         ProxiedPlayer p = (ProxiedPlayer)sender;
         if(p.hasPermission("bungee.staff")) {
            if(args.length != 0) {
               ResultSet s;
               int r;
               String ex;
               if(args[0].equalsIgnoreCase("lista")) {
                  try {
                     s = Main.getMySQL().query("SELECT * FROM reportes WHERE cerrado=0 ORDER BY hora LIMIT 10");
                     if (!s.next()) {
                    	 p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fNo hay reportes abiertos")));
                     } else {
                    	 s.beforeFirst();
                    	 while(s.next()) {
	                         r = s.getInt("id");
	                         ex = s.getString("jugador");
	                         p.sendMessage((new ComponentBuilder(ChatColor.AQUA + "[⚠] ")).event(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&bWarnear a &6" + ex))).create())).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/warn " + ex + " (R. " + r + ") ")).append(ChatColor.LIGHT_PURPLE + "[✂] ").event(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&dBanear a &6" + ex))).create())).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/gtempban " + ex + " ")).append(ChatColor.RED + "[✘]").event(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cCerrar reporte &6" + r))).create())).event(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/reportstaff cerrar " + r)).append(ChatColor.translateAlternateColorCodes('&', " &a" + r + " &e" + ex + " &fpor &6" + s.getString("razon") + "&7(" + s.getString("server") + ") ")).create());
	                      }
                    }
                  } catch (SQLException var17) {
                     var17.printStackTrace();
                  }
               }

               if(args[0].equalsIgnoreCase("cerrar")) {
                  if(args.length == 2) {
                     Main.getMySQL().actualizarQuery("UPDATE reportes SET cerrado=1 WHERE id=" + args[1]);
                     Main.getMySQL().actualizarQuery("UPDATE reportes SET staff=\'" + p.getName() + "\' WHERE id=" + args[1]);
                     p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &aSe ha cerrado correctamente el Reporte &e" + args[1])));
                  } else {
                     p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff cerrar <ID>")));
                  }
               }

               if(args[0].equalsIgnoreCase("ver")) {
                  if(args.length == 2) {
                     try {
                        s = Main.getMySQL().query("SELECT * FROM reportes WHERE id=" + args[1] + " LIMIT 1");
                        if(s.next()) {
                           Integer var19 = Integer.valueOf(s.getInt("id"));
                           ex = s.getString("jugador");
                           String res = s.getString("reportador");
                           String razon = s.getString("razon");
                           String staff = s.getString("staff");
                           String server = s.getString("server");
                           Date hora = new Date((long)s.getInt("hora") * 1000L);
                           String horaFinal = (new SimpleDateFormat("HH:mm dd-MM-yyyy")).format(hora);
                           Boolean cerrado = Boolean.valueOf(s.getBoolean("cerrado"));
                           p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&6&lDetalles del Reporte &e&l" + var19)));
                           p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "Jugador: &e" + ex + "   &fReportado por: &e" + res)));
                           p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "Razón: &e" + razon + " &fServer: &e" + server)));
                           if(cerrado.booleanValue()) {
                              p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "Hora: " + horaFinal + "   &fCerrado: &aSí, &fpor " + staff)));
                           } else {
                              p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "Hora: " + horaFinal + "   &fCerrado: &cNo")));
                              TextComponent cerrar = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c[Cerrar]"));
                              cerrar.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cCerrar reporte &6" + var19))).create()));
                              cerrar.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/reportstaff cerrar " + s.getInt("id")));
                              p.sendMessage(cerrar);
                           }
                        } else {
                           p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fID no encontrada")));
                        }
                     } catch (SQLException var15) {
                        var15.printStackTrace();
                     }
                  } else {
                     p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff ver <ID>")));
                  }
               }

               if(args[0].equalsIgnoreCase("usuario")) {
                  if(args.length == 2) {
                     int var18 = 0;
                     r = 0;

                     try {
                        for(ResultSet var20 = Main.getMySQL().query("SELECT * FROM reportes WHERE staff=\'" + args[1] + "\'"); var20.next(); ++var18) {
                           ;
                        }

                        for(ResultSet var21 = Main.getMySQL().query("SELECT * FROM reportes WHERE jugador=\'" + args[1] + "\'"); var21.next(); ++r) {
                           ;
                        }
                     } catch (SQLException var16) {
                        var16.printStackTrace();
                     }

                     p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &e" + args[1] + " &fha sido reportado &6" + r + " &fveces")));
                     p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &e" + args[1] + " &fha cerrado &6" + var18 + " &freportes")));
                  } else {
                     p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff usuario <Nombre>")));
                  }
               }
            } else {
               p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff lista")));
               p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff cerrar &e<ID>")));
               p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff ver &e<ID>")));
               p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fUso: &e/reportstaff usuario <Nombre>")));
            }
         } else {
            p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&c&lReportes> &fNo puedes hacer esto")));
         }
      }

   }
}
