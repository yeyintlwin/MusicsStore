package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity;

import com.yeyintlwin.musicsstore.MainController;

import java.io.Serializable;

public class QueueInfo implements Serializable {

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
    private String cover;

    private String link;

    private String perSize;
    private int progress;

    public QueueInfo() {

    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
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


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStatus() {
        return MainController.Companion.getInt("status_" + getId(), STATUS_NOT_DOWNLOAD);
    }

    public void setStatus(int status) {
        MainController.Companion.putInt("status_" + getId(), status);
    }

    public int getProgress() {
        if (getStatus() == STATUS_COMPLETE) return 100;
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

}
