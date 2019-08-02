package com.yeyintlwin.musicsstore.ui.fragment.download.child.finish.entity;

import java.io.Serializable;

public class FinishInfo implements Serializable {

    private String album;
    private String artist;
    private String genre;
    private String path;
    private String title;

    public FinishInfo() {
    }

    public FinishInfo(String title, String artist, String genre, String album, String path) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.path = path;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}