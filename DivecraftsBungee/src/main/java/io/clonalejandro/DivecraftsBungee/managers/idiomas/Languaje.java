package io.clonalejandro.DivecraftsBungee.managers.idiomas;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Languaje {

        /* Idiomas
         *
         * ES => 0
         * US => 1
         *
         * */

        public static int getPlayerLang(ProxiedPlayer player) throws SQLException {
            int lang = 0;
            ResultSet rsSetting = Main.getMySQL().query("SELECT * FROM `settings` WHERE `uuid`='" + player.getUniqueId().toString() + "'");
            while (rsSetting.next()) {
                lang = rsSetting.getInt("lang");
            }
            return lang;
        }

        public static String getLangMsg(int language, String whatToSearch){
            String file;
            switch (language){
                case 1:
                    file = "en.json";
                    break;
                default:
                    file = "es.json";
            }

            JsonObject json = getJson("http://play.divecrafts.net:3000/api/" + file);

            if (json == null) return "Error!";

            String[] key = whatToSearch.split("\\.");

            String message = json.get(key[0]).getAsJsonObject().get(key[1]).getAsString();

            return TextUtils.formatText(message);
        }

    private static JsonObject getJson(String url){
        try {
            URL cUrl = new URL(url);
            HttpURLConnection request = (HttpURLConnection) cUrl.openConnection();

            request.connect();

            JsonParser jsonParser = new JsonParser();
            InputStream stream = (InputStream) request.getContent();
            JsonElement response = jsonParser.parse(new InputStreamReader(stream));

            return response.getAsJsonObject();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
