package com.wz.modularmonolithexample;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularMonolithExampleApplicationTest {

    @Test
    void verifyModulesStructure() {
        var violations = ApplicationModules.of(ModularMonolithExampleApplication.class).detectViolations();

        var messages = violations.getMessages().stream().filter(m -> {
            var isAllowed = m.contains("uses field injection") && m.contains(".eventDispatcher.");
            return !isAllowed;
        }).toList();

        if (!messages.isEmpty()) {
            fail(violations);
        }

    }

}
