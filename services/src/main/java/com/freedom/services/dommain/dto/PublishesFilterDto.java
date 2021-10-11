package com.freedom.services.dommain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.freedom.services.dommain.Category;
import com.freedom.services.dommain.User;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties
@Data
public class PublishesFilterDto {
    private long categoryId;
    private List<String> tags;
}
