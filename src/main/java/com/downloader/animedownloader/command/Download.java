package com.downloader.animedownloader.command;


import com.downloader.animedownloader.application.AnimeDownloaderApplication;
import com.downloader.animedownloader.beans.EpisodeList;
import com.downloader.animedownloader.module.MagnetLinkHandler;
import com.downloader.animedownloader.service.AnimeLookupService;
import com.downloader.animedownloader.service.HorriblesubsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import uriSchemeHandler.CouldNotOpenUriSchemeHandler;
import uriSchemeHandler.URISchemeHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*@CommandLine.Command()
public class Download {
    @CommandLine.Parameters(description = "title")
    List<String> titles;

}*/

@Component
public class Download {
    private Logger logger = LoggerFactory.getLogger(Download.class);

    @Autowired
    AnimeLookupService animeLookupService;

    @Autowired
    HorriblesubsService horriblesubsService;

    @Autowired
    MagnetLinkHandler magnetLinkHandler;

    public void init() {
        try {
            start();

            while (true) {
                String title = getInput();
                EpisodeList episodeList = getEpisodes();
                horriblesubsService.search(animeLookupService.searchTitle(title), episodeList).entrySet().stream().forEach(e -> {
                    try {
                        System.out.println("Downloading " + title + " " + e.getKey());
                        if (System.getProperty("os.name").contains("Windows")) {
                            URISchemeHandler
                        }
                        else {
                            magnetLinkHandler.open(e.getValue());
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException | URISyntaxException | CouldNotOpenUriSchemeHandler | IOException s) {
                        logger.error(s.getMessage());
                    }
                });
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException, InterruptedException {
        final String operatingSystem = System.getProperty("os.name");

        if (operatingSystem.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        Thread.sleep(3000);
        System.out.println("Application is starting...\n\n\n");
        Thread.sleep(3000);
    }

    private String getInput() {
        final Scanner scanner = new Scanner(System.in);
        String title = null;

        System.out.print("Please enter the title of the anime: ");
        title = scanner.nextLine();

        return title;
    }

    public EpisodeList getEpisodes() {
        List<Integer> list = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        EpisodeList episodeList = new EpisodeList();
        final Scanner scanner = new Scanner(System.in);
        String quality = null;

        System.out.print("Which episodes would you like to download: ");
        temp = Arrays.asList(scanner.nextLine().split(" "));
        temp.stream().forEach(s -> list.add(Integer.valueOf(s)));
        episodeList.setEpisodeNum(list);

        System.out.print("480p, 720p, or 1080p? (Default: 1080p)");

        switch (scanner.nextLine()) {
            case "480p":
                episodeList.setResolution("480p");
                System.out.println("Selected: 480p");
                break;
            case "720p":
                episodeList.setResolution("720p");
                System.out.println("Selected: 720p");
                break;
            default:
                episodeList.setResolution("1080p");
                System.out.println("Selected: 1080p");
                break;
        }

        return episodeList;
    }
}