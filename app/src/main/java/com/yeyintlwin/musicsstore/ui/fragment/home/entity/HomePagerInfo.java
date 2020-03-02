package com.yeyintlwin.musicsstore.ui.fragment.home.entity;

import java.io.Serializable;

public class HomePagerInfo implements Serializable {
    private String id;
    private String exclusiveCover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExclusiveCover() {
        return exclusiveCover;
    }

    public void setExclusiveCover(String exclusiveCover) {
        this.exclusiveCover = exclusiveCover;
    }

}
