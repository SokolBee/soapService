package org.sokolov.soapService.config;

import org.sokolov.soapService.generated.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan(basePackages = {"org.sokolov.soapService"})
public class AppConfig {
    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
    @Bean
    ObjectFactory objectFactory(){
        return new ObjectFactory();
    }
}
