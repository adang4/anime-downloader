package com.downloader.animedownloader.application;

import com.downloader.animedownloader.command.Download;
import com.downloader.animedownloader.service.AnimeLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.downloader.animedownloader")
public class AnimeDownloaderApplication {
	@Autowired
	Download download;

	public static void main(String[] args) {
		SpringApplication.run(AnimeDownloaderApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			download.init();
		};
	}
}
