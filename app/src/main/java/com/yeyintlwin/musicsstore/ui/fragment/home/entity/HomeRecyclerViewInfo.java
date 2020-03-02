package com.yeyintlwin.musicsstore.ui.fragment.home.entity;

import java.io.Serializable;
import java.util.List;


public class HomeRecyclerViewInfo implements Serializable {
    private String sectionName;
    private List<HomeSectionListDataInfo> sectionListData;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<HomeSectionListDataInfo> getSectionListData() {
        return sectionListData;
    }

    public void setSectionListData(List<HomeSectionListDataInfo> sectionListData) {
        this.sectionListData = sectionListData;
    }
}
