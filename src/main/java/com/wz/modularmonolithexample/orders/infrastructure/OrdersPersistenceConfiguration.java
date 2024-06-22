package com.wz.modularmonolithexample.orders.infrastructure;

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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.wz.modularmonolithexample.orders.domain"}, entityManagerFactoryRef =
        "ordersEntityManagerFactory", transactionManagerRef = "ordersTransactionManager")
@EntityScan("com.wz.modularmonolithexample.orders.domain")
public class OrdersPersistenceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.orders")
    public DataSourceProperties ordersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource ordersDataSource() {
        return ordersDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean ordersEntityManagerFactory(@Qualifier("ordersDataSource") DataSource dataSource,
                                                                             EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource).packages("com.wz.modularmonolithexample.orders.domain").build();
    }

    @Bean
    @Qualifier("ordersTransactionManager")
    public PlatformTransactionManager ordersTransactionManager(
            @Qualifier("ordersEntityManagerFactory") LocalContainerEntityManagerFactoryBean ordersEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(ordersEntityManagerFactory.getObject()));
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.orders.liquibase")
    public LiquibaseProperties ordersLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase ordersLiquibase() {
        return springLiquibase(ordersDataSource(), ordersLiquibaseProperties());
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
