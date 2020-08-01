package com.agmtopy.testcontainers.config;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@EnableAutoConfiguration
@Configuration
public class MysqlTestConfiguration {

    @Value("${spring.datasource.url}")
    String jdbcUrl;
    @Value("${spring.datasource.username}")
    String user;
    @Value("${spring.datasource.password}")
    String password;

    @Bean
    public DataSource customDatasource() {
        PoolConfiguration poolConfiguration = new PoolProperties();
        poolConfiguration.setUrl(jdbcUrl);
        poolConfiguration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        poolConfiguration.setUsername(user);
        poolConfiguration.setPassword(password);
        poolConfiguration.setTestOnBorrow(true);
        poolConfiguration.setTestOnReturn(true);
        return new org.apache.tomcat.jdbc.pool.DataSource(poolConfiguration);
    }
}
