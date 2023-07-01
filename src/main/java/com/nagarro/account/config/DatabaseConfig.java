package com.nagarro.account.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        String classpath = System.getProperty("user.dir");
        properties.setUrl("jdbc:ucanaccess://" + classpath + "\\src\\main\\resources\\data\\accountsdb.accdb");
        properties.setDriverClassName("net.ucanaccess.jdbc.UcanaccessDriver");
        return properties.initializeDataSourceBuilder().build();
    }

}
