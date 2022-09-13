package org.sokolov.soapService.config;

import org.sokolov.soapService.generated.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Configuration
@ComponentScan(basePackages = {"org.sokolov.soapService"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {
    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
    @Bean
    ObjectFactory objectFactory(){
        return new ObjectFactory();
    }

    @Bean
    RandomGenerator randomGenerator(){
        RandomGeneratorFactory<RandomGenerator> factory =
                RandomGeneratorFactory.of("Random");
        return factory.create();
    }
}
