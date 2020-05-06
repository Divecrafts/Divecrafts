package io.clonalejandro.DivecraftsBungee.comands;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class CMD_Request extends Command {

   public CMD_Request() {
      super("request");
   }

   public void execute(CommandSender sender, String[] args) {
      if(sender instanceof ProxiedPlayer) {
         ProxiedPlayer p = (ProxiedPlayer)sender;
         if(args.length > 0) {
            String razon = String.join(" ", args);

            for (ProxiedPlayer player : Main.getMB().getProxy().getPlayers()) {
                if (player.hasPermission("bungee.staff")) {
                    player.sendMessage("§c§lAyuda> §e" + p.getName() + " §fNecesita ayuda en: §b" + p.getServer().getInfo().getName() + " §fpor: §e" + razon);
                }
            }
         } else {
            try {
               p.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(p), "Request.Uso"));
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }

}
