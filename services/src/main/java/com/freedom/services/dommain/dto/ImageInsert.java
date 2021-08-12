package com.freedom.services.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * json object - for insert image ContentTools
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageInsert {
    private String url;
    private String alt;
    private int[] size;


}
