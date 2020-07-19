package com.downloader.animedownloader.service;

import com.downloader.animedownloader.beans.Anime;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@Service
public class AnimeLookupService {
    private String baseURL = "https://kitsu.io/api/edge";

    private Logger logger = LoggerFactory.getLogger(AnimeLookupService.class);

    public Anime searchTitle(String title) {
        Anime anime = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseURL + "/anime?filter[text]=" + title.replaceAll(" ", "%20"));

            httpGet.setHeader("Accept", "application/vnd.api+json");
            httpGet.setHeader("Content-Type", "application/vnd.api+json");
            HttpResponse result = httpClient.execute(httpGet);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            JSONObject jsonObject = jsonParse(json);

            anime = new Anime();
            anime.setTitles(convertJSONObjectToMap(jsonObject.getJSONObject("titles")));
            anime.setAverageRating(jsonObject.getDouble("averageRating"));
            anime.setEpisodeCount(jsonObject.getInt("episodeCount"));
            anime.setEpisodeLength(jsonObject.getInt("episodeLength"));
            anime.setStartDate(LocalDate.parse(jsonObject.getString("startDate")));
            anime.setEndDate(LocalDate.parse(jsonObject.getString("endDate")));
            anime.setNsfw(jsonObject.getBoolean("nsfw"));
            anime.setUserCount(jsonObject.getInt("userCount"));
            anime.setSynopsis(jsonObject.getString("synopsis"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return  anime;
    }

    private JSONObject jsonParse(String json) {
        JSONArray jsonArray = new JSONObject(json).getJSONArray("data");
        JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject("attributes");

        return jsonObject;
    }

    private HashMap<String, String> convertJSONObjectToMap(JSONObject jsonObject) {
        HashMap<String, String> hashMap = new Gson().fromJson(
                jsonObject.toString(), HashMap.class
        );

        return hashMap;
    }
}
