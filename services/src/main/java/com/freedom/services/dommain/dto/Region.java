package com.freedom.services.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Json for regions ContentTools
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {
    private String name;
    private String region;

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
