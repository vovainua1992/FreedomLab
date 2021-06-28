package com.example.springmvc.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * json object for image upload ContentTools
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDto {
    private String url;
    private int size;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
