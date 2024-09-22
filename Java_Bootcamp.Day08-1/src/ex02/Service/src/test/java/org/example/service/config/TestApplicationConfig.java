package org.example.service.config;

import org.example.service.repositories.UsersRepository;
import org.example.service.repositories.UsersRepositoryJdbcImpl;
import org.example.service.services.UserService;
import org.example.service.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {

    @Bean("SpringDataSource")
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setName("test")
                .addScript("schema.sql")
                .build();
    }

    @Bean(name = "UserService")
    public UserService getUsersServiceImpl(){
        return new UserServiceImpl();
    }

    @Bean
    public UsersRepository getUsersRepository() {
        return new UsersRepositoryJdbcImpl();
    }
}
