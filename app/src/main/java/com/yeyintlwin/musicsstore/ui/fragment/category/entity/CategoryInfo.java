package com.yeyintlwin.musicsstore.ui.fragment.category.entity;

import java.io.Serializable;

public class CategoryInfo implements Serializable {

    private String id;
    private String name;

    public CategoryInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
