package com.freedom.services.config;

import com.freedom.services.repos.ImageRepository;
import com.freedom.services.service.MailService;
import com.freedom.services.service.UserService;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

@Configuration
@ComponentScan({"com.freedom.services","com.freedom.services.repos"})
@EnableAutoConfiguration
public class ServiceTestConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public FlywayMigrationStrategy clean() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }

    @Bean
    @Primary
    public MailService getMailService() {
        return Mockito.mock(MailService.class);
    }


}
