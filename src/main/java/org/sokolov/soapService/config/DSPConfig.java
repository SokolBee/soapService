package org.sokolov.soapService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@Profile("default")
@Configuration
public class DSPConfig {

    private final Logger log = LoggerFactory.getLogger(DSPConfig.class);

    private final Environment env;

    public DSPConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder;
        try {
            dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(env.getProperty("database.className"));
            dataSourceBuilder.url(env.getProperty("database.url"));
            dataSourceBuilder.username(env.getProperty("database.userName"));
            dataSourceBuilder.password(env.getProperty("database.password"));
        } catch (Exception e) {
            log.error("DataSource bean cannot be created!", e);
            return null;
        }
        return dataSourceBuilder.build();
    }
    @Bean
    @Primary
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", env.getProperty("dialect.prod"));
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.hbm2ddl.auto", "create");
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
        return hibernateProp;
    }
}
