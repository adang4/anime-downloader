package com.downloader.animedownloader.beans;

import java.util.List;

public class EpisodeList {
    private List<Integer> episodeNum;
    private String resolution;

    public List<Integer> getEpisodeNum() {
        return episodeNum;
    }

    public void setEpisodeNum(List<Integer> episodeNum) {
        this.episodeNum = episodeNum;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "EpisodeList{" +
                "episodeNum=" + episodeNum +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
