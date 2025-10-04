package br.com.alura.projeto.course;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TestSearchCourseDTO {

    @Test
    public void validateCourseCodePattern__mustSucceedAllOptions() {
        var pattern = "^[a-zA-Z]+(-[a-zA-Z]+)*$";
        Assertions.assertAll(
            () -> assertTrue("spring-boot-avancado".matches(pattern)),
            () -> assertTrue("SPRING-BOOT-AVANCADO".matches(pattern)),
            () -> assertTrue("Spring-Boot-Avancado".matches(pattern)),
            () -> assertTrue("SpringBootAvancado".matches(pattern)),
            () -> assertTrue("S-p-r-i-n-g-B-o-o-t-A-v-a-n-c-a-d-o".matches(pattern))
        );
    }

    @Test
    public void validateCourseCodePattern__mustFailForAllOptions() {
        var pattern = "^[a-zA-Z]+(-[a-zA-Z]+)*$";
        Assertions.assertAll(
            () -> assertFalse("spring-boot-avançado".matches(pattern)),
            () -> assertFalse("spring-boot-avancado!".matches(pattern)),
            () -> assertFalse("spring_boot_avancado".matches(pattern)),
            () -> assertFalse("spring.boot.avancado".matches(pattern)),
            () -> assertFalse("#spring-boot-avancado".matches(pattern)),
            () -> assertFalse("$spring-boot-avancado".matches(pattern)),
            () -> assertFalse("*spring-boot-avancado".matches(pattern)),
            () -> assertFalse("spring&boot&avancado".matches(pattern)),
            () -> assertFalse("@spring-boot-avancado".matches(pattern)),
            () -> assertFalse("spring-boot-avancado()".matches(pattern)),
            () -> assertFalse("spring-boot-avancado[]".matches(pattern)),
            () -> assertFalse("spring-boot-avancado{}".matches(pattern)),
            () -> assertFalse("spring-boot-avancado,".matches(pattern)),
            () -> assertFalse("spring-boot-avancado;".matches(pattern)),
            () -> assertFalse("spring-boot^avancado".matches(pattern)),
            () -> assertFalse("curso-joão-costal".matches(pattern)),
            () -> assertFalse("curso-do-maurício".matches(pattern)),
            () -> assertFalse("lingüica-como-antigamente".matches(pattern)),
            () -> assertFalse("\\spring\\boot\\avancado".matches(pattern)),
            () -> assertFalse("/spring/boot/avancado".matches(pattern))
        );
    }

}
