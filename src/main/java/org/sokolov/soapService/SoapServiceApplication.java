package org.sokolov.soapService;


import org.sokolov.soapService.utils.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SoapServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SoapServiceApplication.class, args);
        Data data = ctx.getBean("dataPropagation",Data.class);
        data.propagate();
    }

}
