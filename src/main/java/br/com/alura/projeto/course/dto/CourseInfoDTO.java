package br.com.alura.projeto.course.dto;

import br.com.alura.projeto.course.domain.Course;
import br.com.alura.projeto.course.domain.CourseStatusType;

import java.time.OffsetDateTime;

public record CourseInfoDTO(
    Long id,
    String name,
    String code,
    String description,
    String instructorEmail,
    CourseStatusType status,
    OffsetDateTime inactivationDate) {

}
