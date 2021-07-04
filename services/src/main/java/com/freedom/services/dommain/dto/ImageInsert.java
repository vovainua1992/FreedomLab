package com.freedom.services.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * json object - for insert image ContentTools
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageInsert {
    private String url;
    private String alt;
    private int[] size;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int[] getSize() {
        return size;
    }

    public void setSize(int[] size) {
        this.size = size;
    }
}
