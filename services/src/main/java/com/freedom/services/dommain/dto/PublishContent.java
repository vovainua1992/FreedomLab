package com.freedom.services.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

/**
 * Json to update content via ContentTools
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PublishContent {
    private String title;
    private String imgUrl;
    private Set<Region> regions;

    public String getHtml(){
        return regions.iterator().next().getRegion();
    }
}
