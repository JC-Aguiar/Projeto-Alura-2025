package br.com.alura.projeto.course.dto;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record InactivateCourseDTO (
    @Length(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)*$")
    String code) {
}
