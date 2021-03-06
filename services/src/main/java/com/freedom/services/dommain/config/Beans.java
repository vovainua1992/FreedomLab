package com.freedom.services.dommain.config;

import com.freedom.services.repos.ImageRepository;
import com.freedom.services.service.ImageService;
import com.freedom.services.utils.ImageEditor;
import com.freedom.services.utils.ImageEditorWithImageJ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

@Configuration
public class Beans {
    private ImageEditor editor;

    @Bean
    public ImageEditor getImageEditor(){
        if (editor!=null)
            return editor;
        else {
            editor = new ImageEditorWithImageJ();
            return editor;
        }
    }

}
