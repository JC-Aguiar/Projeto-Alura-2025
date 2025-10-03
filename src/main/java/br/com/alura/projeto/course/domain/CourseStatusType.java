package br.com.alura.projeto.course.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum CourseStatusType {
    ACTIVE,
    INACTIVE;

    @JsonCreator
    public static CourseStatusType fromValue(String value) {
        return identify(value).orElseThrow(
            () -> new IllegalArgumentException("Invalid Course status type: " + value)
        );
    }

    public static Optional<CourseStatusType> identify(String value) {
        return Arrays.stream(CourseStatusType.values())
            .filter(type -> type.name().equalsIgnoreCase(value))
            .findFirst();
    }
}
