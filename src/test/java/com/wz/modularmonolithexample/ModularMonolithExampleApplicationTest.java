package com.wz.modularmonolithexample;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

public class ModularMonolithExampleApplicationTest {

    private final static ApplicationModules modules = ApplicationModules.of(ModularMonolithExampleApplication.class);

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.wz.modularmonolithexample");

    @Test
    void verifyModulesStructure() {
        var violations = modules.detectViolations();

        var messages = violations.getMessages().stream().filter(m -> {
            var isFieldInjectionOfEventDispatcher = m.contains("uses field injection") && m.contains(".eventDispatcher.");

            String regex = ".*\\.application\\..*Event.*";

            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(m);
            var isCyclicDependencyOnEvents = m.contains("Cycle detected") && matcher.matches();
            return !isFieldInjectionOfEventDispatcher && !isCyclicDependencyOnEvents;
        }).toList();

        if (!messages.isEmpty()) {
            fail(violations);
        }

    }

    @Test
    void writeDocumentationSnippets() {
        var options = Documenter.DiagramOptions.defaults().withStyle(Documenter.DiagramOptions.DiagramStyle.UML);
        new Documenter(modules).writeModulesAsPlantUml(options).writeModuleCanvases();
    }

}
