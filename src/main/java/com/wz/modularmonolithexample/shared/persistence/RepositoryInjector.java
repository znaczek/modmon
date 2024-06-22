package com.wz.modularmonolithexample.shared.persistence;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class RepositoryInjector {

    private final AutowireCapableBeanFactory autowirer;

    @AfterReturning(pointcut = "execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))", returning = "result")
    public void injectAutowiredBeansToEntities(Object result) {
        if (result instanceof List<?>) {
            ((List<?>) result).forEach(autowirer::autowireBean);
        } else if (result instanceof Page) {
            ((Page<?>) result).getContent().forEach(autowirer::autowireBean);
        } else {
            if (result != null) {
                if (result instanceof Optional<?>) {
                    if (((Optional<?>) result).isPresent()) {
                        autowirer.autowireBean(((Optional<?>) result).get());
                    }
                } else {
                    autowirer.autowireBean(result);
                }
            }
        }

    }

}
