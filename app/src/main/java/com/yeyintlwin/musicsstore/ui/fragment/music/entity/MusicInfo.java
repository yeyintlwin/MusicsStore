package com.yeyintlwin.musicsstore.ui.fragment.music.entity;

import java.io.Serializable;

public class MusicInfo implements Serializable {

    public static final int STATUS_NOT_DOWNLOAD = 0;
    public static final int STATUS_CONNECTING = 1;
    public static final int STATUS_CONNECT_ERROR = 2;
    public static final int STATUS_DOWNLOADING = 3;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_DOWNLOAD_ERROR = 5;
    public static final int STATUS_COMPLETE = 6;

    private String id;
    private String title;
    private String artist;
    private String genre;
    private String album;
    private String country;
    private String cover;
    private String link;
    private String counter;

    private int status;
    private int progress;
    private String perSize;


    public MusicInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPerSize() {
        return perSize;
    }

    public void setPerSize(String perSize) {
        this.perSize = perSize;
    }

    public String getButtonText() {
        switch (getStatus()) {
            default:
            case STATUS_NOT_DOWNLOAD:
                return "DOWNLOAD";
            case STATUS_CONNECTING:
                return "CANCEL";
            case STATUS_CONNECT_ERROR:
            case STATUS_DOWNLOAD_ERROR:
                return "TRY AGAIN";
            case STATUS_DOWNLOADING:
                return "PAUSE";
            case STATUS_PAUSED:
                return "RESUME";
            case STATUS_COMPLETE:
        }
        return "PLAY";
    }

    public String getStatusText() {
        switch (getStatus()) {
            default:
            case STATUS_NOT_DOWNLOAD:
                return "";
            case STATUS_CONNECTING:
                return "Connecting...";
            case STATUS_CONNECT_ERROR:
                return "Connect Error";
            case STATUS_DOWNLOADING:
                return "Downloading...";
            case STATUS_PAUSED:
                return "Paused";
            case STATUS_DOWNLOAD_ERROR:
                return "Download Error";
            case STATUS_COMPLETE:
        }
        return "Complete";
    }

    public String getStatusEmoji() {
        switch (getStatus()) {
            default:
            case STATUS_NOT_DOWNLOAD:
                return "";
            case STATUS_CONNECTING:
                return "\ud83d\udce1";
            case STATUS_CONNECT_ERROR:
            case STATUS_DOWNLOAD_ERROR:
                return "\u2757";
            case STATUS_DOWNLOADING:
                return "\ud83d\udce5";
            case STATUS_PAUSED:
                return "\ud83d\udca4";
            case STATUS_COMPLETE:
        }
        return "\ud83c\udfb5";
    }
}
