package br.com.alura.projeto.course.dto;

import br.com.alura.projeto.course.domain.CourseStatusType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public record SearchCourseFormDTO (
    @Length(max = 256) String name,
    @Length(max = 10) String code,
    @Length(max = 300) String description,
    @Length(max = 150) String instructorEmail,
    CourseStatusType status,
    OffsetDateTime inactivationDate) {

}
