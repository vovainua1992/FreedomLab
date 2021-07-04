package com.freedom.services.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Json for regions ContentTools
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {
    private String name;
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
