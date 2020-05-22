package io.clonalejandro.DivecraftsCore.idiomas;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Languaje {

    @AllArgsConstructor
    @Getter
    public enum Lang {
        ES (0), EN (1);
        private final int id;
    }

    public static String getLangMsg(int language, String whatToSearch) {
        String file;
        switch (language) {
            case 1:
                file = "en.json";
                break;
            default:
                file = "es.json";
        }

        JsonObject json = getJson("http://91.121.76.115:3000/api/" + file);

        if (json == null) return "Error!";

        String[] key = whatToSearch.split("\\.");

        String message = json.get(key[0]).getAsJsonObject().get(key[1]).getAsString();

        return Utils.colorize(message).replace("%new", "\n");
    }

    private static JsonObject getJson(String url) {
        try {
            URL cUrl = new URL(url);
            HttpURLConnection request = (HttpURLConnection) cUrl.openConnection();

            request.connect();

            JsonParser jsonParser = new JsonParser();
            InputStream stream = (InputStream) request.getContent();
            JsonElement response = jsonParser.parse(new InputStreamReader(stream));

            return response.getAsJsonObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
