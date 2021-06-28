package com.example.springmvc.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/**
 * Json to update content via ContentTools
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishContent {
    private String title;
    private String imgUrl;
    private Set<Region> regions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public String getHtml(){
        return regions.iterator().next().getRegion();
    }
}
