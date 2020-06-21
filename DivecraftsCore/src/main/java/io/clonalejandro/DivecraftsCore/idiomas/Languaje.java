package io.clonalejandro.DivecraftsCore.idiomas;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Languaje {

    private static final List<LangFile> searches = new ArrayList<>();

    @AllArgsConstructor
    @Getter
    public enum Lang {
        ES (0), EN (1);
        private final int id;
    }

    public static String getLangMsg(int language, String whatToSearch) {
        final List<LangFile> customSearch = searches.stream()
                .filter(langFile -> langFile.getKey().equalsIgnoreCase(whatToSearch))
                .collect(Collectors.toList());

        if (customSearch.size() > 0) return Utils.colorize(customSearch.get(0).getValue()).replace("%new%", "\n");

        String file;
        switch (language) {
            case 1:
                file = "en.json";
                break;
            default:
                file = "es.json";
        }

        final JsonObject json = getJson("http://play.divecrafts.net:3000/api/" + file);

        if (json == null) return "Error!";

        String[] key = whatToSearch.split("\\.");
        String message = json.get(key[0]).getAsJsonObject().get(key[1]).getAsString();

        searches.add(language, new LangFile(language, whatToSearch, message));

        return Utils.colorize(message).replace("%new", "\n");
    }

    private static JsonObject getJson(String url) {
        try {
            URL cUrl = new URL(url);

            if (url.contains("https")){
                HttpsURLConnection request = (HttpsURLConnection) cUrl.openConnection();

                request.connect();

                JsonParser jsonParser = new JsonParser();
                InputStream stream = (InputStream) request.getContent();
                JsonElement response = jsonParser.parse(new InputStreamReader(stream));

                return response.getAsJsonObject();
            }
            else {
                HttpURLConnection request = (HttpURLConnection) cUrl.openConnection();

                request.connect();

                JsonParser jsonParser = new JsonParser();
                InputStream stream = (InputStream) request.getContent();
                JsonElement response = jsonParser.parse(new InputStreamReader(stream));

                return response.getAsJsonObject();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @AllArgsConstructor @Data
    static class LangFile {
        private final int language;
        private final String key, value;
    }
}
