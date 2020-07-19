package com.downloader.animedownloader.service;

import com.downloader.animedownloader.beans.Anime;
import com.downloader.animedownloader.beans.EpisodeList;
import com.downloader.animedownloader.module.MagnetLinkHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uriSchemeHandler.CouldNotOpenUriSchemeHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HorriblesubsService {
    private String baseUrl = "www.horriblesubs.info";

    @Autowired
    private MagnetLinkHandler magnetLinkHandler;

    private Logger logger = LoggerFactory.getLogger(HorriblesubsService.class);

    public HashMap<Integer, String> search(Anime anime, EpisodeList episodeList) {
        HashMap<Integer, String> magnetLinks = new HashMap<>();

        anime.getTitles().entrySet().stream().forEach(a -> {
            String str = a.getValue().toLowerCase().trim().replaceAll("[\\s:]", "-");
            URI uri = null;
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                uri = new URIBuilder()
                        .setScheme("https")
                        .setHost(baseUrl)
                        .setPath("/shows")
                        .setPath("/" + str)
                        .build();
                HttpGet httpGet = new HttpGet(uri);
                HttpResponse result = httpClient.execute(httpGet);
                int statusCode = result.getStatusLine().getStatusCode();

                if (statusCode >= 200 && statusCode < 300) {
                    String response = EntityUtils.toString(result.getEntity());
                    Document html = Jsoup.parse(response);
                    Elements scriptElements = html.getElementsByTag("script");
                    String showId = findShowId(scriptElements);
                    getShows(showId, episodeList).entrySet().stream().forEach(e -> {
                        magnetLinks.putIfAbsent(e.getKey(), e.getValue());
                    });
                }
            } catch (URISyntaxException | IOException e) {
                logger.error(e.getMessage());
            }
        });

        return magnetLinks;
    }

    private String getMagnetLink(String response, String showId, String resolution, int episodeNum) {
        String id = String.format("#%02d-%s", episodeNum, resolution);
        Document html = null;
        Element element = null;
        URI uri = null;
        int i = 0;

        try {
            html = Jsoup.parse(response);
            element = html.selectFirst(id).selectFirst("[title~=Magnet Link]");
        } catch (NullPointerException e) {
            logger.debug("Magnet link not found for " + episodeNum + " " + resolution);
        }

        while (element == null) {
            i++;
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                uri = new URIBuilder()
                        .setScheme("https")
                        .setHost(baseUrl)
                        .setPath("api.php")
                        .setCustomQuery("")
                        .setParameter("method", "getshows")
                        .setParameter("type", "show")
                        .setParameter("showid", showId)
                        .setParameter("nextid", String.valueOf(i))
                        .build();
                HttpGet httpGet = new HttpGet(uri);
                httpGet.setHeader("Accept", "text/html");
                httpGet.setHeader("Accept", "text/html");

                HttpResponse httpResponse = httpClient.execute(httpGet);
                String result = EntityUtils.toString(httpResponse.getEntity());

                if (result.equalsIgnoreCase("DONE"))
                    break;

                html = Jsoup.parse(result);
                element  = html.selectFirst(id).selectFirst("[title~=Magnet Link]");
            } catch (URISyntaxException | IOException e) {
                logger.error("Unable to connect to " + uri.toString());
            } catch (NullPointerException e) {
                logger.debug("Element could not be found");
            }
        }

        return element.attr("href");
    }

    private HashMap<Integer, String> getShows(String showId, EpisodeList episodeList) {
        URI uri = null;
        HashMap<Integer, String> magnetLinks = new HashMap<Integer, String>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(baseUrl)
                    .setPath("api.php")
                    .setCustomQuery("")
                    .setParameter("method", "getshows")
                    .setParameter("type", "show")
                    .setParameter("showid", showId)
                    .build();
            HttpGet httpGet = new HttpGet(uri);

            httpGet.setHeader("Acceot", "text/html");
            httpGet.setHeader("Content-Type", "text/html");

            HttpResponse result = httpClient.execute(httpGet);
            int statusCode = result.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode < 300) {
                String response = EntityUtils.toString(result.getEntity());

                episodeList.getEpisodeNum().forEach(number -> {
                    String magnet = getMagnetLink(response, showId, episodeList.getResolution(), number);
                    magnetLinks.putIfAbsent(number, magnet);
                });
            }
        } catch (URISyntaxException | IOException e) {
            logger.error("Unable to make http GET request");

        }

        return magnetLinks;
    }

    private String findShowId(Elements scriptElements) {
        String showId = null;

        for (Element element : scriptElements) {
            if (element.data().contains("hs_showid")) {
                Pattern pattern = Pattern.compile("(\\d+)");
                Matcher matcher = pattern.matcher(element.data());

                if (matcher.find()) {
                    showId = matcher.group(1);
                    break;
                }
            }
        }
        ;

        return showId;
    }
}
