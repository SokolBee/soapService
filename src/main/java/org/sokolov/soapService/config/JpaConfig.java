package org.sokolov.soapService.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.sokolov.soapService"})
@EnableJpaRepositories(basePackages = {"org.sokolov.soapService.repositories"})
public class JpaConfig {
    private final Environment env;

    @Autowired
    public JpaConfig (Environment env) {
        this.env = env;
    }

    private static final Logger logger = LoggerFactory.getLogger(JpaConfig.class);
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder;
        try {
            dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName(env.getProperty("database.className"));
            dataSourceBuilder.url(env.getProperty("database.url"));
            dataSourceBuilder.username(env.getProperty("database.userName"));
            dataSourceBuilder.password(env.getProperty("database.password"));
        } catch (Exception e) {
            logger.error("DataSource bean cannot be created!", e);
            return null;
        }
        return dataSourceBuilder.build();
    }
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
        return hibernateProp;
    }
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("org.sokolov.soapService.models");
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getNativeEntityManagerFactory();
    }
    @Bean
    public PersistenceUnitUtil persistenceUnitUtil(){
        return entityManagerFactory()
                .getPersistenceUnitUtil();
    }
}
