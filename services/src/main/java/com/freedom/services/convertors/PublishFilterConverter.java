package com.freedom.services.convertors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.services.dommain.dto.PublishesFilterDto;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

public class PublishFilterConverter  implements Converter<String, PublishesFilterDto> {

    @Override
    public PublishesFilterDto convert(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(s, PublishesFilterDto.class);
        } catch (IOException ex) {
            System.out.println("eror");
        }
        return new PublishesFilterDto();
    }

}
