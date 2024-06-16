package com.wz.modularmonolithexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {
        LiquibaseAutoConfiguration.class
})
public class ModularMonolithExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModularMonolithExampleApplication.class, args);
    }

}
