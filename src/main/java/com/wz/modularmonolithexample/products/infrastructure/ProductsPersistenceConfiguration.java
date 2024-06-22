package com.wz.modularmonolithexample.products.infrastructure;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.wz.modularmonolithexample.products.domain"}, entityManagerFactoryRef =
        "productsEntityManagerFactory", transactionManagerRef = "productsTransactionManager")
@EntityScan("com.wz.modularmonolithexample.products.domain.*")
public class ProductsPersistenceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.products")
    public DataSourceProperties productsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource productsDataSource() {
        return productsDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productsEntityManagerFactory(@Qualifier("productsDataSource") DataSource dataSource,
                                                                               EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages("com.wz.modularmonolithexample.products.domain").build();
    }

    @Bean
    @Qualifier("productsTransactionManager")
    public PlatformTransactionManager productsTransactionManager(
            @Qualifier("productsEntityManagerFactory") LocalContainerEntityManagerFactoryBean productsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(productsEntityManagerFactory.getObject()));
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.products.liquibase")
    public LiquibaseProperties productsLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase productsLiquibase() {
        return springLiquibase(productsDataSource(), productsLiquibaseProperties());
    }

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }

}
