package com.wz.modularmonolithexample;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

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
        var options = Documenter.DiagramOptions
                .defaults()
                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML);
        new Documenter(modules)
                .writeModulesAsPlantUml(options)
                .writeModuleCanvases();
    }

    //    @Test
    void domainLayerShouldNotDependOnApplicationOrInfrastructure() {
        ArchRule rule = ArchRuleDefinition.classes()
                                          .that()
                                          .resideInAPackage(domainPackage())
                                          .should()
                                          .onlyDependOnClassesThat()
                                          .resideOutsideOfPackages(infrastructurePackage())
                                          .andShould()
                                          .onlyDependOnClassesThat(resideInApplicationPackageOfSameModuleAndAreDtoOrEvent);

        rule.check(importedClasses);
    }

    private final DescribedPredicate<? super JavaClass> resideInApplicationPackageOfSameModuleAndAreDtoOrEvent = new DescribedPredicate<>(
            "reside in application package of the same module and are DTO or Event") {
        @Override
        public boolean test(JavaClass javaClass) {
            var packageName = javaClass.getPackageName();
            var isInApp = packageName.startsWith("com.wz.modularmonolithexample.");

            if (!isInApp) {
                return true;
            }
            var modulePackageName = "com.wz.modularmonolithexample." + getModuleOfPackage(javaClass.getPackageName());
            var isInApplicationPackage = javaClass.getPackageName().startsWith(modulePackageName);

            if (!isInApplicationPackage) {
                return false;
            }
            var isDtoOrEvent = javaClass.getSimpleName().endsWith("DTO") || javaClass.getSimpleName().endsWith("Event");
            var result = isDtoOrEvent;
            return result;
        }
    };

    private String getModuleOfPackage(String packageName) {
        var tmp = packageName.replace("com.wz.modularmonolithexample.", "");
        int dotIndex = tmp.indexOf('.');
        return tmp.substring(0, dotIndex);
    }

    private String domainPackage() {
        return "com.wz.modularmonolithexample..domain..";
    }

    private String applicationPackage() {
        return "com.wz.modularmonolithexample..application..";
    }

    private String infrastructurePackage() {
        return "com.wz.modularmonolithexample..infrastructure..";
    }

}
