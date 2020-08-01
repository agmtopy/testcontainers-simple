package com.agmtopy.testcontainers;

import com.agmtopy.testcontainers.config.MysqlTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * mysql 容器测试
 */
@SpringBootTest(
        classes = MysqlTestConfiguration.class,
        properties = {
                "spring.profiles.active=test",
                "embedded.mysql.install.enabled=true",
                "embedded.mysql.init-script-path=initScript.sql"
        })
public class MysqlTestContainersTest {

    @Autowired
    ConfigurableEnvironment environment;

    /**
     * jdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void propertiesAreAvailable() {
        assertThat(environment.getProperty("embedded.mysql.port")).isNotEmpty();
        assertThat(environment.getProperty("embedded.mysql.host")).isNotEmpty();
        assertThat(environment.getProperty("embedded.mysql.schema")).isNotEmpty();
        assertThat(environment.getProperty("embedded.mysql.user")).isNotEmpty();
        assertThat(environment.getProperty("embedded.mysql.password")).isNotEmpty();
        assertThat(environment.getProperty("embedded.mysql.init-script-path")).isNotEmpty();
    }

    @Test
    public void shouldInitDBForMySQL() throws Exception {
        assertThat(jdbcTemplate.queryForObject("select count(first_name) from users where first_name = 'Sam' ", Integer.class)).isEqualTo(1);
    }
}
