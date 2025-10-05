package br.com.alura.projeto.course.dto;

import br.com.alura.projeto.course.domain.Course;
import br.com.alura.projeto.course.domain.CourseStatusType;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;

public record UpdateCourseFormDTO(
    @Length(max = 256)
    String name,

    @Length(max = 10)
    @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)*$")
    String code,

    @Length(max = 300)
    String description,

    @Length(max = 150)
    String instructorEmail,
    CourseStatusType status,
    OffsetDateTime inactivationDate) {

}
