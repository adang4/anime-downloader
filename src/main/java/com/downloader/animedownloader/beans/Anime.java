package com.downloader.animedownloader.beans;

import java.time.LocalDate;
import java.util.HashMap;

public class Anime {
    LocalDate startDate;
    LocalDate endDate;
    HashMap<String, String> titles;
    Double averageRating;
    int userCount;
    int episodeCount;
    int episodeLength;
    boolean nsfw;
    String synopsis;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public HashMap<String, String> getTitles() {
        return titles;
    }

    public void setTitles(HashMap<String, String> titles) {
        this.titles = titles;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", titles=" + titles +
                ", averageRating=" + averageRating +
                ", userCount=" + userCount +
                ", episodeCount=" + episodeCount +
                ", episodeLength=" + episodeLength +
                ", nsfw=" + nsfw +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }
}
